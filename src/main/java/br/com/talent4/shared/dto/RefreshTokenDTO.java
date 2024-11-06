package br.com.talent4.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RefreshTokenDTO {
    @JsonProperty("refresh_token")
    private String refreshToken;
}
