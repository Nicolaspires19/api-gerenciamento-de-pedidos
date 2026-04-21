package br.com.pires.api_gerenciamento_de_pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDTO(
        @NotNull Long clienteId,
        @NotBlank String enderecoEntrega,
        @NotEmpty List<ItemPedidoDTO> itens
) {}
