package com.company.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Setter
@Getter
public class RegistrationDTO {

    @NotEmpty(message = "Kalla name qani")
    private String name;
    @NotEmpty(message = "Kalla surname qani")
    @Size(min = 3, max = 15, message = "3-15 oralig'ida bo'lishi kerak mazgi.")
    private String surname;
    @Email
    private String email;
    @NotBlank(message = "Login xato mazgi")
    private String login;
    private String password;
}
