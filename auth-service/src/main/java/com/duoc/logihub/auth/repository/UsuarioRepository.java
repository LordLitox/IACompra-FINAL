package com.duoc.logihub.auth.repository;

import com.duoc.logihub.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository // Indica que esta interfaz maneja el acceso a los datos (Capa de persistencia)
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Al extender JpaRepository, ya tienes gratis los métodos:
    // .save(), .findAll(), .findById(), .deleteById(), etc.

    // Este es un "Query Method" personalizado para buscar por el nombre de usuario
    // Es fundamental para el proceso de Login
    Optional<Usuario> findByUsername(String username);
}