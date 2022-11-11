package org.hbrs.project.wram.control.entwickler.user;

import com.vaadin.flow.component.notification.Notification;
import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EntwicklerService {

    @Autowired
    private EntwicklerRepository entwicklerRepository;

    /**
     * @param entwickler
     * @return : saves a entwickler entity in the database
     */
    public Entwickler doCreatEntwickler(Entwickler entwickler) {
        return this.entwicklerRepository.save(entwickler);
    }

    /**
     * @param userId
     * @return : corrsponding Entwickler Entity for given UUID
     */
    public Entwickler getByUserId(UUID userId) {
        if(userId == null) {
            new Notification("userId is null!!!");
            return null;
        }
        return this.entwicklerRepository.findByUserId(userId);
    }

    public Entwickler findEntwicklerByUserId(UUID id) {
        return this.entwicklerRepository.findByUserId(id);
    }

    public void deleteEntwicklerById(UUID id) {
        this.entwicklerRepository.deleteById(id);
    }
}
