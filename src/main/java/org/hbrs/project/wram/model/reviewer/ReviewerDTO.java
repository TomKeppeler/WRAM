/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 14.11.22 by Salah
 *
 */
package org.hbrs.project.wram.model.reviewer;

import java.util.UUID;

import lombok.Builder;
import org.hbrs.project.wram.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Data Transfer Object für Reviewer, um ein Reviewer zu ertellen  */

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