package com.duoc.logihub.payments.client;

import com.duoc.logihub.payments.dto.OrderResponseDTO; // Importa tu DTO
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

// La URL apunta a la base del servicio (host:puerto)
@FeignClient(name = "orders-service", url = "http://localhost:8085") 
public interface OrderClient {

    // El GetMapping completa la ruta específica
    @GetMapping("/api/orders/{id}")
    OrderResponseDTO getOrderById(@PathVariable("id") Long id); 

    @PutMapping("/api/orders/{id}/status")
    void updateOrderStatus(@PathVariable("id") Long id, @RequestParam("nuevoEstado") String nuevoEstado);

}