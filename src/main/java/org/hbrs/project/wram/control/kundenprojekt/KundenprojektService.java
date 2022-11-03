package org.hbrs.project.wram.control.kundenprojekt;

import lombok.RequiredArgsConstructor;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.kundenprojekt.KundenprojektRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KundenprojektService {
    @Autowired
    private KundenprojektRepository kundenprojektRepository;

    public Kundenprojekt doCreateKundenprojekt(Kundenprojekt kundenprojekt) {
        return this.kundenprojektRepository.save(kundenprojekt);
    }
}
