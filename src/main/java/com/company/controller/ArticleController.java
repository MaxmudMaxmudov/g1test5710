package com.company.controller;

import com.company.dto.article.ArticleDTO;
import com.company.dto.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.service.ArticleService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/article")
@Api("Article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    @ApiOperation(value = "Create Task", notes = "Some Noted of ")
    public ResponseEntity<?> create(@RequestBody ArticleDTO dto,
                                    HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.MODERATOR_ROLE);
        ArticleDTO response = articleService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/publish/{id}")
    public ResponseEntity<?> publish(@ApiParam(value = "id",required = true,example = "id of article")
                                         @PathVariable("id") Integer id,
                                     HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.PUBLISHER_ROLE);
        articleService.publish(id, jwtDTO.getId());
        return ResponseEntity.ok().build();
    }

}
