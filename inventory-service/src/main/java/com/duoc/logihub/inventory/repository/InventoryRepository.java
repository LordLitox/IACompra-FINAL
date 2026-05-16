package com.duoc.logihub.inventory.repository;

import com.duoc.logihub.inventory.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventario, Long> {
    // Busca el stock de un producto específico
    Optional<Inventario> findByProductoId(Long productoId);
}