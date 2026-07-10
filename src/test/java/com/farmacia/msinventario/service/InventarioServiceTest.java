package com.farmacia.msinventario.service;
import com.farmacia.msinventario.dto.request.InventarioRequestDTO;
import com.farmacia.msinventario.dto.response.InventarioResponseDTO;
import com.farmacia.msinventario.dto.stock.ActualizarStockDTO;
import com.farmacia.msinventario.exception.ResourceNotFoundException;
import com.farmacia.msinventario.exception.StockInsuficienteException;
import com.farmacia.msinventario.mapper.InventarioMapper;
import com.farmacia.msinventario.model.Inventario;
import com.farmacia.msinventario.repository.InventarioRepository;
import com.farmacia.msinventario.service.impl.InventarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository repository;

    @Spy
    private InventarioMapper mapper;

    @InjectMocks
    private InventarioServiceImpl service;

    private Inventario inventarioBase;

    @BeforeEach
    void setUp() {

        inventarioBase = Inventario.builder()
                .id(1L)
                .medicamentoId(100L)
                .cantidad(50)
                .actualizadoEn(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Debe crear un nuevo inventario cuando no existe")
    void crearOActualizar_NuevoMedicamento() {

        InventarioRequestDTO request = new InventarioRequestDTO();
        request.setMedicamentoId(100L);
        request.setCantidad(50);

        when(repository.findByMedicamentoId(100L))
                .thenReturn(Optional.empty());

        when(repository.save(any(Inventario.class)))
                .thenReturn(inventarioBase);

        InventarioResponseDTO response =
                service.crearOActualizar(request);

        assertNotNull(response);
        assertEquals(100L, response.getMedicamentoId());
        assertEquals(50, response.getCantidad());

        verify(repository).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Debe actualizar un inventario existente")
    void crearOActualizar_Existente() {

        InventarioRequestDTO request = new InventarioRequestDTO();
        request.setMedicamentoId(100L);
        request.setCantidad(70);

        when(repository.findByMedicamentoId(100L))
                .thenReturn(Optional.of(inventarioBase));

        Inventario actualizado = Inventario.builder()
                .id(1L)
                .medicamentoId(100L)
                .cantidad(70)
                .actualizadoEn(LocalDateTime.now())
                .build();

        when(repository.save(any(Inventario.class)))
                .thenReturn(actualizado);

        InventarioResponseDTO response =
                service.crearOActualizar(request);

        assertNotNull(response);
        assertEquals(70, response.getCantidad());

        verify(repository).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Debe obtener inventario por medicamento correctamente")
    void obtenerPorMedicamentoId_Exitoso() {

        when(repository.findByMedicamentoId(100L))
                .thenReturn(Optional.of(inventarioBase));

        InventarioResponseDTO response =
                service.obtenerPorMedicamentoId(100L);

        assertNotNull(response);
        assertEquals(100L, response.getMedicamentoId());
        assertEquals(50, response.getCantidad());

        verify(repository).findByMedicamentoId(100L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el inventario no existe")
    void obtenerPorMedicamentoId_NoEncontrado() {

        when(repository.findByMedicamentoId(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.obtenerPorMedicamentoId(999L)
        );
    }

    @Test
    @DisplayName("Debe listar todos los inventarios")
    void listarInventario_Exitoso() {

        Inventario inventario2 = Inventario.builder()
                .id(2L)
                .medicamentoId(200L)
                .cantidad(80)
                .actualizadoEn(LocalDateTime.now())
                .build();

        when(repository.findAll())
                .thenReturn(List.of(inventarioBase, inventario2));

        List<InventarioResponseDTO> resultado =
                service.listarInventario();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(repository).findAll();
    }

    @Test
    @DisplayName("Debe descontar stock correctamente")
    void descontarStock_Exitoso() {

        ActualizarStockDTO dto = new ActualizarStockDTO();
        dto.setMedicamentoId(100L);
        dto.setCantidad(20);

        when(repository.findByMedicamentoId(100L))
                .thenReturn(Optional.of(inventarioBase));

        service.descontarStock(dto);

        assertEquals(30, inventarioBase.getCantidad());

        verify(repository).save(inventarioBase);
    }

    @Test
    @DisplayName("Debe lanzar excepción por stock insuficiente")
    void descontarStock_Insuficiente() {

        ActualizarStockDTO dto = new ActualizarStockDTO();
        dto.setMedicamentoId(100L);
        dto.setCantidad(60);

        when(repository.findByMedicamentoId(100L))
                .thenReturn(Optional.of(inventarioBase));

        assertThrows(
                StockInsuficienteException.class,
                () -> service.descontarStock(dto)
        );

        verify(repository, never())
                .save(any(Inventario.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el medicamento no existe")
    void descontarStock_MedicamentoNoExiste() {

        ActualizarStockDTO dto = new ActualizarStockDTO();
        dto.setMedicamentoId(999L);
        dto.setCantidad(10);

        when(repository.findByMedicamentoId(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.descontarStock(dto)
        );

        verify(repository, never())
                .save(any(Inventario.class));
    }
}
