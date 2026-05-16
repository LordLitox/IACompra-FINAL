package com.duoc.logihub.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String nombre;
    private int precio;
    private boolean disponible;
    private String categoria;
    private String descripcion;
    private String mensaje;
}