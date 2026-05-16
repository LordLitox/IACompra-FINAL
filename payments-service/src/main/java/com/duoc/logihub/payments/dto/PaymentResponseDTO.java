package com.duoc.logihub.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    private Long id;
    private Long pedidoId;
    private double monto;
    private String metodo;
    private String estado;
    private String mensaje;
}