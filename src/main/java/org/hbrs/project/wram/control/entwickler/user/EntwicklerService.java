package org.hbrs.project.wram.control.entwickler.user;

import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EntwicklerService {
    
    private EntwicklerRepository entwicklerRepository;

    public Entwickler doCreatEntwickler(Entwickler entwickler) {
        return this.entwicklerRepository.save(entwickler);
    }
}
