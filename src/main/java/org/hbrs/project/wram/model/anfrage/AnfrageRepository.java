package org.hbrs.project.wram.model.anfrage;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

/**JPA Repository für Anfragen */

public interface AnfrageRepository extends JpaRepository<Anfrage, UUID> {
}
