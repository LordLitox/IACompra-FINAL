package com.duoc.logihub.payments.client;

import com.duoc.logihub.payments.dto.ShippingRequestDTO;
import com.duoc.logihub.payments.dto.ShippingResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Importante: El nombre debe coincidir con spring.application.name del shipping-service
@FeignClient(name = "shipping-service", url = "http://localhost:8087")
public interface ShippingClient {

    @PostMapping("/api/shipping")
    ShippingResponseDTO crearEnvio(@RequestBody ShippingRequestDTO dto);
}