package org.example.rideshare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Validation Errors --> for username and password validation we can check here!
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String, Object> error = new HashMap<>();
        FieldError fieldError = ex.getBindingResult().getFieldError();

        error.put("error", "VALIDATION_ERROR");
        error.put("message", fieldError != null ? fieldError.getDefaultMessage() : "Invalid data");
        error.put("timestamp", new Date());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // NotFoundException --> this is for not found exception to find if the user has registered or not!
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "NOT_FOUND");
        error.put("message", ex.getMessage());
        error.put("timestamp", new Date());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    // BadRequestException --> this is for bad request exception!
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "BAD_REQUEST");
        error.put("message", ex.getMessage());
        error.put("timestamp", new Date());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
