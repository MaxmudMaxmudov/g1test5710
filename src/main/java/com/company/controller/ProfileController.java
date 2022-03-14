package com.company.controller;

import com.company.dto.ProfileDTO;
import com.company.dto.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ForbiddenException;
import com.company.exceptions.ItemNotFoundException;
import com.company.exceptions.UnauthorizedException;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto,
                                             HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);

        ProfileDTO response = profileService.create(dto);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> registration(@RequestBody ProfileJwtDTO jwtDTO){

        return null;
    }

    // registration
    // authorization

}
