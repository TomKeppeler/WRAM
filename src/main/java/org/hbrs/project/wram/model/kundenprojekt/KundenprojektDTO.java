package org.hbrs.project.wram.model.kundenprojekt;

import java.util.UUID;

import org.hbrs.project.wram.model.manager.Manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KundenprojektDTO {
    private UUID id;
    private boolean publicProjekt;
    private Manager manager;
}
