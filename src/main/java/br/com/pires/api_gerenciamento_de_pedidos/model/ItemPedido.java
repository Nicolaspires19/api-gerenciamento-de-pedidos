package br.com.pires.api_gerenciamento_de_pedidos.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name  = "Item_Pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal precoUnitario;

    @Min(1)
    private int quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "pedido_id")
    private Pedido pedido;
}
