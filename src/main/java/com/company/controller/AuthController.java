package com.company.controller;

import com.company.dto.ProfileDTO;
import com.company.dto.auth.AuthorizationDTO;
import com.company.dto.auth.RegistrationDTO;
import com.company.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Api(tags = "Auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "Login method", notes = "Sekinroq. Ishlami qolishi mumkin",nickname = "nikeName")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthorizationDTO dto) {
        ProfileDTO response = authService.authorization(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    @ApiOperation(value = "Registration method", notes = "Sekinroq. Ishlami qolishi mumkin",nickname = "nikeName")
    public ResponseEntity<ProfileDTO> registration(@Valid @RequestBody RegistrationDTO dto) {
        authService.registration(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verification/{jwt}")
    public ResponseEntity<ProfileDTO> verification(@PathVariable("jwt") String jwt) {
        authService.verification(jwt);
        return ResponseEntity.ok().build();
    }
}
