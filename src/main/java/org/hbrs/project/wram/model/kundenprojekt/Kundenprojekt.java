package org.hbrs.project.wram.model.kundenprojekt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hbrs.project.wram.model.common.BaseEntity;
import org.hbrs.project.wram.model.manager.Manager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="kundenprojekt", schema = "public")
public class Kundenprojekt extends BaseEntity {
    
    @NotNull
    @Column(name="public_projekt", nullable = false)
    private boolean publicProjekt;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(
        name = "manager_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_manager_id"))
    private Manager manager;

    @Column(name="projektname")
    private String projektname;

    @Column(name="projektbeschreibung")
    private String projektbeschreibung;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (publicProjekt ? 1231 : 1237);
        result = prime * result + ((manager == null) ? 0 : manager.hashCode());
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
        Kundenprojekt other = (Kundenprojekt) obj;
        if (publicProjekt != other.publicProjekt)
            return false;
        if (manager == null) {
            if (other.manager != null)
                return false;
        } else if (!manager.equals(other.manager))
            return false;
        return true;
    }
    
}
