package com.alom.dorundorunbe.domain.doodle.repository;

import com.alom.dorundorunbe.domain.doodle.domain.Doodle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoodleRepository extends JpaRepository<Doodle, Long> {

}
