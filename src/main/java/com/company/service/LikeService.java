package com.company.service;

import com.company.dto.LikeDTO;
import com.company.entity.LikeEntity;
import com.company.entity.ProfileEntity;
import com.company.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ProfileService profileService;

    // like or dislike
    public LikeDTO create(LikeDTO likeDTO, Integer userId) {
        ProfileEntity profileEntity = profileService.get(userId);

        Optional<LikeEntity> likeOptional = likeRepository.findByActionIdAndProfileAndType(likeDTO.getActionId(),
                profileEntity, likeDTO.getType());

        if (likeOptional.isPresent()) {
            LikeEntity lEntity = likeOptional.get();
            lEntity.setStatus(likeDTO.getStatus());
            likeRepository.save(lEntity);
            likeDTO.setId(lEntity.getId());
            return likeDTO;
        }

        LikeEntity entity = new LikeEntity();
        entity.setProfile(profileEntity);
        entity.setStatus(likeDTO.getStatus());
        entity.setType(likeDTO.getType());
        entity.setActionId(likeDTO.getActionId());

        likeRepository.save(entity);
        likeDTO.setId(entity.getId());
        return likeDTO;
    }
    // remove

}
