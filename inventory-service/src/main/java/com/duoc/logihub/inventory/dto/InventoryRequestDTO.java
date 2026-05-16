package com.duoc.logihub.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequestDTO {
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private int cantidad;

    @NotBlank(message="El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La ubicación de la bodega es obligatoria")
    private String bodega;
}