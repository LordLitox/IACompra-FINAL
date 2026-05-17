package com.duoc.logihub.tracking.service;

import com.duoc.logihub.tracking.dto.TrackingRequestDTO;
import com.duoc.logihub.tracking.dto.TrackingResponseDTO;
import com.duoc.logihub.tracking.model.Rastreo;
import com.duoc.logihub.tracking.repository.TrackingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackingService {
    private static final Logger logger = LoggerFactory.getLogger(TrackingService.class);
    private final TrackingRepository repo;

    public TrackingService(TrackingRepository repo) {
        this.repo = repo;
    }

    public List<TrackingResponseDTO> obtenerHistorial(Long envioId) {
        return repo.findByEnvioIdOrderByUltimaActualizacionDesc(envioId).stream()
                .map(r -> mapToResponse(r, "Punto de rastreo recuperado"))
                .collect(Collectors.toList());
    }

    public TrackingResponseDTO buscarPorId(Long id) {
        return repo.findById(id)
                .map(r -> mapToResponse(r, "Detalle de movimiento encontrado"))
                .orElse(null);
    }

    public TrackingResponseDTO registrarMovimiento(TrackingRequestDTO dto) {
        logger.info("Registrando movimiento para envío ID: {}", dto.getEnvioId());
        Rastreo rastreo = new Rastreo();
        rastreo.setEnvioId(dto.getEnvioId());
        rastreo.setUbicacionActual(dto.getUbicacionActual());
        rastreo.setDetalle(dto.getDetalle());

        Rastreo guardado = repo.save(rastreo);
        return mapToResponse(guardado, "Movimiento registrado con éxito");
    }

    public TrackingResponseDTO editar(Long id, TrackingRequestDTO dto) {
        return repo.findById(id).map(r -> {
            r.setUbicacionActual(dto.getUbicacionActual());
            r.setDetalle(dto.getDetalle());
            Rastreo actualizado = repo.save(r);
            return mapToResponse(actualizado, "Movimiento actualizado");
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    private TrackingResponseDTO mapToResponse(Rastreo r, String mensaje) {
        return new TrackingResponseDTO(r.getId(), r.getEnvioId(), r.getUbicacionActual(), 
                r.getDetalle(), r.getUltimaActualizacion(), mensaje);
    }
    public List<TrackingResponseDTO> listarTodos() {
        logger.info("Obteniendo listado global de movimientos de rastreo");
        return repo.findAll().stream()
                .map(r -> mapToResponse(r, "Registro de rastreo recuperado"))
                .collect(Collectors.toList());
    }
}