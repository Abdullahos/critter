package com.udacity.jdnd.course3.critter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFound extends RuntimeException{
    public ObjectNotFound() {
        super();
    }
    public ObjectNotFound(String message){
        super(message);
    }

    public ObjectNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFound(Throwable cause) {
        super(cause);
    }

    public ObjectNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
