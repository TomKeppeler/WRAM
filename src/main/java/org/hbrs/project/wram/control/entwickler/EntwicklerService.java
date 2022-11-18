package org.hbrs.project.wram.control.entwickler;

import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.model.entwickler.EntwicklerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EntwicklerService {

    @Autowired
    private EntwicklerRepository entwicklerRepository;

    /**
     * @param entwickler
     * @return : saves an entwickler entity in the database
     */
    public Entwickler doCreatEntwickler(Entwickler entwickler) {
        return this.entwicklerRepository.save(entwickler);
    }

    /**
     * @param userId
     * @return : corresponding Entwickler Entity for given UUID
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

    /**
     * Diese Methode dient dazu mithilfe eines Repositorys alle Entwickler aus der Datenbank auszulesen.
     * @return Eine Liste aller Entwickler
     */
    public List<Entwickler> findAllEntwickler(){
        return entwicklerRepository.findAll();
    }
}
