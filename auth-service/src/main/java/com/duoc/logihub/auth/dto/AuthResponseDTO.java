package com.duoc.logihub.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automáticamente Getters, Setters y toString
@NoArgsConstructor // Requerido para que Jackson pueda crear el objeto desde un JSON
@AllArgsConstructor // Permite crear el objeto con todos sus datos en una sola línea
public class AuthResponseDTO {
    private Long id;
    private String username;
    private String rol;
    private String token; // Según la guía, sirve para simular la sesión del usuario
    private String mensaje; // Útil para enviar confirmaciones al frontend
}