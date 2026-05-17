package com.duoc.logihub.tracking.controller;

import com.duoc.logihub.tracking.dto.TrackingRequestDTO;
import com.duoc.logihub.tracking.dto.TrackingResponseDTO;
import com.duoc.logihub.tracking.service.TrackingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    private final TrackingService service;

    public TrackingController(TrackingService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<TrackingResponseDTO>> getAll() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/envio/{envioId}")
    public ResponseEntity<List<TrackingResponseDTO>> getHistory(@PathVariable Long envioId) {
        return ResponseEntity.ok(service.obtenerHistorial(envioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackingResponseDTO> getById(@PathVariable Long id) {
        TrackingResponseDTO res = service.buscarPorId(id);
        return res != null ? ResponseEntity.ok(res) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TrackingResponseDTO> create(@Valid @RequestBody TrackingRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarMovimiento(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrackingResponseDTO> update(@PathVariable Long id, @Valid @RequestBody TrackingRequestDTO dto) {
        TrackingResponseDTO res = service.editar(id, dto);
        return res != null ? ResponseEntity.ok(res) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}