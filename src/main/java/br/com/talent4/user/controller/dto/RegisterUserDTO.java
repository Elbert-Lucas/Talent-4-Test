package br.com.talent4.user.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
public class RegisterUserDTO {

    @NotEmpty
    @Size(min = 3, message = "{minimumSize.name}")
    private String name;

    @NotEmpty
    @Email(message = "{invalid.email}")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}$",
            message = "{invalid.password}")
    private String password;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}$",
            message = "{invalid.password}")
    private String confirmPassword;
}
