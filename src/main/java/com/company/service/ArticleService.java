package com.company.service;

import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleFilterDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ArticleStatus;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ArticleCustomRepositoryImpl;
import com.company.repository.ArticleRepository;
import com.company.spec.ArticleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleCustomRepositoryImpl articleCustomRepository;

    public ArticleDTO create(ArticleDTO dto, Integer userId) {
        ProfileEntity profileEntity = profileService.get(userId);

        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new BadRequestException("Title can not be null");
        }
        if (dto.getContent() == null || dto.getContent().isEmpty()) {
            throw new BadRequestException("Content can not be null");
        }


        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle()); // null
        entity.setContent(dto.getContent());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setProfile(profileEntity);

        articleRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public void publish(Integer articleId, Integer userId) {
        ProfileEntity profileEntity = profileService.get(userId);

        ArticleEntity entity = get(articleId);
        entity.setStatus(ArticleStatus.PUBLISHED);
        entity.setPublishedDate(LocalDateTime.now());
        articleRepository.save(entity);
    }

    public ArticleEntity get(Integer id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Article not found"));
    }

    public PageImpl<ArticleDTO> filter(int page, int size, ArticleFilterDTO filterDTO) {
        PageImpl<ArticleEntity> entityPage = articleCustomRepository.filter(page, size, filterDTO);

        List<ArticleDTO> articleDTOList = entityPage.stream().map(articleEntity -> {
            ArticleDTO dto = new ArticleDTO();
            dto.setId(articleEntity.getId());
            //
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(articleDTOList, entityPage.getPageable(), entityPage.getTotalElements());
    }

    public PageImpl<ArticleDTO> filterSpe(int page, int size, ArticleFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
//        Specification<ArticleEntity> title = ((root, query, criteriaBuilder) -> {
//            return criteriaBuilder.equal(root.get("title"), filterDTO.getTitle());
//        });
//
//        Specification<ArticleEntity> idSpec = ((root, query, criteriaBuilder) -> {
//            return criteriaBuilder.equal(root.get("id"), filterDTO.getArticleId());
//        });
//
//        Specification<ArticleEntity> spec = Specification.where(title);
//        spec.and(idSpec);
//
//        Page<ArticleEntity> articlePage = articleRepository.findAll(spec, pageable);
//        System.out.println(articlePage.getTotalElements());
//        return null;
        Specification<ArticleEntity> spec = null;
        if (filterDTO.getStatus() != null) {
            spec = Specification.where(ArticleSpecification.status(filterDTO.getStatus()));
        } else {
            spec = Specification.where(ArticleSpecification.status(ArticleStatus.PUBLISHED));
        }

        if (filterDTO.getTitle() != null) {
            spec.and(ArticleSpecification.title(filterDTO.getTitle()));
        }
        if (filterDTO.getArticleId() != null) {
            spec.and(ArticleSpecification.equal("id", filterDTO.getArticleId()));
        }
        if (filterDTO.getProfileId() != null) {
            spec.and(ArticleSpecification.equal("profile.id", filterDTO.getProfileId()));
        }

        Page<ArticleEntity> articlePage = articleRepository.findAll(spec, pageable);
        System.out.println(articlePage.getTotalElements());
        return null;
    }
}
