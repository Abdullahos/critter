package com.udacity.jdnd.course3.critter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class MissingInfoException extends RuntimeException{
    public MissingInfoException() {
    }

    public MissingInfoException(String message) {
        super(message);
    }

    public MissingInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingInfoException(Throwable cause) {
        super(cause);
    }

    public MissingInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
