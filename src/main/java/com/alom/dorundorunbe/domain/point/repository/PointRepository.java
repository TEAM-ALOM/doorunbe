package com.alom.dorundorunbe.domain.point.repository;

import com.alom.dorundorunbe.domain.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PointRepository extends JpaRepository<Point, Long> {
}
