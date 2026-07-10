package com.farmacia.msinventario.repository;

import com.farmacia.msinventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InventarioRepository
        extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByMedicamentoId(Long medicamentoId);
}
