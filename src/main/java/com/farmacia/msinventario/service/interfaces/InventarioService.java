package com.farmacia.msinventario.service.interfaces;

import com.farmacia.msinventario.dto.stock.ActualizarStockDTO;
import com.farmacia.msinventario.dto.request.InventarioRequestDTO;
import com.farmacia.msinventario.dto.response.InventarioResponseDTO;

import java.util.List;

public interface InventarioService {
    InventarioResponseDTO crearOActualizar(
            InventarioRequestDTO dto
    );
    InventarioResponseDTO obtenerPorMedicamentoId(
            Long medicamentoId
    );

    List<InventarioResponseDTO> listarInventario();
    void descontarStock(ActualizarStockDTO dto);

    InventarioResponseDTO actualizarInventario(Long id, InventarioRequestDTO dto);
    void eliminarInventario(Long id);

}


