/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.model.anfrage;

import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**JPA Repository für Anfragen */

public interface AnfrageRepository extends JpaRepository<Anfrage, UUID> {

    Anfrage findByEntwicklerProfilAndKundenprojekt(Entwickler e, Kundenprojekt k);

    List<Anfrage> findAllByEntwicklerProfilId(UUID EntwicklerProfilID);


    List<Anfrage> findAll();

}
