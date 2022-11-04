package org.hbrs.project.wram.model.entwickler.user;

import java.util.UUID;

import org.hbrs.project.wram.model.manager.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface EntwicklerRepository extends JpaRepository<Entwickler, UUID> {
    @Query("SELECT entwickler from Entwickler entwickler where entwickler.user.id=:userId")
    public Entwickler findByUserId(@Param("userId") UUID userId);
}
