package com.duoc.logihub.auth.service;

import com.duoc.logihub.auth.dto.AuthRequestDTO;
import com.duoc.logihub.auth.dto.AuthResponseDTO;
import com.duoc.logihub.auth.model.Usuario;
import com.duoc.logihub.auth.repository.UsuarioRepository;
import java.util.stream.Collectors;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UsuarioRepository repo;

    public AuthService(UsuarioRepository repo) {
        this.repo = repo;
    }

    // 1. Registro: Ahora devuelve AuthResponseDTO para mayor seguridad
    public AuthResponseDTO registrar(AuthRequestDTO dto) {
        logger.info("Registrando nuevo usuario en BD: {}", dto.getUsername());
        Usuario user = new Usuario();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword()); // En producción usar BCrypt
        user.setRol(dto.getRol());

        Usuario guardado = repo.save(user);
        return new AuthResponseDTO(guardado.getId(), guardado.getUsername(), 
                                 guardado.getRol(), null, "Registro exitoso");
    }

    // 2. Login: Retorna un mensaje amigable o token simulado
    public String validarAcceso(String user, String pass) {
        logger.info("Intento de login para usuario: {}", user);
        return repo.findByUsername(user)
                .filter(u -> u.getPassword().equals(pass))
                .map(u -> "Login correcto para: " + u.getUsername())
                .orElse(null);
    }

    // 3. Editar: Busca por ID y actualiza los campos permitidos
    public AuthResponseDTO editar(Long id, AuthRequestDTO dto) {
        logger.info("Editando usuario con ID: {}", id);
        return repo.findById(id).map(user -> {
            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
            user.setRol(dto.getRol());
            Usuario actualizado = repo.save(user);
            return new AuthResponseDTO(actualizado.getId(), actualizado.getUsername(), 
                                     actualizado.getRol(), null, "Usuario actualizado con éxito");
        }).orElse(null);
    }

    // 4. Eliminar: Remueve el registro físico de la BD
    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            logger.warn("Eliminando usuario con ID: {}", id);
            repo.deleteById(id);
            return true;
        }
        return false;
    }
    public List<AuthResponseDTO> listarTodos() {
        logger.info("Consultando lista completa de usuarios");
        return repo.findAll().stream()
                .map(u -> new AuthResponseDTO(u.getId(), u.getUsername(), u.getRol(), null, "Usuario cargado"))
                .collect(Collectors.toList());
    }
}