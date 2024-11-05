package br.com.talent4.shared.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
public abstract class MessageResponseDto {
    protected final String message;
    protected final HttpStatus status;
}
