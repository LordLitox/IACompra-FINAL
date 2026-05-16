package com.duoc.logihub.catalog.service;

import com.duoc.logihub.catalog.dto.ProductRequestDTO;
import com.duoc.logihub.catalog.dto.ProductResponseDTO;
import com.duoc.logihub.catalog.model.Producto;
import com.duoc.logihub.catalog.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoService.class);
    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<ProductResponseDTO> listarTodos() {
        return repo.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO guardar(ProductRequestDTO dto) {
        logger.info("Persistiendo nuevo producto: {}", dto.getNombre());
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDisponible(dto.getDisponible());
        producto.setCategoria(dto.getCategoria());
        producto.setDescripcion(dto.getDescripcion());

        Producto guardado = repo.save(producto);
        return convertirAResponseDTO(guardado);
    }

    public ProductResponseDTO buscarPorId(Long id) {
        return repo.findById(id)
                .map(this::convertirAResponseDTO)
                .orElse(null);
    }

    public ProductResponseDTO actualizar(Long id, ProductRequestDTO dto) {
        return repo.findById(id).map(p -> {
            p.setNombre(dto.getNombre());
            p.setPrecio(dto.getPrecio());
            p.setDisponible(dto.getDisponible());
            p.setCategoria(dto.getCategoria());
            p.setDescripcion(dto.getDescripcion());
            return convertirAResponseDTO(repo.save(p));
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    private ProductResponseDTO convertirAResponseDTO(Producto producto) {
        ProductResponseDTO res = new ProductResponseDTO();
        res.setId(producto.getId());
        res.setNombre(producto.getNombre());
        res.setPrecio(producto.getPrecio());
        res.setDisponible(producto.isDisponible());
        res.setMensaje("Operación exitosa");
        res.setCategoria(producto.getCategoria());
        res.setDescripcion(producto.getDescripcion());
        return res;
    }
}