package com.duoc.logihub.payments.service;

import com.duoc.logihub.payments.dto.PaymentRequestDTO;
import com.duoc.logihub.payments.dto.PaymentResponseDTO;
import com.duoc.logihub.payments.dto.ShippingRequestDTO; // Asegúrate de tener este DTO
import com.duoc.logihub.payments.model.Pago;
import com.duoc.logihub.payments.repository.PaymentRepository;
import com.duoc.logihub.payments.client.OrderClient;
import com.duoc.logihub.payments.client.ShippingClient; // Importamos el nuevo cliente
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository repo;
    private final OrderClient orderClient;
    private final ShippingClient shippingClient; // Inyección de Shipping

    // Actualizamos el constructor para incluir ShippingClient
    public PaymentService(PaymentRepository repo, OrderClient orderClient, ShippingClient shippingClient) {
        this.repo = repo;
        this.orderClient = orderClient;
        this.shippingClient = shippingClient;
    }

    public List<PaymentResponseDTO> listarTodos() {
        return repo.findAll().stream()
                .map(p -> new PaymentResponseDTO(p.getId(), p.getPedidoId(), p.getMonto(), p.getMetodo(), p.getEstado(), "Pago recuperado"))
                .collect(Collectors.toList());
    }

    public PaymentResponseDTO editar(Long id, PaymentRequestDTO dto) {
        return repo.findById(id).map(p -> {
            p.setMetodo(dto.getMetodo());
            p.setMonto(dto.getMonto());
            Pago actualizado = repo.save(p);
            return new PaymentResponseDTO(actualizado.getId(), actualizado.getPedidoId(), 
                    actualizado.getMonto(), actualizado.getMetodo(), actualizado.getEstado(), "Pago actualizado");
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public PaymentResponseDTO buscarPorId(Long id) {
        return repo.findById(id)
                .map(p -> new PaymentResponseDTO(p.getId(), p.getPedidoId(), p.getMonto(), 
                        p.getMetodo(), p.getEstado(), "Pago encontrado"))
                .orElse(null);
    }

    public PaymentResponseDTO procesarPago(PaymentRequestDTO dto) {
        // 1. Definir el estado basado en lógica de negocio
        String estadoFinal = dto.getMonto() < 100000 ? "APROBADO" : "REQUIERE_AUTORIZACION";

        // 2. Persistir el pago
        Pago pago = new Pago();
        pago.setPedidoId(dto.getPedidoId());
        pago.setMonto(dto.getMonto());
        pago.setMetodo(dto.getMetodo());
        pago.setEstado(estadoFinal);
        Pago guardado = repo.save(pago);

        // 3. Si el pago fue APROBADO, sincronizamos con Orders y Shipping
        if ("APROBADO".equals(estadoFinal)) {
            // Sincronización con Orders
            try {
                orderClient.updateOrderStatus(dto.getPedidoId(), "APROBADO");
                logger.info("Pedido {} marcado como APROBADO en orders-service", dto.getPedidoId());
            } catch (Exception e) {
                logger.error("Error al sincronizar con orders-service: {}", e.getMessage());
            }

            // Sincronización con Shipping (NUEVO)
            try {
                ShippingRequestDTO shippingDto = new ShippingRequestDTO();
                shippingDto.setPedidoId(dto.getPedidoId());
                shippingDto.setDireccionDestino("Dirección de Despacho LogiHub"); // Dato simulado
                shippingDto.setTransportista("LogiHub Express");
                shippingDto.setEstadoEnvio("EN_PREPARACION");

                shippingClient.crearEnvio(shippingDto);
                logger.info("Envío generado automáticamente para pedido ID: {}", dto.getPedidoId());
            } catch (Exception e) {
                logger.error("Error al generar el despacho en shipping-service: {}", e.getMessage());
            }
        }

        return new PaymentResponseDTO(guardado.getId(), guardado.getPedidoId(), guardado.getMonto(), 
                guardado.getMetodo(), guardado.getEstado(), "Pago procesado, pedido actualizado y despacho generado");
    }
}