package br.com.pires.api_gerenciamento_de_pedidos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemPedidoDTO(
        @NotBlank String descricao,
        @Min(1) int quantidade,
        @NotNull BigDecimal precoUnitario
) {}