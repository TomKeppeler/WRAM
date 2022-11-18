package org.hbrs.project.wram.model.entwickler.profile;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

/** JPA Repository für die Entwicklerprofile */

public interface EntwicklerProfilRepository extends JpaRepository<EntwicklerProfil, UUID> {




}
    
