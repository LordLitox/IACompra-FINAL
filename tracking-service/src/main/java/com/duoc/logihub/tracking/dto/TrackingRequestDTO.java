package com.duoc.logihub.tracking.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TrackingRequestDTO {
    @NotNull(message = "El ID del envío es obligatorio")
    private Long envioId;

    @NotBlank(message = "La ubicación actual es obligatoria")
    private String ubicacionActual;

    @NotBlank(message = "El detalle del estado es obligatorio")
    private String detalle;
}