/**
 * @author Tom & Lukas
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.model.entwickler;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/** JPA Repository für CRUD eines Entwickler */

@Component
public interface EntwicklerRepository extends JpaRepository<Entwickler, UUID> {
    @Query("SELECT entwickler from Entwickler entwickler where entwickler.user.id=:userId")
    Entwickler findByUserId(@Param("userId") UUID userId);


    List<Entwickler> findAllByKundenprojektIsNull();

}
