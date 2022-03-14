package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private Integer id;

    // create
    private String content; // update
    private Integer articleId;

    private Integer profileId;
    private LocalDateTime createdDate;
}