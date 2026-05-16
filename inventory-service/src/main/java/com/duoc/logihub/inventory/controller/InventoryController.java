package com.duoc.logihub.inventory.controller;

import com.duoc.logihub.inventory.dto.InventoryRequestDTO;
import com.duoc.logihub.inventory.dto.InventoryResponseDTO;
import com.duoc.logihub.inventory.service.InventoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAll() {
        return ResponseEntity.ok(service.listarTodo());
    }

    @GetMapping("/{productoId}")
    public ResponseEntity<InventoryResponseDTO> getStock(@PathVariable Long productoId) {
        InventoryResponseDTO response = service.obtenerPorProducto(productoId);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @PostMapping("/update")
    public ResponseEntity<InventoryResponseDTO> update(@Valid @RequestBody InventoryRequestDTO dto) {
        return ResponseEntity.ok(service.actualizarStock(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody InventoryRequestDTO dto) {
        InventoryResponseDTO actualizado = service.editar(id, dto);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content si borró
        }
        return ResponseEntity.notFound().build(); // Retorna 404 si el ID no existía
    }

    @PutMapping("/descontar")
    public ResponseEntity<String> descontar(@RequestParam Long productoId, @RequestParam int cantidad) {
        boolean exito = service.descontarStock(productoId, cantidad);
        return exito ? ResponseEntity.ok("Stock actualizado") : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sin stock");
    }
}