package br.com.pires.api_gerenciamento_de_pedidos.controller;

import br.com.pires.api_gerenciamento_de_pedidos.dto.LoginRequestDTO;
import br.com.pires.api_gerenciamento_de_pedidos.dto.LoginResponseDTO;
import br.com.pires.api_gerenciamento_de_pedidos.dto.RegisterRequestDTO;
import br.com.pires.api_gerenciamento_de_pedidos.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
