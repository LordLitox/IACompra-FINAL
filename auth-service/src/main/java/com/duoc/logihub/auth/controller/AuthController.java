package com.duoc.logihub.auth.controller;

import com.duoc.logihub.auth.dto.AuthRequestDTO;
import com.duoc.logihub.auth.dto.AuthResponseDTO;
import com.duoc.logihub.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(dto));
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO dto) {
        String token = service.validarAcceso(dto.getUsername(), dto.getPassword());
        if (token != null) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody AuthRequestDTO dto) {
        var actualizado = service.editar(id, dto);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204
        }
        return ResponseEntity.notFound().build(); // Retorna 404
    }
    @GetMapping("/list")
    public ResponseEntity<List<AuthResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listarTodos());
    }
}