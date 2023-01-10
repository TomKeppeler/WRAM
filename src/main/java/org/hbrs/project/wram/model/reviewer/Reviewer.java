/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 14.11.22 by Salah
 */
package org.hbrs.project.wram.model.reviewer;

import lombok.*;
import org.hbrs.project.wram.model.common.BaseEntity;
import org.hbrs.project.wram.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/** Diese Klasse stellt die Entity Reviewer mit ihren Attributen dar.*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "reviewer", schema = "public")
public class Reviewer extends BaseEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @NotNull
    @OneToOne(orphanRemoval = true, optional = false)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;


    /**
     * hashCode methode
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    /**
     * equals methode
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Reviewer other = (Reviewer) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (firstname == null) {
            if (other.firstname != null)
                return false;
        } else if (!firstname.equals(other.firstname))
            return false;
        if (user == null) {
            return other.user == null;
        } else return user.equals(other.user);
    }

}
