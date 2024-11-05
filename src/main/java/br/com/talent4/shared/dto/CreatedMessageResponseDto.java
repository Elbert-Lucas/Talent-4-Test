package br.com.talent4.shared.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter @Setter
public class CreatedMessageResponseDto extends MessageResponseDto {

    private final Long createdId;

    public CreatedMessageResponseDto(String message, Long createdId){
        super(message, HttpStatus.CREATED);
        this.createdId = createdId;
    }

}