package br.com.talent4.shared.dto;

import org.springframework.http.HttpStatus;

public class DeletedMessageResponse extends MessageResponseDto {

    public DeletedMessageResponse(String message){
        super(message, HttpStatus.OK);
    }
}
