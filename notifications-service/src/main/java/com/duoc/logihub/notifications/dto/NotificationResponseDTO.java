package com.duoc.logihub.notifications.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDTO {
    private Long id;
    private String destinatario;
    private String tipo;
    private LocalDateTime fechaEnvio;
    private String mensajeEstado;
}