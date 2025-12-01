package io.github.wiltonreis.library.exception;

import lombok.Getter;

@Getter
public class InvalidFieldException extends RuntimeException {

    private String field;

    public InvalidFieldException(String field, String message) {
        super(message);
        this.field = field;
    }
}
