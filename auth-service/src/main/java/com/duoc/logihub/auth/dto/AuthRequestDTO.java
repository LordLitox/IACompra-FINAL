package com.duoc.logihub.auth.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Permite a Jackson crear el objeto vacío antes de llenarlo
@AllArgsConstructor // Útil para crear el DTO con datos rápidamente
public class AuthRequestDTO {

    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 4, max = 20, message = "El usuario debe tener entre 4 y 20 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotBlank(message = "El rol es obligatorio (ADMIN/USER)")
    private String rol;
}   