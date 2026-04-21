package br.com.pires.api_gerenciamento_de_pedidos.controller;

import br.com.pires.api_gerenciamento_de_pedidos.dto.PedidoRequestDTO;
import br.com.pires.api_gerenciamento_de_pedidos.dto.PedidoResponseDTO;
import br.com.pires.api_gerenciamento_de_pedidos.model.enums.StatusPedido;
import br.com.pires.api_gerenciamento_de_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO criarPedido(@RequestBody @Valid PedidoRequestDTO request) {
        return pedidoService.criarPedido(request);
    }
    @GetMapping
    public List<PedidoResponseDTO> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    @GetMapping("/{id}")
    public PedidoResponseDTO buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @PatchMapping("/{id}/aceitar")
    public PedidoResponseDTO aceitar(@PathVariable Long id) {
        return pedidoService.atualizarStatus(id, StatusPedido.ACEITO);
    }

    @PatchMapping("/{id}/em-rota")
    public PedidoResponseDTO emRota(@PathVariable Long id) {
        return pedidoService.atualizarStatus(id, StatusPedido.EM_ROTA);
    }

    @PatchMapping("/{id}/entregar")
    public PedidoResponseDTO entregar(@PathVariable Long id) {
        return pedidoService.atualizarStatus(id, StatusPedido.ENTREGUE);
    }

    @PatchMapping("/{id}/cancelar")
    public PedidoResponseDTO cancelar(@PathVariable Long id) {
        return pedidoService.atualizarStatus(id, StatusPedido.CANCELADO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
    }
}