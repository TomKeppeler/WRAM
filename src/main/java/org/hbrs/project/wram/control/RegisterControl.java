package org.hbrs.project.wram.control;

import com.vaadin.flow.data.validator.EmailValidator;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.util.Utils;

public class RegisterControl {

    public static boolean passwortCheck(String passwort){
        boolean longEnough = passwort.length() > 7;
        boolean hasNumber = Utils.hasNumber(passwort);
        boolean hasUppercaseLetter = Utils.hasUpperCaseLetter(passwort);

        return longEnough && hasNumber && hasUppercaseLetter;
    }

    public static void registerMethod(String vorname,String nachname,String email,String passwort,String rolle){
      /*User u=new User();
      u.setVorname(vorname);
      u.setNachname(nachname);
      u.setEmail(email);
      u.setPasswort(passwort);
      u.setRolle(rolle); */
    }

}
