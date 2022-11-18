/**
 * @outhor Salah
 * @vision 1.0
 * @Zuletzt bearbeiret: 14.11.22 by Salah
 *
 */
package org.hbrs.project.wram.model.reviewer;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/** JPA Repository für Reviewer */

@Repository
@Component
public interface ReviewerRepository extends JpaRepository<Reviewer, UUID> {
    @Query("SELECT reviewer from Reviewer reviewer where reviewer.user.id=:userId")
    public Reviewer findByUserId(@Param("userId") UUID userId);
}
