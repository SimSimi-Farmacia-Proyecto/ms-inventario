package com.farmacia.msinventario.controller;

import com.farmacia.msinventario.dto.stock.ActualizarStockDTO;
import com.farmacia.msinventario.dto.request.InventarioRequestDTO;
import com.farmacia.msinventario.dto.response.InventarioResponseDTO;
import com.farmacia.msinventario.service.interfaces.InventarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/v1/inventarios")
public class InventarioController {

    private final InventarioService service;

    @Autowired
    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InventarioResponseDTO> crearOActualizar(
            @Valid @RequestBody InventarioRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crearOActualizar(dto));
    }

    @GetMapping("/medicamento/{medicamentoId}")
    public ResponseEntity<InventarioResponseDTO> obtenerPorMedicamento(
            @PathVariable Long medicamentoId) {

        return ResponseEntity.ok(
                service.obtenerPorMedicamentoId(medicamentoId)
        );
    }

    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listarInventario()
        );
    }

    @PostMapping("/reducciones")
    public ResponseEntity<Void> descontar(
            @Valid @RequestBody ActualizarStockDTO dto) {

        service.descontarStock(dto);

        return ResponseEntity.noContent().build();
    }
}
