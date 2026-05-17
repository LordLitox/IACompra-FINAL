package com.duoc.logihub.reviews.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReviewRequestDTO {
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private int calificacion;

    @NotBlank(message = "El comentario no puede estar vacío")
    @Size(max = 1000, message = "El comentario es demasiado largo")
    private String comentario;
}