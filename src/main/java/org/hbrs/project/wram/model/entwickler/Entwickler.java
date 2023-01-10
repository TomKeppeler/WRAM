/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.model.entwickler;

import lombok.*;
import org.hbrs.project.wram.model.common.BaseEntity;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.user.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/** Diese Klasse stellt die Entity Entwickler mit ihren Attributen dar.*/

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "entwickler", schema = "public")
public class Entwickler extends BaseEntity {

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

    @Lob
    @Column(name = "image", nullable = true, columnDefinition = "BYTEA")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] image;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "skills", nullable = true)
    private String skills;

    @OneToOne
    @JoinColumn(
            name = "kundenprojekt_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_kundenprojekt_id"))
    private Kundenprojekt kundenprojekt;


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
        Entwickler other = (Entwickler) obj;
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
