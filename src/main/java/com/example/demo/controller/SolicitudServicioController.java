package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class SolicitudServicioController {

    @GetMapping("/solicitud-servicio")
    public String mostrarFormulario() {
        return "solicitud_servicio";
    }

    @PostMapping("/solicitud-servicio")
    public String procesarFormulario(
            @RequestParam String nombreRestaurante,
            @RequestParam String correo,
            @RequestParam String ubicacion,
            @RequestParam String telefono,
            @RequestParam String representante,
            @RequestParam String descripcion,
            @RequestParam String cedula,
            Model model
    ) {
        model.addAttribute("mensaje", "¡Solicitud enviada con éxito!");
        return "solicitud_servicio";
    }
}