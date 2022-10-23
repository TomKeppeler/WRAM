package org.hbrs.project.wram.model.manager;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, UUID> {
}
