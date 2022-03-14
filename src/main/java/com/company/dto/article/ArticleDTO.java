package com.company.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private Integer id;

    private String title;
    private String content;

    private Integer profileId;

    // status

    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;

}
