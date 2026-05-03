package br.com.pires.api_gerenciamento_de_pedidos.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponse(
        int status,
        String erro,
        String mensagem,
        Map<String, String> campos,
        LocalDateTime timestamp
) {
    public ValidationErrorResponse(int status, String erro,
                                   String mensagem, Map<String, String> campos) {
        this(status, erro, mensagem, campos, LocalDateTime.now());
    }
}