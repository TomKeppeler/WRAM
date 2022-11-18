package org.hbrs.project.wram.control.entwickler.profile;

import org.hbrs.project.wram.model.entwickler.profile.EntwicklerProfil;
import org.hbrs.project.wram.model.entwickler.profile.EntwicklerProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/** Diese Klasse ist eine Service-Klasse für Entwicklerprofil.*/

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EntwicklerProfileService {

    @Autowired
    private EntwicklerProfilRepository entwicklerRepository;

    public EntwicklerProfil doCreatEntwickler(EntwicklerProfil entwickler) {
        return this.entwicklerRepository.save(entwickler);
    }
}
