package org.hbrs.project.wram.model.reviewer;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewerRepository extends JpaRepository<Reviewer, UUID> {
}
