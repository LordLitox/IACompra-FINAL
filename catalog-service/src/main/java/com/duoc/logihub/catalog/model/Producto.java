package com.duoc.logihub.catalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "productos")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio") 
    private String nombre;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;

    @Min(value = 1, message = "El precio debe ser mayor que cero")
    private int precio; 

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    private boolean disponible; 
}