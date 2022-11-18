package org.hbrs.project.wram.model.reviewer;

import java.util.UUID;

import lombok.Builder;
import org.hbrs.project.wram.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Data Transfer Object für Reviewer */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewerDTO {
    private UUID id;
    private String name;
    private String firstname;
    private User user;
}