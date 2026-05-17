package com.duoc.logihub.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingResponseDTO {
    private Long id;
    private Long envioId;
    private String ubicacionActual;
    private String detalle;
    private LocalDateTime ultimaActualizacion;
    private String mensaje;
}