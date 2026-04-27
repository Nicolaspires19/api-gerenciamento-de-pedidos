package br.com.pires.api_gerenciamento_de_pedidos.repository;

import br.com.pires.api_gerenciamento_de_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> { }
