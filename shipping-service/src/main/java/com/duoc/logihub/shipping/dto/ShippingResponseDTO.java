package com.duoc.logihub.shipping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingResponseDTO {
    private Long id;
    private Long pedidoId;
    private String direccionDestino;
    private String transportista;
    private String estadoEnvio;
    private String mensaje;
}