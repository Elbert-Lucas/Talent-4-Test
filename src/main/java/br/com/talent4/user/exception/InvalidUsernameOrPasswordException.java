package br.com.talent4.user.exception;

import org.springframework.http.HttpStatus;

public class InvalidUsernameOrPasswordException extends UserBaseException {
    public InvalidUsernameOrPasswordException(String message){
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
