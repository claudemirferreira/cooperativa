package com.siscred.cooperativa.infrastructure.interceptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

class TransactionInterceptorTest {

    private TransactionInterceptor interceptor;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HandlerInterceptor handler;

    @BeforeEach
    void setUp() {
        interceptor = new TransactionInterceptor();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    void shouldUseExistingTransactionIdWhenPresent() {
        String existingTransactionId = "12345-abcde";
        when(request.getHeader("transaction_id")).thenReturn(existingTransactionId);

        boolean result = interceptor.preHandle(request, response, new Object());

        assertTrue(result);
        assertEquals(existingTransactionId, MDC.get("transaction_id"));
        verify(response).setHeader("transaction_id", existingTransactionId);
    }

    @Test
    void shouldGenerateNewTransactionIdWhenNotPresent() {
        when(request.getHeader("transaction_id")).thenReturn(null);

        boolean result = interceptor.preHandle(request, response, new Object());

        assertTrue(result);
        String generatedTransactionId = MDC.get("transaction_id");
        assertNotNull(generatedTransactionId);
        assertFalse(generatedTransactionId.isEmpty());
        verify(response).setHeader(eq("transaction_id"), anyString());
    }

    @Test
    void shouldRemoveTransactionIdAfterCompletion() {
        MDC.put("transaction_id", "12345-abcde");

        interceptor.afterCompletion(request, response, new Object(), null);

        assertNull(MDC.get("transaction_id"));
    }
}
