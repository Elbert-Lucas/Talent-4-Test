package br.com.talent4.handler;

import br.com.talent4.handler.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler  {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();

        log.error("Erro durante a request: " + ex.getNestedPath());
        log.error("Codigo do erro: " +ex.getStatusCode() + " " + ex.getBody().getTitle());
        log.error("Campo do erro: " + ex.getFieldError().getField()
                + " - valor: " + ex.getFieldError().getRejectedValue());
        log.error("Mensagens: " + errors);

        return new ResponseEntity<>(new ErrorResponseDto(ex.getBody().getTitle(), ex.getBody().getStatus(), errors),
                                    ex.getStatusCode());

    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateKey(DuplicateKeyException ex){
        String message = ex.getCause().getMessage();
        if(message.contains("Duplicate entry") && message.contains("for key 'tb_customer.email'")){
            message = "O email enviado já esta em uso";
        }

        log.error("Erro durante a request: " +  message);

        return new ResponseEntity<>(new ErrorResponseDto(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST.value(), List.of(message)),
                                    HttpStatus.BAD_REQUEST);
    }
}