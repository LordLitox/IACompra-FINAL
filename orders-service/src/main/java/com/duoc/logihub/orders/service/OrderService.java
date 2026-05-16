package com.duoc.logihub.orders.service;

import com.duoc.logihub.orders.dto.OrderRequestDTO;
import com.duoc.logihub.orders.dto.OrderResponseDTO;
import com.duoc.logihub.orders.model.Pedido;
import com.duoc.logihub.orders.repository.OrderRepository;
import com.duoc.logihub.orders.client.InventoryClient; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    
    private final OrderRepository repo;
    private final InventoryClient inventoryClient; // Inyección de la nueva herramienta

    // Constructor único que inicializa ambos repositorios/clientes
    public OrderService(OrderRepository repo, InventoryClient inventoryClient) {
        this.repo = repo;
        this.inventoryClient = inventoryClient;
    }

    // MODIFICADO: Ahora valida stock antes de guardar
    public OrderResponseDTO crearPedido(OrderRequestDTO dto) {
        logger.info("Procesando nuevo pedido para cliente: {}. Validando inventario...", dto.getClienteId());
        
        try {
            // Llamada al microservicio de inventario
            inventoryClient.descontar(dto.getProductoId(), dto.getCantidad());
            logger.info("Inventario validado correctamente para producto ID: {}", dto.getProductoId());
        } catch (Exception e) {
            logger.error("Error al validar inventario o stock insuficiente: {}", e.getMessage());
            throw new RuntimeException("No se pudo crear el pedido: Stock insuficiente o servicio de inventario no disponible.");
        }

        // Si el inventario respondió OK, procedemos a guardar en orders_db
        Pedido pedido = new Pedido();
        pedido.setClienteId(dto.getClienteId());
        pedido.setProductoId(dto.getProductoId());
        pedido.setCantidad(dto.getCantidad());
        pedido.setTotal(dto.getTotal());
        pedido.setEstado("PENDIENTE"); // Estado inicial por defecto

        Pedido guardado = repo.save(pedido);
        return new OrderResponseDTO(guardado.getId(), guardado.getEstado(), 
                guardado.getTotal(), guardado.getFechaCreacion(), "Pedido generado exitosamente");
    }

    // Listar todos los pedidos (General)
    public List<OrderResponseDTO> listarTodos() {
        logger.info("Obteniendo listado global de pedidos");
        return repo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Editar un pedido existente
    public OrderResponseDTO editar(Long id, OrderRequestDTO dto) {
        logger.info("Actualizando pedido ID: {}", id);
        return repo.findById(id).map(p -> {
            p.setCantidad(dto.getCantidad());
            p.setTotal(dto.getTotal());
            p.setProductoId(dto.getProductoId());
            Pedido actualizado = repo.save(p);
            return new OrderResponseDTO(actualizado.getId(), actualizado.getEstado(), 
                    actualizado.getTotal(), actualizado.getFechaCreacion(), "Pedido actualizado");
        }).orElse(null);
    }

    // Eliminar pedido
    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            logger.warn("Eliminando pedido ID: {}", id);
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    // Buscar por ID específico
    public OrderResponseDTO buscarPorId(Long id) {
        logger.info("Buscando pedido con ID: {}", id);
        return repo.findById(id)
                .map(this::mapToResponse)
                .orElse(null);
    }

    // Listar por cliente
    public List<OrderResponseDTO> listarPorCliente(Long clienteId) {
        return repo.findByClienteId(clienteId).stream()
                .map(p -> new OrderResponseDTO(p.getId(), p.getEstado(), 
                        p.getTotal(), p.getFechaCreacion(), "Historial cargado"))
                .collect(Collectors.toList());
    }

    // Cambiar estado (Usado por Payments para Aprobar/Cancelar)
    public OrderResponseDTO cambiarEstado(Long id, String nuevoEstado) {
        return repo.findById(id).map(p -> {
            p.setEstado(nuevoEstado);
            Pedido actualizado = repo.save(p);
            logger.info("Estado del pedido {} actualizado a: {}", id, nuevoEstado);
            return mapToResponse(actualizado);
        }).orElse(null);
    }

    // Método de apoyo para mapear Entidad -> DTO
    private OrderResponseDTO mapToResponse(Pedido p) {
        return new OrderResponseDTO(p.getId(), p.getEstado(), p.getTotal(), 
                p.getFechaCreacion(), "Carga exitosa");
    }
}