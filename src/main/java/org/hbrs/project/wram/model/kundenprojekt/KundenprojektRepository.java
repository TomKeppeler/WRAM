/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.model.kundenprojekt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/** JPA Repository für Kundenprojekte */

@Repository
@Component
public interface KundenprojektRepository extends JpaRepository<Kundenprojekt, UUID> {
    @Query("SELECT kundenprojekt from Kundenprojekt kundenprojekt where kundenprojekt.manager.id=:managerId")
    List<Kundenprojekt> findKundenprojektByManagerId(@Param("managerId") UUID ManagerId);

    /*@Query("SELECT kundenprojekt from Kundenprojekt kundenprojekt where kundenprojekt.entwickler.id =: entwicklerId")
    public List<Kundenprojekt> findKundenprojekByEntwicklerId(@Param("entwicklerId") UUID entwicklerId);*/

}
