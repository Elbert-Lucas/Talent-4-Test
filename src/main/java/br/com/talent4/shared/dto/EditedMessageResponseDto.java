package br.com.talent4.shared.dto;

import org.springframework.http.HttpStatus;

public class EditedMessageResponseDto extends MessageResponseDto {

    public EditedMessageResponseDto(String message){
        super(message, HttpStatus.OK);
    }
}
