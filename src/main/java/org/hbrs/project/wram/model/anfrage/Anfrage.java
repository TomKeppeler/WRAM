/**
 * @outhor Salah & Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 17.11.22 by Salah
 */
package org.hbrs.project.wram.model.anfrage;

import lombok.*;
import org.hbrs.project.wram.model.common.BaseEntity;
import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.reviewer.Reviewer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @Column(name = "entwicklerpublic", nullable = true)
    private boolean entwicklerpublic;

    @Column(name = "accepted", nullable = true)
    private boolean accepted;

    @Column(name = "bearbeitet", nullable = true)
    private boolean bearbeitet = false;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "reviewer_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_reviewer_id"))
    private Reviewer reviewer;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "entwicklerprofil_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_entwicklerprofil_id"))
    private Entwickler entwicklerProfil;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "kundenprojekt_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_kundenprojekt_id"))
    private Kundenprojekt kundenprojekt;


}
