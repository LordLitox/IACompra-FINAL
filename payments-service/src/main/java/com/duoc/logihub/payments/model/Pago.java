package com.duoc.logihub.payments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "pagos")
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long pedidoId;
    @Min(value = 1, message = "El precio debe ser mayor que cero")
    private double monto;
    @NotBlank(message = "Efectivo o tarjeta debito/credito")
    private String metodo;
    private String estado;
}