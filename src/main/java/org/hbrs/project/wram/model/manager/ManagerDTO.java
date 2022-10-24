package org.hbrs.project.wram.model.manager;

import java.util.UUID;

import org.hbrs.project.wram.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDTO {
    private UUID id;
    private String name;
    private String firstname;
    private User user;
}
