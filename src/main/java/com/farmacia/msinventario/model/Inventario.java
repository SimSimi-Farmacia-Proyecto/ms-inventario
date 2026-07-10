package com.farmacia.msinventario.model;
import jakarta.persistence.*;
        import lombok.*;

        import java.time.LocalDateTime;

@Entity
@Table(name = "inventario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medicamento_id", nullable = false, unique = true)
    private Long medicamentoId;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;

    @PrePersist
    @PreUpdate
    public void actualizarFecha() {
        this.actualizadoEn = LocalDateTime.now();
    }
}