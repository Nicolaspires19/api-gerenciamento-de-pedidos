package br.com.pires.api_gerenciamento_de_pedidos.model.enums;

public enum StatusPedido {
    PENDENTE,
    ACEITO,
    EM_ROTA,
    ENTREGUE,
    CANCELADO;

    public boolean podeTransicionarPara(StatusPedido proximo) {
        return switch (this) {
            case PENDENTE -> proximo == ACEITO || proximo == CANCELADO;
            case ACEITO -> proximo == EM_ROTA || proximo == CANCELADO;
            case EM_ROTA -> proximo == ENTREGUE;
            default -> false;
        };
    }
}
