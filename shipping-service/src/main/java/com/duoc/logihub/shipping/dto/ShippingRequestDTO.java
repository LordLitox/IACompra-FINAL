package com.duoc.logihub.shipping.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ShippingRequestDTO {
    @NotNull(message = "El ID del pedido es obligatorio")
    private Long pedidoId;

    @NotBlank(message = "La dirección de destino es obligatoria")
    private String direccionDestino;

    @NotBlank(message = "El transportista es obligatorio")
    private String transportista;

    private String estadoEnvio; 
}