package com.duoc.logihub.catalog.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor que cero")
    private Integer precio; // Usamos Integer para que @NotNull funcione correctamente

    @NotNull(message = "Debe indicar si el producto está disponible")
    private Boolean disponible;

    @NotBlank(message = "La categoria es obligatoria")
    private String categoria;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;
}