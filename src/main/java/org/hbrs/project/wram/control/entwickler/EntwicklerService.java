/**
 * @outhor Lukas, Tom & Fabio
 * @vision 1.0
 * @Zuletzt bearbeiret: 17.11.22 by Salah
 *
 */
package org.hbrs.project.wram.control.entwickler;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.model.entwickler.EntwicklerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.flow.component.notification.Notification;

import lombok.RequiredArgsConstructor;

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

    /**
     * finde Entwickler von mittels UserId
     * @param id
     * @return
     */
    public Entwickler findEntwicklerByUserId(UUID id) {
        return this.entwicklerRepository.findByUserId(id);
    }

    /**
     * lösche Entwickler von DB
     * @param id
     */
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

    /**
     * Diese Methode dient dazu mithilfe eines Repositorys einen Entwickler ein Bild zu geben
     * und dann in der Datenbank zu speichern.
     * @param id
     * @return Entwickler
     */
    public void saveImage(byte[] image, @NotNull UUID id) {
        Entwickler entwickler = this.entwicklerRepository.findById(id).get();
        entwickler.setImage(image);
        this.entwicklerRepository.save(entwickler);
    }
    @Transactional
    public @Nullable byte[] getImage(UUID id) {
        Entwickler entwickler = this.entwicklerRepository.findById(id).orElseGet(null);
        return entwickler.getImage();
    }
}
