package com.farmacia.msinventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.Map;

@RestController
public class StatusController {

    @GetMapping("/")
    public ResponseEntity<?> estado() {

        return ResponseEntity.ok(
                Map.of(
                        "servicio", "msinventario",
                        "estado", "activo",
                        "mensaje", "Microservicio de inventario funcionando correctamente"
                )
        );
    }
}