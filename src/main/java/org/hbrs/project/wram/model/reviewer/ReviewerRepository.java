package org.hbrs.project.wram.model.reviewer;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ReviewerRepository extends JpaRepository<Reviewer, UUID> {
}
