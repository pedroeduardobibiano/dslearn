package com.improvement.dslearn.resources.exceptions;

import com.improvement.dslearn.servicies.exceptions.DatabaseException;
import com.improvement.dslearn.servicies.exceptions.ForbiddenException;
import com.improvement.dslearn.servicies.exceptions.ResourceNotFoundException;
import com.improvement.dslearn.servicies.exceptions.UnauthorizedException;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Entity not found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler({DatabaseException.class, EntityExistsException.class})
    public ResponseEntity<StandardError> dataViolation(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Bad request, Integrity violation");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ValidationError> methodArgumentNotValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Bad request, Integrity violation");
        err.setMessage("Dados Invalidos");
        err.setPath(request.getRequestURI());

        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            assert f != null;
            err.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<OauthCustomError> handleUnauthorized(UnauthorizedException e, HttpServletRequest request) {
        System.out.println("UnauthorizedException caught!");
        OauthCustomError err = new OauthCustomError();
        err.setError("Unauthorized");
        err.setErrorDescription(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }


    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<OauthCustomError> Forbidden(ForbiddenException e, HttpServletRequest request) {
        OauthCustomError err = new OauthCustomError();
        err.setError("Forbiden");
        err.setError(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }


}
