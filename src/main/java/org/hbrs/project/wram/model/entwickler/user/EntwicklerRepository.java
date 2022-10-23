package org.hbrs.project.wram.model.entwickler.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntwicklerRepository extends JpaRepository<Entwickler, UUID> {
}
