package com.duoc.logihub.shipping.service;

import com.duoc.logihub.shipping.dto.ShippingRequestDTO;
import com.duoc.logihub.shipping.dto.ShippingResponseDTO;
import com.duoc.logihub.shipping.model.Envio;
import com.duoc.logihub.shipping.repository.ShippingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingService {
    private static final Logger logger = LoggerFactory.getLogger(ShippingService.class);
    private final ShippingRepository repo;

    public ShippingService(ShippingRepository repo) {
        this.repo = repo;
    }

    public List<ShippingResponseDTO> listarTodos() {
        return repo.findAll().stream()
                .map(e -> mapToResponse(e, "Listado de envíos recuperado"))
                .collect(Collectors.toList());
    }

    public ShippingResponseDTO buscarPorId(Long id) {
        return repo.findById(id)
                .map(e -> mapToResponse(e, "Detalle de envío encontrado"))
                .orElse(null);
    }

    public ShippingResponseDTO crearEnvio(ShippingRequestDTO dto) {
        logger.info("Generando despacho para pedido ID: {}", dto.getPedidoId());
        Envio envio = new Envio();
        envio.setPedidoId(dto.getPedidoId());
        envio.setDireccionDestino(dto.getDireccionDestino());
        envio.setTransportista(dto.getTransportista());
        // Si no viene estado, por defecto es EN_PREPARACION
        envio.setEstadoEnvio(dto.getEstadoEnvio() != null ? dto.getEstadoEnvio() : "EN_PREPARACION");

        Envio guardado = repo.save(envio);
        return mapToResponse(guardado, "Orden de despacho generada exitosamente");
    }

    public ShippingResponseDTO editar(Long id, ShippingRequestDTO dto) {
        return repo.findById(id).map(e -> {
            e.setDireccionDestino(dto.getDireccionDestino());
            e.setTransportista(dto.getTransportista());
            if(dto.getEstadoEnvio() != null) e.setEstadoEnvio(dto.getEstadoEnvio());
            Envio actualizado = repo.save(e);
            return mapToResponse(actualizado, "Datos de envío actualizados");
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    private ShippingResponseDTO mapToResponse(Envio e, String mensaje) {
        ShippingResponseDTO res = new ShippingResponseDTO();
        res.setId(e.getId());
        res.setPedidoId(e.getPedidoId());
        res.setDireccionDestino(e.getDireccionDestino());
        res.setTransportista(e.getTransportista());
        res.setEstadoEnvio(e.getEstadoEnvio());
        res.setMensaje(mensaje); // El mensaje va solo en su campo
        return res;
    }
}