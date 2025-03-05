package com.siscred.cooperativa.infrastructure.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import java.util.UUID;

@Component("httpTransactionInterceptor")
public class TransactionInterceptor implements HandlerInterceptor {
    private static final String TRANSACTION_ID_KEY = "transaction_id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String transactionId = request.getHeader(TRANSACTION_ID_KEY);
        if (transactionId == null || transactionId.isEmpty()) {
            transactionId = UUID.randomUUID().toString();
        }

        MDC.put(TRANSACTION_ID_KEY, transactionId);
        response.setHeader(TRANSACTION_ID_KEY, transactionId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove(TRANSACTION_ID_KEY);
    }
}
