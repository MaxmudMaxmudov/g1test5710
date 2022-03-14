package com.company.repository;

import com.company.entity.LikeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
    Optional<LikeEntity> findByActionIdAndProfileAndType(Integer actionId,
                                                         ProfileEntity profileEntity, LikeType type);
}
