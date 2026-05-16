package com.duoc.logihub.auth.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Define que esta clase es una tabla en la BD
@Table(name = "usuarios") // Nombre de la tabla en MySQL
@Data
public class Usuario {

    @Id // Llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental en MySQL
    private Long id;

    @Column(unique = true, nullable = false) // El username no se puede repetir
    private String username;

    @Column(nullable = false)
    private String password;

    private String rol;
}