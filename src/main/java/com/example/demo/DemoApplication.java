package com.example.demo;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner cargarProductos(ProductoRepository productoRepo) {
        return args -> {
            if (productoRepo.count() == 0) {
                Producto hamburguesa = new Producto();
                hamburguesa.setNombre("Hamburguesa");
                hamburguesa.setPrecio(5.0);
                productoRepo.save(hamburguesa);

                Producto pizza = new Producto();
                pizza.setNombre("Pizza");
                pizza.setPrecio(8.0);
                productoRepo.save(pizza);

                Producto refresco = new Producto();
                refresco.setNombre("Refresco");
                refresco.setPrecio(2.0);
                productoRepo.save(refresco);
            }
        };
    }
}