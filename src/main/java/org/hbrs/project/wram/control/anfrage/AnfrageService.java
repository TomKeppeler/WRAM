/**
 * @outhor Lukas, Tom & Fabio
 * @vision 1.0
 * @Zuletzt bearbeiret: 17.11.22 by Salah
 *
 */
package org.hbrs.project.wram.control.anfrage;

import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.hbrs.project.wram.model.anfrage.Anfrage;
import org.hbrs.project.wram.model.anfrage.AnfrageRepository;
import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.model.entwickler.EntwicklerRepository;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AnfrageService {

    @Autowired
    private AnfrageRepository anfrageRepository;

    /**
     * @param anfrage
     * @return : saves an anfrage entity in the database
     */
    public Anfrage doCreatAnfrage(Anfrage anfrage) {
        return this.anfrageRepository.save(anfrage);
    }

    public boolean anfrageAlreadyExists(Entwickler entwickler, Kundenprojekt kundenprojekt) {
        Anfrage a = this.anfrageRepository.findByEntwicklerProfilAndKundenprojekt(entwickler, kundenprojekt);
        return a==null?false:true;
    }

    /*public List<Anfrage> findAllByEntwickler(Entwickler entwickler) {
        return  anfrageRepository.findByfindByEntwicklerProfil(entwickler);
    }*/
}
