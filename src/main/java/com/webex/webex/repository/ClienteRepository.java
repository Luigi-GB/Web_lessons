package com.webex.webex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.webex.webex.model.Cliente;

@Repository 
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    @Query (value = "SELECT * FROM cliente WHERE email = :email AND senha = :senha", nativeQuery = true)
    Optional<Cliente> realizarLogin(@Param("email") String email, @Param("senha") String senha);

    Optional<Cliente> findByEmail(String email);
    
    // Novo método para buscar pelo token de recuperação
    Optional<Cliente> findByToken(String token);
}

