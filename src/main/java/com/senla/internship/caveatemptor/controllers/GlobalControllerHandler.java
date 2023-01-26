package com.senla.internship.caveatemptor.controllers;

import com.senla.internship.caveatemptor.dto.Response;
import com.senla.internship.caveatemptor.exceptions.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalControllerHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleException(ServletWebRequest request, UserNotFoundException e) {
        Response response = new Response(new Date().getTime(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request.getContextPath());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleException(ServletWebRequest request, ConstraintViolationException e) {
      Response response = new Response(new Date().getTime(),
              HttpStatus.BAD_REQUEST.value(),
              HttpStatus.BAD_REQUEST.getReasonPhrase(),
              e.getMessage(),
              request.getContextPath());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
