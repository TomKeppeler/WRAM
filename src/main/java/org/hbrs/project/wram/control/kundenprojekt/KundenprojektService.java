package org.hbrs.project.wram.control.kundenprojekt;

import lombok.RequiredArgsConstructor;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.kundenprojekt.KundenprojektRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KundenprojektService {
    @Autowired
    private KundenprojektRepository kundenprojektRepository;

    public Kundenprojekt doCreateKundenprojekt(Kundenprojekt kundenprojekt) {
        return this.kundenprojektRepository.save(kundenprojekt);
    }

    public List<Kundenprojekt> findAllKundenprojektByManagerId(UUID id){
        return kundenprojektRepository.findKundenprojektByManagerId(id);
    }

    public List<Kundenprojekt> findAllPublicKundenprojekt(){
        List<Kundenprojekt> erg = new ArrayList<>();
        List<Kundenprojekt> temp = kundenprojektRepository.findAll();
        for(Kundenprojekt k:temp){
            if(k.isPublicProjekt()){
                erg.add(k);
            }
        }
        return erg;
    }

}
