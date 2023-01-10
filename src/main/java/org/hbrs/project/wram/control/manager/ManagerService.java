/**
 * @outhor Lukas, Tom & Fabio
 * @vision 1.0
 * @Zuletzt bearbeiret: 17.11.22 by Salah
 */
package org.hbrs.project.wram.control.manager;

import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.manager.ManagerRepository;
import org.hbrs.project.wram.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/** Diese Klasse ist eine Serviceklasse für Manager*/

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * speischert ein  Manager mittels ManagerRepository
     * @param manager
     * @return
     */
    public Manager doCreateManager(Manager manager) {
        return this.managerRepository.save(manager);
    }

    /**
     * Manager wird von DB mittels userID hollen
     * @param userId
     * @return
     */
    public Manager getByUserId(UUID userId) {
        if (userId == null) {
            new Notification("userId is null!!!");
            return null;
        }
        return this.managerRepository.findByUserId(userId);
    }

    /**
     * Manager wird von DB mittels userID hollen
     * @param id
     * @return
     */

    public Manager findManagerByUserId(UUID id) {
        return this.managerRepository.findByUserId(id);
    }

    /**
     * Manager wird von DB mittels id gelöcht
     * @param id
     * @return
     */

    public void deleteManagerById(UUID id) {
        this.managerRepository.deleteById(id);
    }
}
