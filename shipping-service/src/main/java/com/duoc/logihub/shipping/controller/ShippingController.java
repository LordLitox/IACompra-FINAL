package com.duoc.logihub.shipping.controller;

import com.duoc.logihub.shipping.dto.ShippingRequestDTO;
import com.duoc.logihub.shipping.dto.ShippingResponseDTO;
import com.duoc.logihub.shipping.service.ShippingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

    private final ShippingService service;

    public ShippingController(ShippingService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ShippingResponseDTO>> getAll() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingResponseDTO> getById(@PathVariable Long id) {
        ShippingResponseDTO res = service.buscarPorId(id);
        return res != null ? ResponseEntity.ok(res) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ShippingResponseDTO> create(@Valid @RequestBody ShippingRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearEnvio(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShippingResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ShippingRequestDTO dto) {
        ShippingResponseDTO res = service.editar(id, dto);
        return res != null ? ResponseEntity.ok(res) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}