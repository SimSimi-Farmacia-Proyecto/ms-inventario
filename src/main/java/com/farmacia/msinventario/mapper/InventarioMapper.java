package com.farmacia.msinventario.mapper;

import com.farmacia.msinventario.dto.request.InventarioRequestDTO;
import com.farmacia.msinventario.dto.response.InventarioResponseDTO;
import com.farmacia.msinventario.model.Inventario;
import org.springframework.stereotype.Component;

@Component
public class InventarioMapper {

    public Inventario toEntity(InventarioRequestDTO dto) {

        Inventario inventario = new Inventario();

        inventario.setMedicamentoId(dto.getMedicamentoId());
        inventario.setCantidad(dto.getCantidad());

        return inventario;
    }


    public InventarioResponseDTO toDTO(Inventario inventario) {

        InventarioResponseDTO dto = new InventarioResponseDTO();

        dto.setId(inventario.getId());
        dto.setMedicamentoId(inventario.getMedicamentoId());
        dto.setCantidad(inventario.getCantidad());
        dto.setActualizadoEn(inventario.getActualizadoEn());

        return dto;
    }
}
