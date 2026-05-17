package com.duoc.logihub.users.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "perfiles_usuarios")
@Data
public class UsuarioPerfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombreCompleto;

    @Email(message = "Debe ser un correo electrónico válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "Formato de teléfono inválido")
    private String telefono;

    private String direccion;
    
    @NotNull(message = "El ID de autenticación es obligatorio")
    private Long authId; 
}