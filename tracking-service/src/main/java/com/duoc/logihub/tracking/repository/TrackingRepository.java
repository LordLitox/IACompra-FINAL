package com.duoc.logihub.tracking.repository;

import com.duoc.logihub.tracking.model.Rastreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrackingRepository extends JpaRepository<Rastreo, Long> {
    List<Rastreo> findByEnvioIdOrderByUltimaActualizacionDesc(Long envioId);
}