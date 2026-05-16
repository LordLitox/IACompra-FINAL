package com.duoc.logihub.catalog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // Anotación para capturar excepciones en todos los controladores [cite: 335]
public class GlobalExceptionHandler {

    // Captura específicamente errores de validación de los DTOs (@Valid) [cite: 337, 338]
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErroresValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        
        // Recorremos cada error encontrado y lo guardamos en un mapa [cite: 340]
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage()); 
        });
        
        // Retornamos un 400 Bad Request con la lista de errores [cite: 343]
        return ResponseEntity.badRequest().body(errores);
    }
}