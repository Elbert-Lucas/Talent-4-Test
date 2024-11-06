package br.com.talent4.user.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends UserBaseException {
    public InvalidPasswordException(String message){
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
