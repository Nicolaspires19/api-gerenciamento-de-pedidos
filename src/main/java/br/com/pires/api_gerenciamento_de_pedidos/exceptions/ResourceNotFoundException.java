package br.com.pires.api_gerenciamento_de_pedidos.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) { super(msg); }
}
