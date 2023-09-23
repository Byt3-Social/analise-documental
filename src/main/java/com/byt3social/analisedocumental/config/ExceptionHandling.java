package com.byt3social.analisedocumental.config;

import com.byt3social.analisedocumental.exceptions.EmailFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler(EmailFailedException.class)
    public ResponseEntity emailFailedException(EmailFailedException e) {
        Map<String, String> response = new HashMap<>();
        response.put("code", ((Integer) HttpStatus.INTERNAL_SERVER_ERROR.value()).toString());
        response.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.put("message", e.getMessage());

        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
