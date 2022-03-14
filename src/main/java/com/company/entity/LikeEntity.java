package com.company.entity;

import com.company.enums.LikeStatus;
import com.company.enums.LikeType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "likes")
public class LikeEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @Column(name = "action_id")
    private Integer actionId; // comment,article

    @Enumerated(EnumType.STRING)
    private LikeStatus status;

    @Enumerated(EnumType.STRING)
    private LikeType type;
}
