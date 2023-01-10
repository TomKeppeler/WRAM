/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 25.11.2022 by Sophia
 */
package org.hbrs.project.wram.model.user;


import lombok.*;
import org.hbrs.project.wram.model.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/** Diese Klasse stellt die Entity User mit ihren Attributen dar.*/

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "User", schema = "public")
public class User extends BaseEntity {

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "verified")
    private boolean verified = false;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    /**
     * hashCode methode
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    /**
     * equals methode
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return verified == user.verified && username.equals(user.username) && email.equals(user.email) && password.equals(user.password) && Objects.equals(verificationCode, user.verificationCode);
    }
}
