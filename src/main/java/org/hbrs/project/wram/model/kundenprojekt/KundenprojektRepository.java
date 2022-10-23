package org.hbrs.project.wram.model.kundenprojekt;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KundenprojektRepository extends JpaRepository<Kundenprojekt, UUID> {
}
