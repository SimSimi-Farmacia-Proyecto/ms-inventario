package com.farmacia.msinventario.config;

import com.farmacia.msinventario.model.Inventario;
import com.farmacia.msinventario.repository.InventarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {


    @Bean
    CommandLineRunner initData(InventarioRepository repository) {

        return args -> {

            if (repository.count() == 0) {

                Inventario inventario1 = Inventario.builder()
                        .medicamentoId(1L)
                        .cantidad(100)
                        .build();


                Inventario inventario2 = Inventario.builder()
                        .medicamentoId(2L)
                        .cantidad(50)
                        .build();


                Inventario inventario3 = Inventario.builder()
                        .medicamentoId(3L)
                        .cantidad(200)
                        .build();


                repository.save(inventario1);
                repository.save(inventario2);
                repository.save(inventario3);

                System.out.println("Datos demo de inventario cargados correctamente");
            }

        };
    }
}
