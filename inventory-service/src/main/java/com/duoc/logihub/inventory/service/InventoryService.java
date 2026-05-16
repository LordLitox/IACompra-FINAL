package com.duoc.logihub.inventory.service;

import com.duoc.logihub.inventory.dto.InventoryRequestDTO;
import com.duoc.logihub.inventory.dto.InventoryResponseDTO;
import com.duoc.logihub.inventory.model.Inventario;
import com.duoc.logihub.inventory.repository.InventoryRepository;
import java.util.List;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);
    private final InventoryRepository repo;

    public InventoryService(InventoryRepository repo) {
        this.repo = repo;
    }

    public List<InventoryResponseDTO> listarTodo() {
        logger.info("Obteniendo todos los registros de inventario");
        return repo.findAll().stream()
                .map(entidad -> convertirAResponseDTO(entidad, "Registro recuperado"))
                .collect(Collectors.toList());
    }

    public InventoryResponseDTO actualizarStock(InventoryRequestDTO dto) {
        logger.info("Actualizando inventario para producto ID: {}", dto.getProductoId());
        
        // Buscamos si ya existe registro para ese producto, si no, creamos uno nuevo
        Inventario inv = repo.findByProductoId(dto.getProductoId()).orElse(new Inventario());
        
        inv.setProductoId(dto.getProductoId());
        inv.setCantidad(dto.getCantidad());
        inv.setNombre(dto.getNombre());
        inv.setBodega(dto.getBodega()); // Aseguramos que la bodega se guarde
        
        Inventario guardado = repo.save(inv);
        return convertirAResponseDTO(guardado, "Stock actualizado correctamente");
    }

    public InventoryResponseDTO obtenerPorProducto(Long productoId) {
        return repo.findByProductoId(productoId)
                   .map(inv -> convertirAResponseDTO(inv, "Producto encontrado"))
                   .orElse(null);
    }

    private InventoryResponseDTO convertirAResponseDTO(Inventario entidad, String mensaje) {
        InventoryResponseDTO res = new InventoryResponseDTO();
        res.setId(entidad.getId());
        res.setProductoId(entidad.getProductoId());
        res.setCantidad(entidad.getCantidad());
        res.setNombre(entidad.getNombre());
        res.setBodega(entidad.getBodega());
        res.setMensaje(mensaje);
        return res;
    }
    public InventoryResponseDTO editar(Long id, InventoryRequestDTO dto){
        return repo.findById(id).map(existente -> {
            existente.setProductoId(dto.getProductoId());
            existente.setCantidad(dto.getCantidad());
            existente.setNombre(dto.getNombre());
            existente.setBodega(dto.getBodega());
            Inventario actualizado = repo.save(existente);
            return convertirAResponseDTO(actualizado, "Registro de inventario editado con éxito");
        }).orElse(null);
    }
    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
    public boolean descontarStock(Long productoId, int cantidad) {
        return repo.findByProductoId(productoId).map(inv -> {
            if (inv.getCantidad() >= cantidad) {
                inv.setCantidad(inv.getCantidad() - cantidad);
                repo.save(inv);
                logger.info("Stock descontado: Producto {}, cantidad {}", productoId, cantidad);
                return true;
            }
            logger.warn("Stock insuficiente para producto {}", productoId);
            return false;
        }).orElse(false);
    }

}