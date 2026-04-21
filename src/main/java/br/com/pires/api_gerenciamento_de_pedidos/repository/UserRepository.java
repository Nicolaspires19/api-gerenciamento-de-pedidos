package br.com.pires.api_gerenciamento_de_pedidos.repository;

import br.com.pires.api_gerenciamento_de_pedidos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

    public interface UserRepository extends JpaRepository<User, Long> {

        Optional<User> findByEmail(String email);

    }

