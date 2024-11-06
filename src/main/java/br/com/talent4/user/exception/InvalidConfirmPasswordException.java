package br.com.talent4.user.exception;

import org.springframework.http.HttpStatus;

public class InvalidConfirmPasswordException extends UserBaseException {

    public InvalidConfirmPasswordException(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}
