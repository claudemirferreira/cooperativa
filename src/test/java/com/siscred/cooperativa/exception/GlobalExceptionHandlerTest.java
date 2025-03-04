package com.siscred.cooperativa.exception;

import com.siscred.cooperativa.infrastructure.exception.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleNotExistSessaoAbertaException_ShouldReturnBadRequest() {
        NotExistSessaoAbertaException exception = new NotExistSessaoAbertaException("Sessão fechada");

        ResponseEntity<Object> response = exceptionHandler.handleNotExistSessaoAbertaException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Sessão fechada", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    void handleCpfInvalidException_ShouldReturnBadRequest() {
        CpfInvalidException exception = new CpfInvalidException("CPF inválido");

        ResponseEntity<Object> response = exceptionHandler.handleCpfInvalidException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("CPF inválido", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    void handleExistVotoCPFException_ShouldReturnConflict() {
        ExistVotoCPFException exception = new ExistVotoCPFException("Voto já registrado para este CPF");

        ResponseEntity<String> response = exceptionHandler.handleExistVotoCPFException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Voto já registrado para este CPF", response.getBody());
    }

    @Test
    void handleEntityNotFoundException_ShouldReturnNotFound() {
        EntityNotFoundException exception = new EntityNotFoundException("Entidade não encontrada");

        ResponseEntity<Object> response = exceptionHandler.handleEntityNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Entidade não encontrada", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    void handleValidationExceptions_ShouldReturnBadRequestWithFieldErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(
                java.util.List.of(new FieldError("pautaRequest", "nome", "Nome é obrigatório"))
        );
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleValidationExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation Error", response.getBody().get("error"));
        assertEquals("Nome é obrigatório", ((Map<?, ?>) response.getBody().get("errors")).get("nome"));
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerError() {
        Exception exception = new Exception("Erro inesperado");

        ResponseEntity<Object> response = exceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred.", ((Map<?, ?>) response.getBody()).get("message"));
    }
}
