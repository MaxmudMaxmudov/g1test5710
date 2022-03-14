package com.company.dto.article;

import com.company.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ArticleFilterDTO {
    private String title;
    private Integer profileId;
    private Integer articleId;
    private ArticleStatus status;

    private LocalDate fromDate;
    private LocalDate toDate;

}
