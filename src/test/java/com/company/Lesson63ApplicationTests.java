package com.company;

import com.company.dto.article.ArticleDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.article.ArticleFilterDTO;
import com.company.enums.ProfileRole;
import com.company.repository.ArticleCustomRepositoryImpl;
import com.company.service.ArticleService;
import com.company.service.ProfileService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class Lesson63ApplicationTests {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleCustomRepositoryImpl articleCustomRepositoryImpl;

    @Test
    void createProfile() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName("Admin");
        profileDTO.setSurname("Adminjon");
        profileDTO.setLogin("admin");
        profileDTO.setEmail("admin@gmail.com");
        profileDTO.setPswd("123");
        profileDTO.setRole(ProfileRole.ADMIN_ROLE);

        profileService.create(profileDTO);
    }

    @Test
    public void createArticle() {
        ArticleDTO dto = new ArticleDTO();
        dto.setTitle("Dollar kursi");
        dto.setContent("Dollar kursi pasaymoqda. Xa");
        dto.setProfileId(1);

        articleService.create(dto, 1);
    }

    @Test
    public void filterTest() {
        ArticleFilterDTO dto = new ArticleFilterDTO();
        dto.setTitle("salom");
        dto.setArticleId(1);
        dto.setFromDate(LocalDate.of(2022, 01, 02));
        dto.setToDate(LocalDate.now());

//        articleService.filter(1, 10, dto);
        articleCustomRepositoryImpl.filerCriteriaBuilder(dto);
    }
}
