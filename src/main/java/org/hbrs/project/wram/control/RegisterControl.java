package org.hbrs.project.wram.control;

import org.hbrs.project.wram.model.user.User;

public class RegisterControl {

    public static boolean confirmPasswort(String passwort,String passwortbestätigung){
        if(!passwort.equals(passwortbestätigung)|passwort.length()<8){
            return false;
        }
        boolean upperCaseFlag=false;
        boolean digitFlag=false;
        for(int i=0;i<passwort.length();i++){
            char s=passwort.charAt(i);
            if(Character.isDigit(s)){
                digitFlag=true;
            }
            if(Character.isUpperCase(s)){
                upperCaseFlag=true;
            }
        }
        return digitFlag&upperCaseFlag;
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
