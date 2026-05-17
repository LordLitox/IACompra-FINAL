package com.duoc.logihub.users.repository;

import com.duoc.logihub.users.model.UsuarioPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsuarioPerfil, Long> {
    Optional<UsuarioPerfil> findByAuthId(Long authId);
}