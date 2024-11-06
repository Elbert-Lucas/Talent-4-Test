package br.com.talent4.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class UserBaseException extends ResponseStatusException {

    public UserBaseException(HttpStatus httpStatus, String message){
        super(httpStatus, message);
    }
}
