package com.example.demo.repository;

import com.example.demo.model.EstadoPedido;
import com.example.demo.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Pedido> findByEstadoInAndFechaCreacionBetween(List<EstadoPedido> estados, LocalDateTime inicio, LocalDateTime fin);
}