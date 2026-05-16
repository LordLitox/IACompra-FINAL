package com.duoc.logihub.payments.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    @NotNull(message = "El ID del pedido es obligatorio")
    private Long pedidoId;

    @Min(value = 1, message = "El monto debe ser positivo")
    private double monto;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodo;

    private String estado; // Opcional en el request, se puede setear por defecto
}