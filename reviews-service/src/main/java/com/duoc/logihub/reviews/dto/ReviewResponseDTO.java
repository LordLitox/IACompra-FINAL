package com.duoc.logihub.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    private Long id;
    private Long productoId;
    private Long usuarioId;
    private int calificacion;
    private String comentario;
    private LocalDateTime fechaCreacion;
    private String mensaje;
}