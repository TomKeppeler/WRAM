package org.hbrs.project.wram.model.manager;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/* JPA Repository für Manager */

@Repository
@Component
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    //Note to myself:aufruf über manager.user.id, da FK entity ist
    @Query("SELECT manager from Manager manager where manager.user.id=:userId")
    public Manager findByUserId(@Param("userId") UUID userId);
}
