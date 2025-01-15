package com.alom.dorundorunbe.domain.runningrecord.repository;

import com.alom.dorundorunbe.domain.runningrecord.domain.GpsCoordinate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpsCoordinateRepository extends JpaRepository<GpsCoordinate, Long> {
}
