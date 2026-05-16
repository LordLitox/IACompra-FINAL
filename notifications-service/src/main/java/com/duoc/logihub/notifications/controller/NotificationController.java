package com.duoc.logihub.notifications.controller;

import com.duoc.logihub.notifications.dto.NotificationRequestDTO;
import com.duoc.logihub.notifications.dto.NotificationResponseDTO;
import com.duoc.logihub.notifications.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDTO>> getAll() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> getById(@PathVariable Long id) {
        NotificationResponseDTO res = service.buscarPorId(id);
        return res != null ? ResponseEntity.ok(res) : ResponseEntity.notFound().build();
    }

    @PostMapping("/send")
    public ResponseEntity<NotificationResponseDTO> send(@Valid @RequestBody NotificationRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.enviar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> update(@PathVariable Long id, @Valid @RequestBody NotificationRequestDTO dto) {
        NotificationResponseDTO res = service.editar(id, dto);
        return res != null ? ResponseEntity.ok(res) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}