/**
 * @outhor Salah, Tom & Fabio
 * @vision 1.0
 * @Zuletzt bearbeiret: 17.11.22 by Salah
 */
package org.hbrs.project.wram.control.kundenprojekt;

import lombok.RequiredArgsConstructor;
import org.hbrs.project.wram.control.anfrage.AnfrageService;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.kundenprojekt.KundenprojektRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Diese Klasse ist eine Serviceklasse für Kundenprojekt*/

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KundenprojektService {
    @Autowired
    private KundenprojektRepository kundenprojektRepository;

    @Autowired
    private AnfrageService anfrageService;

    /**
     * speischert ein Kundenprojekt mittels kundenprojektRepository
     * @param kundenprojekt
     * @return
     */
    public Kundenprojekt doCreateKundenprojekt(Kundenprojekt kundenprojekt) {
        return this.kundenprojektRepository.save(kundenprojekt);
    }

    /**
     * hollt von DB alle Kundenprojekt mittels ManagerId, die zu einem Manager gehoren
     * @param id
     * @return
     */
    public List<Kundenprojekt> findAllKundenprojektByManagerId(UUID id) {
        return kundenprojektRepository.findKundenprojektByManagerId(id);
    }


    /**
     * hollt von DB alle Kundenprojekt mittels EntwicklerId, die zu einem Entwickler zungewissen sind
     * @param entwickler<Anfrage>
     * @return
     */


    /**
     * hollt von DB aller Kundenprojekt
     * @return
     */
    public List<Kundenprojekt> findAllPublicKundenprojekt() {
        List<Kundenprojekt> erg = new ArrayList<>();
        List<Kundenprojekt> temp = kundenprojektRepository.findAll();
        for (Kundenprojekt k : temp) {
            if (k.isPublicProjekt()) {
                erg.add(k);
            }
        }
        return erg;
    }

}
