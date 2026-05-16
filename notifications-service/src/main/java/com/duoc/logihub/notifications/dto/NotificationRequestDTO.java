package com.duoc.logihub.notifications.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NotificationRequestDTO {
    @NotBlank(message = "El destinatario es obligatorio")
    @Email(message = "Debe ser un correo válido")
    private String destinatario;

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    @NotBlank(message = "El tipo es obligatorio (EMAIL, SMS, PUSH)")
    private String tipo;
}