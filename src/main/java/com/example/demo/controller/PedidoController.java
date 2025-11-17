package com.example.demo.controller;

import com.example.demo.model.EstadoPedido;
import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.PedidoProducto;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.PedidoProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final PedidoProductoRepository pedidoProductoRepository;

    public PedidoController(PedidoRepository pedidoRepository, ProductoRepository productoRepository, PedidoProductoRepository pedidoProductoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.pedidoProductoRepository = pedidoProductoRepository;
    }


    @GetMapping("/pedidos")
    public String areaPedidos(Model model) {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDia = hoy.atStartOfDay();
        LocalDateTime finDia = hoy.atTime(23, 59, 59);

        List<Pedido> pedidos = pedidoRepository.findByFechaCreacionBetween(inicioDia, finDia);

        Map<Long, Double> totalesPorPedido = new HashMap<>();
        for (Pedido pedido : pedidos) {
            double total = 0;
            for (PedidoProducto pp : pedido.getProductos()) {
                total += pp.getProducto().getPrecio() * pp.getCantidad();
            }
            totalesPorPedido.put(pedido.getId(), total);
        }

        model.addAttribute("pedidos", pedidos);
        model.addAttribute("totalesPorPedido", totalesPorPedido);
        return "area_pedido";
    }


    @GetMapping("/pedidos/nuevo")
    public String nuevoPedido(Model model) {
        List<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "nuevo_pedido";
    }


    @PostMapping("/pedidos/nuevo")
    public String guardarPedido(
            @RequestParam("mesa") Integer mesa,
            @RequestParam("productoId") List<Long> productoIds,
            @RequestParam("cantidad") List<Integer> cantidades,
            Model model
    )

    {
        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido = pedidoRepository.save(pedido);

        List<PedidoProducto> pedidoProductos = new ArrayList<>();
        for (int i = 0; i < productoIds.size(); i++) {
            Long productoId = productoIds.get(i);
            Integer cantidad = cantidades.get(i);

            Producto producto = productoRepository.findById(productoId).orElse(null);
            if (producto != null && cantidad > 0) {
                PedidoProducto pp = new PedidoProducto();
                pp.setPedido(pedido);
                pp.setProducto(producto);
                pp.setCantidad(cantidad);
                pedidoProductos.add(pp);
            }
        }
        pedidoProductoRepository.saveAll(pedidoProductos);

        return "redirect:/pedidos";
    }
}