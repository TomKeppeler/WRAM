/**
 * @outhor Sophia
 * @vision 1.0
 * @Zuletzt bearbeiret: 14.11.22 by Salah
 *
 */
package org.hbrs.project.wram.model.user;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Data Transfer Object für User */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String username;
    private String email;
    private String password;
}
