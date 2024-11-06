package br.com.talent4.handler;

import br.com.talent4.handler.dto.ErrorResponseDto;
import br.com.talent4.shared.util.MessageUtil;
import br.com.talent4.user.exception.UserBaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLSyntaxErrorException;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler  {

    private final br.com.talent4.shared.util.MessageUtil messageUtil;

    @Autowired
    public GlobalExceptionHandler(MessageUtil messageUtil, MessageSource messageSource) {
        this.messageUtil = messageUtil;
    }

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
        if(message.contains("Duplicate entry")
            && (message.contains("for key 'tb_customer.email'") || message.contains("for key 'tb_user.email'") )){
            message = messageUtil.getMessage("duplicate.email");
        }
        log.error("Erro durante a request: " +  message);

        return new ResponseEntity<>(new ErrorResponseDto(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST.value(), List.of(message)),
                                    HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleSQLSyntaxError(SQLSyntaxErrorException ex) {

        String message = ex.getLocalizedMessage();
        if(message.contains("Unknown column") && message.contains("in 'order clause'")){
            message = messageUtil.getMessage("invalid.order");
        }

        log.error("Erro durante a request: " +  ex.getMessage());

        return new ResponseEntity<>(new ErrorResponseDto(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST.value(), List.of(message)),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserBaseException.class)
    public ResponseEntity<ErrorResponseDto> handlerGeneralUserExceptions(UserBaseException ex){
        log.error("Erro durante a request: " +  ex.getMessage());

        return new ResponseEntity<>(new ErrorResponseDto(ex.getStatusCode().toString(),
                ex.getStatusCode().value(),
                List.of(ex.getReason())),
                ex.getStatusCode());
    }
}