package com.duoc.logihub.orders.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "http://localhost:8084")
public interface InventoryClient {
    @PutMapping("/api/inventory/descontar")
    void descontar(@RequestParam("productoId") Long productoId, @RequestParam("cantidad") int cantidad);
}