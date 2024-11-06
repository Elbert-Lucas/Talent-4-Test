package br.com.talent4.shared.dto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class CredentialsDto {

    private final String token;
    @JsonProperty("refresh_token")
    private final String refreshToken;

    public CredentialsDto(Map<String, String> tokens){
        this.token = tokens.get("token");
        this.refreshToken = tokens.get("refreshToken");
    }
}
