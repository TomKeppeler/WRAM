/**
 * @outhor Sophia
 * @vision 1.0
 * @Zuletzt bearbeiret: 14.11.22 by Salah
 */
package org.hbrs.project.wram.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/** Data Transfer Object für User, um ein User zu ertellen  */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private boolean verified;
    private String verificationCode;
}
