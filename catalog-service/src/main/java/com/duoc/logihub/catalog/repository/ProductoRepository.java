package com.duoc.logihub.catalog.repository;

import com.duoc.logihub.catalog.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Método adicional para filtrar por categoría, útil para el negocio
    List<Producto> findByCategoriaIgnoreCase(String categoria);
}