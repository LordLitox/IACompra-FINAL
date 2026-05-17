package com.duoc.logihub.reviews.service;

import com.duoc.logihub.reviews.dto.ReviewRequestDTO;
import com.duoc.logihub.reviews.dto.ReviewResponseDTO;
import com.duoc.logihub.reviews.model.Resena;
import com.duoc.logihub.reviews.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewRepository repo;

    public ReviewService(ReviewRepository repo) {
        this.repo = repo;
    }

    public List<ReviewResponseDTO> listarTodas() {
        return repo.findAll().stream()
                .map(r -> mapToResponse(r, "Reseña recuperada"))
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDTO> obtenerPorProducto(Long productoId) {
        return repo.findByProductoId(productoId).stream()
                .map(r -> mapToResponse(r, "Reseña de producto encontrada"))
                .collect(Collectors.toList());
    }

    public ReviewResponseDTO buscarPorId(Long id) {
        return repo.findById(id)
                .map(r -> mapToResponse(r, "Detalle de reseña encontrado"))
                .orElse(null);
    }

    public ReviewResponseDTO publicarResena(ReviewRequestDTO dto) {
        logger.info("Publicando reseña para producto ID: {}", dto.getProductoId());
        Resena resena = new Resena();
        resena.setProductoId(dto.getProductoId());
        resena.setUsuarioId(dto.getUsuarioId());
        resena.setCalificacion(dto.getCalificacion());
        resena.setComentario(dto.getComentario());

        Resena guardada = repo.save(resena);
        return mapToResponse(guardada, "Reseña publicada con éxito");
    }

    public ReviewResponseDTO editar(Long id, ReviewRequestDTO dto) {
        return repo.findById(id).map(r -> {
            r.setCalificacion(dto.getCalificacion());
            r.setComentario(dto.getComentario());
            Resena actualizada = repo.save(r);
            return mapToResponse(actualizada, "Reseña actualizada correctamente");
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    private ReviewResponseDTO mapToResponse(Resena r, String mensaje) {
        return new ReviewResponseDTO(r.getId(), r.getProductoId(), r.getUsuarioId(), 
                r.getCalificacion(), r.getComentario(), r.getFechaCreacion(), mensaje);
    }
}