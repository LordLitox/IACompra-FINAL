package com.duoc.logihub.users.service;

import com.duoc.logihub.users.dto.UserRequestDTO;
import com.duoc.logihub.users.dto.UserResponseDTO;
import com.duoc.logihub.users.model.UsuarioPerfil;
import com.duoc.logihub.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserResponseDTO crearPerfil(UserRequestDTO dto) {
        logger.info("Persistiendo nuevo perfil en BD: {}", dto.getEmail());
        UsuarioPerfil user = new UsuarioPerfil();
        user.setNombreCompleto(dto.getNombreCompleto());
        user.setEmail(dto.getEmail());
        user.setAuthId(dto.getAuthId());
        user.setTelefono(dto.getTelefono());
        user.setDireccion(dto.getDireccion());

        UsuarioPerfil guardado = repo.save(user);
        return convertirAResponseDTO(guardado, "Usuario creado exitosamente en base de datos");
    }

    public List<UserResponseDTO> listarTodos() {
        return repo.findAll().stream()
                .map(user -> convertirAResponseDTO(user, "Usuario recuperado"))
                .collect(Collectors.toList());
    }

    public UserResponseDTO obtenerPorId(Long id) {
        return repo.findById(id)
                .map(user -> convertirAResponseDTO(user, "Usuario encontrado"))
                .orElse(null);
    }

    public UserResponseDTO actualizar(Long id, UserRequestDTO dto) {
        return repo.findById(id).map(existente -> {
            existente.setNombreCompleto(dto.getNombreCompleto());
            existente.setEmail(dto.getEmail());
            existente.setTelefono(dto.getTelefono());
            existente.setDireccion(dto.getDireccion());
            UsuarioPerfil actualizado = repo.save(existente);
            return convertirAResponseDTO(actualizado, "Usuario actualizado correctamente");
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    private UserResponseDTO convertirAResponseDTO(UsuarioPerfil user, String mensaje) {
        UserResponseDTO res = new UserResponseDTO();
        res.setId(user.getId());
        res.setNombreCompleto(user.getNombreCompleto());
        res.setEmail(user.getEmail());
        res.setMensaje(mensaje);
        return res;
    }
}