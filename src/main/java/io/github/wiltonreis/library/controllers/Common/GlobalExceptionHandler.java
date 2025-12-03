package io.github.wiltonreis.library.controllers.Common;

import io.github.wiltonreis.library.controllers.DTO.ErrorField;
import io.github.wiltonreis.library.controllers.DTO.ErrorResponse;
import io.github.wiltonreis.library.exception.DuplicatedRecordException;
import io.github.wiltonreis.library.exception.InvalidFieldException;
import io.github.wiltonreis.library.exception.OperationNotAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();

        List<ErrorField> listErrors = fieldErrors
                .stream()
                .map(fe -> new ErrorField(fe.getField(), fe.getDefaultMessage()))
                .toList();

        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", listErrors);
    }

    @ExceptionHandler(DuplicatedRecordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerDuplicatedRecordException(DuplicatedRecordException e){
        return ErrorResponse.conflict(e.getMessage());
    }

    @ExceptionHandler(OperationNotAllowed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerOperationNotAllowed(OperationNotAllowed e){
        return ErrorResponse.standardError(e.getMessage());
    }

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handlerInvalidFieldException(InvalidFieldException e){

        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", List.of(
                new ErrorField(e.getField(), e.getMessage())
        ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccesDeniedException(AccessDeniedException e){
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Access denied", List.of());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnhandledError(RuntimeException e){
        e.printStackTrace();
        return new ErrorResponse(500, "Unexpected error", List.of());
    }
}