package br.com.talent4.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter @Setter
public class CreatedMessageResponseDto extends MessageResponseDto {

    @JsonProperty("created_id")
    private final Long createdId;

    public CreatedMessageResponseDto(String message, Long createdId){
        super(message, HttpStatus.CREATED);
        this.createdId = createdId;
    }

}