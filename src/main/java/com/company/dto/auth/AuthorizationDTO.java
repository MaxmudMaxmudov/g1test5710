package com.company.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorizationDTO {
    private String login;
    private String password;

}
