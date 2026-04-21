package br.com.pires.api_gerenciamento_de_pedidos.service;


import br.com.pires.api_gerenciamento_de_pedidos.dto.ItemPedidoDTO;
import br.com.pires.api_gerenciamento_de_pedidos.dto.PedidoRequestDTO;
import br.com.pires.api_gerenciamento_de_pedidos.dto.PedidoResponseDTO;
import br.com.pires.api_gerenciamento_de_pedidos.exceptions.ResourceNotFoundException;
import br.com.pires.api_gerenciamento_de_pedidos.exceptions.StatusInvalidoException;
import br.com.pires.api_gerenciamento_de_pedidos.model.ItemPedido;
import br.com.pires.api_gerenciamento_de_pedidos.model.Pedido;
import br.com.pires.api_gerenciamento_de_pedidos.model.User;
import br.com.pires.api_gerenciamento_de_pedidos.model.enums.StatusPedido;
import br.com.pires.api_gerenciamento_de_pedidos.repository.PedidoRepository;
import br.com.pires.api_gerenciamento_de_pedidos.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UserRepository  userRepository;

    public PedidoResponseDTO criarPedido(@Valid PedidoRequestDTO request) {
        User cliente = userRepository.findById(request.clienteId()).
                orElseThrow(() -> new ResourceNotFoundException("Cliente Não Encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEnderecoEntrega(request.enderecoEntrega());

        List<ItemPedido> itens = request.itens().stream().map(dto -> {
            ItemPedido item = new ItemPedido();
            item.setDescricao(dto.descricao());
            item.setQuantidade(dto.quantidade());
            item.setPrecoUnitario(dto.precoUnitario());
            item.setPedido(pedido);
            return item;
        }).toList();

        pedido.setItens(itens);
        pedido.setValorTotal(calcularTotal(itens));

        return toDTO(pedidoRepository.save(pedido));
    }


    public PedidoResponseDTO atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não Encontrado"));

        if (!pedido.getStatus().podeTransicionarPara(novoStatus)) {
            throw new StatusInvalidoException(
                    "Não é possivel mudar de " + pedido.getStatus() + " para " +novoStatus
            );
        }

        pedido.setStatus(novoStatus);
        return toDTO(pedidoRepository.save(pedido));
    }

    public List<PedidoResponseDTO> listarPedidos() {
        return pedidoRepository.findAll().stream().map(this::toDTO).toList();
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido Não Encontrado"));
    }

    public void deletar(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não Encontrado");
        }
        pedidoRepository.deleteById(id);
    }

    private BigDecimal calcularTotal(List<ItemPedido> itens) {
        return itens.stream()
                .map(i -> i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private PedidoResponseDTO toDTO(Pedido p) {
        return new PedidoResponseDTO(
                p.getId(), p.getEnderecoEntrega(),
                p.getStatus(), p.getValorTotal(), p.getCriadoEm()
        );
    }
}
