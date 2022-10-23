package org.hbrs.project.wram.Entities;

import javax.persistence.*;

@Entity
@Table(name="User",schema = "WRAMSchema")
public class User {

    private static Long id=1L;
    private String rolle;
    private String vorname;
    private String nachname;
    private String email;
    private String passwort;


    public void setRolle(String rolle) {

        this.rolle = rolle;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setVorname(String vorname){
        this.vorname=vorname;
    }
    public  void setNachname(String nachname){
        this.nachname=nachname;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setPasswort(String passwort){
        this.passwort=passwort;
    }
    @Id
    @GeneratedValue
    @Column(name="User_ID")
    public Long getId() {
        return id;
    }
    @Column(name="Rolle")
    public String getRolle(){
        return rolle;
    }
    @Column(name="Vorname")
    public String getVorname(){
        return vorname;
    }
    @Column(name="Nachname")
    public String getNachname(){
        return nachname;
    }
    @Column(name="Email")
    public String getEmail(){
        return email;
    }
    @Column(name = "Passwort")
    public String getPasswort(){
        return passwort;
    }

}
