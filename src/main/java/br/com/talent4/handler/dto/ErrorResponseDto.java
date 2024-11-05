package br.com.talent4.handler.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Builder
@Getter @Setter
public class ErrorResponseDto {
    private String error;
    private int code;
    private List<String> messages;
}
