package org.hbrs.project.wram.model.kundenprojekt;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KundenprojektRepository extends JpaRepository<Kundenprojekt, UUID> {
}
