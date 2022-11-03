package org.hbrs.project.wram.control.manager;

import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.manager.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    public Manager doCreateManager(Manager manager) {
        return this.managerRepository.save(manager);
    }

    public Manager getByUserId(UUID userId) {
        if(userId == null) {
            new Notification("userId is null!!!");
            return null;
        }
            return this.managerRepository.findById(userId).get();
    }
}
