package org.hbrs.project.wram.model.user;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDTO {
    private UUID id;
    private String username;
    private String rolle;
    private String vorname;
    private String nachname;
    private String email;
    private String passwort;


}
