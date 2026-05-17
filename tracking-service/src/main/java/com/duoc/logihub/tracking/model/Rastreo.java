package com.duoc.logihub.tracking.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "rastreos")
@Data
public class Rastreo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long envioId;

    @Column(nullable = false)
    private String ubicacionActual;

    @Column(nullable = false)
    private String detalle;

    private LocalDateTime ultimaActualizacion;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.ultimaActualizacion = LocalDateTime.now();
    }
}