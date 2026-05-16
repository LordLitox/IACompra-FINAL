package com.duoc.logihub.payments.dto;

import lombok.Data;

@Data
public class ShippingResponseDTO {
    private Long id;
    private Long pedidoId;
    private String direccionDestino;
    private String transportista;
    private String estadoEnvio;
    private String mensaje;
}