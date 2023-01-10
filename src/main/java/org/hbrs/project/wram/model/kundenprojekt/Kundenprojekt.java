/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.model.kundenprojekt;

import lombok.*;
import org.hbrs.project.wram.model.common.BaseEntity;
import org.hbrs.project.wram.model.manager.Manager;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/** Diese Klasse stellt die Entity Kundenprojekt mit ihren Attributen dar.*/

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "kundenprojekt", schema = "public")
public class Kundenprojekt extends BaseEntity {

    @NotNull
    @Column(name = "public_projekt", nullable = false)
    private boolean publicProjekt;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "manager_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_manager_id"))
    private Manager manager;

    @NotNull
    @Column(name = "projektname")
    private String projektname;

    @NotNull
    @Column(name = "projektbeschreibung", columnDefinition = "varchar(1000)")
    private String projektbeschreibung;

    @NotNull
    @Column(name = "skills")
    private String skills;


    /**
     * hashCode methode
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (publicProjekt ? 1231 : 1237);
        result = prime * result + ((manager == null) ? 0 : manager.hashCode());
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
        Kundenprojekt other = (Kundenprojekt) obj;
        if (publicProjekt != other.publicProjekt)
            return false;
        if (manager == null) {
            return other.manager == null;
        } else return manager.equals(other.manager);
    }

}
