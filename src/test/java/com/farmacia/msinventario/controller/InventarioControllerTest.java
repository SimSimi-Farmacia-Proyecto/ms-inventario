package com.farmacia.msinventario.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.farmacia.msinventario.dto.request.InventarioRequestDTO;
import com.farmacia.msinventario.dto.response.InventarioResponseDTO;
import com.farmacia.msinventario.service.interfaces.InventarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventarioController.class)
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventarioService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarInventario() throws Exception {

        InventarioResponseDTO dto =
                new InventarioResponseDTO(
                        1L,
                        100L,
                        50,
                        LocalDateTime.now()
                );

        when(service.listarInventario())
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/inventarios"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerPorMedicamento() throws Exception {

        InventarioResponseDTO dto =
                new InventarioResponseDTO(
                        1L,
                        100L,
                        50,
                        LocalDateTime.now()
                );

        when(service.obtenerPorMedicamentoId(100L))
                .thenReturn(dto);

        mockMvc.perform(
                        get("/api/v1/inventarios/medicamento/100")
                )
                .andExpect(status().isOk());
    }

    @Test
    void crearInventario() throws Exception {

        InventarioRequestDTO request =
                new InventarioRequestDTO();

        request.setMedicamentoId(100L);
        request.setCantidad(50);

        InventarioResponseDTO response =
                new InventarioResponseDTO(
                        1L,
                        100L,
                        50,
                        LocalDateTime.now()
                );

        when(service.crearOActualizar(request))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/inventarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        ))
                .andExpect(status().isCreated());
    }
}
