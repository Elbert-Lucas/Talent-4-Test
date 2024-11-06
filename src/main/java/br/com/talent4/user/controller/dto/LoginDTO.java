package br.com.talent4.user.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDTO {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
