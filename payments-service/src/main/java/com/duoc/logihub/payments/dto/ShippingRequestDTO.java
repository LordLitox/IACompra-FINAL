package com.duoc.logihub.payments.dto;

import lombok.Data;

@Data
public class ShippingRequestDTO {
    private Long pedidoId;
    private String direccionDestino;
    private String transportista;
    private String estadoEnvio;
}