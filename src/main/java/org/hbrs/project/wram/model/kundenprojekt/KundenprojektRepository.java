package org.hbrs.project.wram.model.kundenprojekt;

import java.util.List;
import java.util.UUID;

import org.hbrs.project.wram.model.manager.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface KundenprojektRepository extends JpaRepository<Kundenprojekt, UUID> {
    @Query("SELECT kundenprojekt from Kundenprojekt kundenprojekt where kundenprojekt.manager.id=:managerId")
    public List<Kundenprojekt> findKundenprojektByManagerId(@Param("managerId") UUID ManagerId);




}
