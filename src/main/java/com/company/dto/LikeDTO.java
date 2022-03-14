package com.company.dto;

import com.company.enums.LikeStatus;
import com.company.enums.LikeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeDTO {
    private Integer id;

    private Integer actionId;
    private LikeStatus status;
    private LikeType type;

    private Integer profileId;
}
