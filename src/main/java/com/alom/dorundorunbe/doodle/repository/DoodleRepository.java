package com.alom.dorundorunbe.doodle.repository;

import com.alom.dorundorunbe.doodle.domain.Doodle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoodleRepository extends JpaRepository<Doodle, Long> {

}
