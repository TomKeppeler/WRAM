package org.hbrs.project.wram.model.entwickler.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface EntwicklerRepository extends JpaRepository<Entwickler, UUID> {
}
