package com.duoc.logihub.notifications.service;

import com.duoc.logihub.notifications.dto.NotificationRequestDTO;
import com.duoc.logihub.notifications.dto.NotificationResponseDTO;
import com.duoc.logihub.notifications.model.Notificacion;
import com.duoc.logihub.notifications.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public List<NotificationResponseDTO> listarTodas() {
        return repo.findAll().stream()
                .map(n -> mapToResponse(n, "Notificación recuperada"))
                .collect(Collectors.toList());
    }

    public NotificationResponseDTO buscarPorId(Long id) {
        return repo.findById(id)
                .map(n -> mapToResponse(n, "Detalle encontrado"))
                .orElse(null);
    }

    public NotificationResponseDTO enviar(NotificationRequestDTO dto) {
        logger.info("Enviando {} a: {}", dto.getTipo(), dto.getDestinatario());
        Notificacion n = new Notificacion();
        n.setDestinatario(dto.getDestinatario());
        n.setMensaje(dto.getMensaje());
        n.setTipo(dto.getTipo());

        Notificacion guardada = repo.save(n);
        return mapToResponse(guardada, "Notificación enviada con éxito");
    }

    public NotificationResponseDTO editar(Long id, NotificationRequestDTO dto) {
        return repo.findById(id).map(n -> {
            n.setDestinatario(dto.getDestinatario());
            n.setMensaje(dto.getMensaje());
            n.setTipo(dto.getTipo());
            Notificacion actualizada = repo.save(n);
            return mapToResponse(actualizada, "Notificación actualizada");
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    private NotificationResponseDTO mapToResponse(Notificacion n, String mensaje) {
        return new NotificationResponseDTO(n.getId(), n.getDestinatario(), 
                n.getTipo(), n.getFechaEnvio(), mensaje);
    }
}