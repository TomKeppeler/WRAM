/**
 * @outhor Salah
 * @vision 1.0
 * @Zuletzt bearbeiret: 14.11.22 by Salah
 */
package org.hbrs.project.wram.model.reviewer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/** JPA Repository für CRUD eines Reviewer */

@Repository
@Component
public interface ReviewerRepository extends JpaRepository<Reviewer, UUID> {
    @Query("SELECT reviewer from Reviewer reviewer where reviewer.user.id=:userId")
    Reviewer findByUserId(@Param("userId") UUID userId);
}
