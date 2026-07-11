package com.farmacia.msinventario.service.impl;

import com.farmacia.msinventario.mapper.InventarioMapper;
import com.farmacia.msinventario.dto.stock.ActualizarStockDTO;
import com.farmacia.msinventario.dto.request.InventarioRequestDTO;
import com.farmacia.msinventario.dto.response.InventarioResponseDTO;
import com.farmacia.msinventario.exception.ResourceNotFoundException;
import com.farmacia.msinventario.exception.StockInsuficienteException;
import com.farmacia.msinventario.model.Inventario;
import com.farmacia.msinventario.repository.InventarioRepository;
import com.farmacia.msinventario.service.interfaces.InventarioService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository repository;
    private final InventarioMapper mapper;

    @Autowired
    public InventarioServiceImpl(InventarioRepository repository,
                                 InventarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public InventarioResponseDTO crearOActualizar(InventarioRequestDTO dto) {

        log.info("Creando/actualizando inventario medicamento {}", dto.getMedicamentoId());

        Inventario inventario = repository.findByMedicamentoId(dto.getMedicamentoId())
                .orElse(new Inventario());

        inventario.setMedicamentoId(dto.getMedicamentoId());
        inventario.setCantidad(dto.getCantidad());

        Inventario guardado = repository.save(inventario);

        log.info("Inventario guardado ID {}", guardado.getId());

        return mapper.toDTO(guardado);
    }


    @Override
    public InventarioResponseDTO obtenerPorMedicamentoId(Long id) {

        return repository.findByMedicamentoId(id)
                .map(mapper::toDTO)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventario no encontrado"));
    }


    @Override
    public List<InventarioResponseDTO> listarInventario() {

        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }


    @Override
    public void descontarStock(ActualizarStockDTO dto) {

        Inventario inv = repository.findByMedicamentoId(dto.getMedicamentoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventario no encontrado"));


        if (inv.getCantidad() < dto.getCantidad()) {

            log.error("Stock insuficiente para medicamento {}",
                    dto.getMedicamentoId());

            throw new StockInsuficienteException("Stock insuficiente");
        }


        inv.setCantidad(
                inv.getCantidad() - dto.getCantidad()
        );

        repository.save(inv);

        log.info("Stock actualizado correctamente");
    }

    @Override
    public InventarioResponseDTO actualizarInventario(Long id, InventarioRequestDTO dto) {

        Inventario inventario = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventario no encontrado"));

        inventario.setMedicamentoId(dto.getMedicamentoId());
        inventario.setCantidad(dto.getCantidad());

        Inventario actualizado = repository.save(inventario);

        log.info("Inventario actualizado ID {}", actualizado.getId());

        return mapper.toDTO(actualizado);
    }

    @Override
    public void eliminarInventario(Long id) {

        Inventario inventario = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventario no encontrado"));

        repository.delete(inventario);

        log.info("Inventario eliminado ID {}", id);
    }

}




