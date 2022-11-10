package org.hbrs.project.wram.control.manager;

import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.manager.ManagerRepository;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private UserRepository userRepository;

    public Manager doCreateManager(Manager manager) {
        return this.managerRepository.save(manager);
    }

    public Manager getByUserId(UUID userId) {
        if(userId == null) {
            new Notification("userId is null!!!");
            return null;
        }
        return this.managerRepository.findByUserId(userId);
    }

    public Manager findManagerByUserId(UUID id) {
        return this.managerRepository.findByUserId(id);
    }

    public void deleteManagerById(UUID id) {
        this.managerRepository.deleteById(id);
    }
}
