package com.webex.webex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webex.webex.model.Pedido;

@Repository 
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
