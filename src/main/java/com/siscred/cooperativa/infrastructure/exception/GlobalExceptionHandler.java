package com.siscred.cooperativa.infrastructure.exception;

import com.siscred.cooperativa.exception.CpfInvalidException;
import com.siscred.cooperativa.exception.ExistVotoCPFException;
import com.siscred.cooperativa.exception.NotExistSessaoAbertaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotExistSessaoAbertaException.class)
    public ResponseEntity<String> handleNotExistSessaoAbertaException(NotExistSessaoAbertaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"" + ex.getMessage() + "\"}");
    }

    @ExceptionHandler(CpfInvalidException.class)
    public ResponseEntity<String> handleCpfInvalidException(CpfInvalidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"" + ex.getMessage() + "\"}");
    }

    @ExceptionHandler(ExistVotoCPFException.class)
    public ResponseEntity<String> handleExistVotoCPFException(ExistVotoCPFException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildErrorResponse("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", message);
        errorDetails.put("status", status.value());
        return new ResponseEntity<>(errorDetails, status);
    }
}
