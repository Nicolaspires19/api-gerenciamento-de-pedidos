package br.com.pires.api_gerenciamento_de_pedidos.model;

import br.com.pires.api_gerenciamento_de_pedidos.model.enums.StatusPedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter @Setter
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private User cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entregador_id")
    private User entregador;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    @NotBlank
    private String enderecoEntrega;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status = StatusPedido.PENDENTE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();


    @Column(nullable = false, updatable = false)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    //GETTERS SETTERS

}
