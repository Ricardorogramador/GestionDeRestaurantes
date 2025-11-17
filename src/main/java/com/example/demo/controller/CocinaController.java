package com.example.demo.controller;

import com.example.demo.model.EstadoPedido;
import com.example.demo.model.Pedido;
import com.example.demo.repository.PedidoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CocinaController {

    private final PedidoRepository pedidoRepository;

    public CocinaController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping("/cocina")
    public String verPedidosCocina(Model model) {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDia = hoy.atStartOfDay();
        LocalDateTime finDia = hoy.atTime(23, 59, 59);

        List<Pedido> pedidos = pedidoRepository.findByEstadoInAndFechaCreacionBetween(
                List.of(EstadoPedido.PENDIENTE, EstadoPedido.PREPARACION),
                inicioDia, finDia
        );
        model.addAttribute("pedidos", pedidos);
        return "cocina_pedidos";
    }

    @PostMapping("/cocina/actualizar/{id}")
    public String actualizarEstado(@PathVariable Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido != null) {
            if (pedido.getEstado() == EstadoPedido.PENDIENTE) {
                pedido.setEstado(EstadoPedido.PREPARACION);
            } else if (pedido.getEstado() == EstadoPedido.PREPARACION) {
                pedido.setEstado(EstadoPedido.TERMINADO);
            }
            pedidoRepository.save(pedido);
        }
        return "redirect:/cocina";
    }
}