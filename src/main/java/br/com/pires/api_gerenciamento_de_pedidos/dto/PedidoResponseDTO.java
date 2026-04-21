package br.com.pires.api_gerenciamento_de_pedidos.dto;

import br.com.pires.api_gerenciamento_de_pedidos.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponseDTO(
        Long id,
        String enderecoEntrega,
        StatusPedido status,
        BigDecimal valorTotal,
        LocalDateTime criadoEm
) {}
