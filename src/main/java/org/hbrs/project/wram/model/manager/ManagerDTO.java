/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 14.11.22 by Salah
 */
package org.hbrs.project.wram.model.manager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hbrs.project.wram.model.user.User;

import java.util.UUID;

/** Data Transfer Object für Manager, um ein Manager zu ertellen  */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerDTO {
    private UUID id;
    private String name;
    private String firstname;
    private User user;
}
