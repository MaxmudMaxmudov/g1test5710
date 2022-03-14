package com.company.service;

import com.company.dto.CommentDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private CommentRepository commentRepository;

    public CommentDTO create(CommentDTO dto, Integer userId) {
        ArticleEntity article = articleService.get(dto.getArticleId());
        ProfileEntity profile = profileService.get(userId);

        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setArticle(article);
        entity.setProfile(profile);
        commentRepository.save(entity);

        dto.setId(entity.getId());
        return dto;
    }

    public CommentDTO getById(Integer commentId, Integer userId) {
        CommentEntity entity = get(commentId);
        if (!entity.getProfile().getId().equals(userId)) {
            throw new BadRequestException("Not Owner");
        }
        return toDto(entity);
    }

    public CommentEntity get(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Comment Not Found"));
    }

    public CommentDTO toDto(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setProfileId(entity.getProfile().getId());
        dto.setArticleId(entity.getArticle().getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public void update(Integer commentId, CommentDTO dto, Integer userId) {
        CommentEntity entity = get(commentId);
        if (!entity.getProfile().getId().equals(userId)) { // owner
            throw new BadRequestException("Not Owner");
        }

        entity.setContent(dto.getContent());
        commentRepository.save(entity);
    }

    public void delete(Integer commentId, Integer userId) {
        CommentEntity entity = get(commentId);
        if (!entity.getProfile().getId().equals(userId)) { // owner
            throw new BadRequestException("Not Owner");
        }
        commentRepository.delete(entity);
    }
}
