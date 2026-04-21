package br.com.pires.api_gerenciamento_de_pedidos.exceptions;

public class StatusInvalidoException extends RuntimeException {
    public StatusInvalidoException(String msg) { super(msg); }
}
