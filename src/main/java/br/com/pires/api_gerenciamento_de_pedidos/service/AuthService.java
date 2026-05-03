package br.com.pires.api_gerenciamento_de_pedidos.service;


import br.com.pires.api_gerenciamento_de_pedidos.config.JwtService;
import br.com.pires.api_gerenciamento_de_pedidos.dto.LoginRequestDTO;
import br.com.pires.api_gerenciamento_de_pedidos.dto.LoginResponseDTO;
import br.com.pires.api_gerenciamento_de_pedidos.dto.RegisterRequestDTO;
import br.com.pires.api_gerenciamento_de_pedidos.model.User;
import br.com.pires.api_gerenciamento_de_pedidos.model.enums.Role;
import br.com.pires.api_gerenciamento_de_pedidos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email já esta em uso.");
        }

        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.valueOf(request.role().toUpperCase()))
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.gerarToken(user.getEmail());
        return new LoginResponseDTO(jwtToken, "Bearer");
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = userRepository.findByEmail(request.email())
                .orElseThrow();

        var jwtToken = jwtService.gerarToken(user.getEmail());
        return new LoginResponseDTO(jwtToken, "Bearer");
    }

}
