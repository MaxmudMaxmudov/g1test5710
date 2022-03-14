package com.company.controller;

import com.company.dto.CommentDTO;
import com.company.dto.ProfileJwtDTO;
import com.company.service.CommentService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    private ResponseEntity create(@RequestBody CommentDTO dto,
                                  HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        CommentDTO response = commentService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    private ResponseEntity getById(@PathVariable("id") Integer id,
                                   HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        CommentDTO response = commentService.getById(id, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    private ResponseEntity update(@PathVariable("id") Integer id, @RequestBody CommentDTO dto,
                                  HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        commentService.update(id, dto, jwtDTO.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity delete(@PathVariable("id") Integer id,
                                  HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        commentService.delete(id, jwtDTO.getId());
        return ResponseEntity.ok().build();
    }

}
