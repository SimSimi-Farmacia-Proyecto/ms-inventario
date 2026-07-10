package com.farmacia.msinventario.repository;
import com.farmacia.msinventario.model.Inventario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InventarioRepositoryTest {

    @Autowired
    private InventarioRepository repository;

    @Test
    void findByMedicamentoId() {

        Inventario inventario = Inventario.builder()
                .medicamentoId(100L)
                .cantidad(50)
                .build();

        repository.save(inventario);

        Optional<Inventario> resultado =
                repository.findByMedicamentoId(100L);

        assertTrue(resultado.isPresent());
        assertEquals(50,
                resultado.get().getCantidad());
    }
}


