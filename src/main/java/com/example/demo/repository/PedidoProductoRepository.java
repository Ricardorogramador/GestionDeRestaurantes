package com.example.demo.repository;

import com.example.demo.model.PedidoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoProductoRepository extends JpaRepository<PedidoProducto, Long> {}