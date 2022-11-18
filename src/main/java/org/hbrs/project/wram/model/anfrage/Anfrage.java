package org.hbrs.project.wram.model.anfrage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hbrs.project.wram.model.common.BaseEntity;
import org.hbrs.project.wram.model.entwickler.profile.EntwicklerProfil;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.reviewer.Reviewer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Diese Klasse stellt die Entity Anfrage mit ihren Attributen dar.*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "anfrage", schema = "public")
public class Anfrage extends BaseEntity {
    
    @Column(name = "reason", nullable = true)
    private String reason;

    @Column(name = "accepted", nullable = true)
    private String accepted;

    @NotNull
    @OneToOne(orphanRemoval = false, optional = false)
    @JoinColumn(
        name = "reviewer_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_reviewer_id"))
    private Reviewer reviewer;

    @NotNull
    @OneToOne(orphanRemoval = true, optional = false)
    @JoinColumn(
        name = "entwicklerprofil_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_entwicklerprofil_id"))
    private EntwicklerProfil entwicklerProfil;

    @NotNull
    @OneToOne(orphanRemoval = true, optional = false)
    @JoinColumn(
        name = "kundenprojekt_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_kundenprojekt_id"))
    private Kundenprojekt kundenprojekt;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((reason == null) ? 0 : reason.hashCode());
        result = prime * result + ((accepted == null) ? 0 : accepted.hashCode());
        result = prime * result + ((reviewer == null) ? 0 : reviewer.hashCode());
        result = prime * result + ((entwicklerProfil == null) ? 0 : entwicklerProfil.hashCode());
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
        Anfrage other = (Anfrage) obj;
        if (reason == null) {
            if (other.reason != null)
                return false;
        } else if (!reason.equals(other.reason))
            return false;
        if (accepted == null) {
            if (other.accepted != null)
                return false;
        } else if (!accepted.equals(other.accepted))
            return false;
        if (reviewer == null) {
            if (other.reviewer != null)
                return false;
        } else if (!reviewer.equals(other.reviewer))
            return false;
        if (entwicklerProfil == null) {
            if (other.entwicklerProfil != null)
                return false;
        } else if (!entwicklerProfil.equals(other.entwicklerProfil))
            return false;
        if (kundenprojekt == null) {
            if (other.kundenprojekt != null)
                return false;
        } else if (!kundenprojekt.equals(other.kundenprojekt))
            return false;
        return true;
    }
    
}
