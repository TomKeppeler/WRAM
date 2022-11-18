/**
 * @outhor Lukas
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 *
 */
package org.hbrs.project.wram.model.entwickler.profile;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

/** JPA Repository für die Entwicklerprofile */

public interface EntwicklerProfilRepository extends JpaRepository<EntwicklerProfil, UUID> {




}
    
