/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 *
 */
package org.hbrs.project.wram.model.entwickler.profile;


import org.hbrs.project.wram.model.common.BaseEntity;
import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/** Diese Klasse stellt die Entity Entwicklerprofil mit ihren Attributen dar.*/

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "EntwicklerProfil", schema = "public")
public class EntwicklerProfil extends BaseEntity {
    
    @Column(name = "image", nullable = true)
    private int image; //toDo: uploadeble image implementation

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "skills", nullable = true)
    private String skills;

    @NotNull
    @OneToOne(orphanRemoval = true, optional = false)
    @JoinColumn(
        name = "entwickler_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_entwickler_id"))
    private Entwickler entwickler;

    @OneToOne
    @JoinColumn(
        name = "kundenprojekt_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_kundenprojekt_id"))
    private Kundenprojekt kundenprojekt;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + image;
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((skills == null) ? 0 : skills.hashCode());
        result = prime * result + ((entwickler == null) ? 0 : entwickler.hashCode());
        result = prime * result + ((kundenprojekt == null) ? 0 : kundenprojekt.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntwicklerProfil other = (EntwicklerProfil) obj;
        if (image != other.image)
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (skills == null) {
            if (other.skills != null)
                return false;
        } else if (!skills.equals(other.skills))
            return false;
        if (entwickler == null) {
            if (other.entwickler != null)
                return false;
        } else if (!entwickler.equals(other.entwickler))
            return false;
        if (kundenprojekt == null) {
            if (other.kundenprojekt != null)
                return false;
        } else if (!kundenprojekt.equals(other.kundenprojekt))
            return false;
        return true;
    }
    
}
