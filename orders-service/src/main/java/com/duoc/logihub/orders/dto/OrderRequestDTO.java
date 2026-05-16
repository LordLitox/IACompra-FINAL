package com.duoc.logihub.orders.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderRequestDTO {
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @Min(value = 1, message = "La cantidad mínima debe ser 1")
    private int cantidad;

    private double total;
}