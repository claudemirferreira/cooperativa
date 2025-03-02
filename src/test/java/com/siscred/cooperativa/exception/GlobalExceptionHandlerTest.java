package com.siscred.cooperativa.exception;

import com.siscred.cooperativa.infrastructure.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleNotExistSessaoAbertaException_ShouldReturnBadRequest() {
        NotExistSessaoAbertaException exception = new NotExistSessaoAbertaException("Sessão não encontrada");

        ResponseEntity<String> response = exceptionHandler.handleNotExistSessaoAbertaException(exception);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("{\"message\": \"Sessão não encontrada\"}", response.getBody());
    }

    @Test
    void handleCpfInvalidException_ShouldReturnBadRequest() {
        CpfInvalidException exception = new CpfInvalidException("CPF inválido");

        ResponseEntity<String> response = exceptionHandler.handleCpfInvalidException(exception);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("{\"message\": \"CPF inválido\"}", response.getBody());
    }

    @Test
    void handleExistVotoCPFException_ShouldReturnConflict() {
        ExistVotoCPFException exception = new ExistVotoCPFException("Voto já registrado para este CPF");

        ResponseEntity<String> response = exceptionHandler.handleExistVotoCPFException(exception);

        assertEquals(409, response.getStatusCode().value());
        assertEquals("Voto já registrado para este CPF", response.getBody());
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerError() {
        Exception exception = new Exception("Erro genérico");

        ResponseEntity<Object> response = exceptionHandler.handleGenericException(exception);

        assertEquals(500, response.getStatusCode().value());
        assertEquals("An unexpected error occurred.", ((java.util.Map<?, ?>) response.getBody()).get("message"));
    }
}
