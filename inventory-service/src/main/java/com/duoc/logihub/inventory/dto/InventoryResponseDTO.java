package com.duoc.logihub.inventory.dto;

import lombok.Data;

@Data
public class InventoryResponseDTO {
    private Long id;
    private Long productoId;
    private int cantidad;
    private String bodega;
    private String nombre;
    private String mensaje;
}