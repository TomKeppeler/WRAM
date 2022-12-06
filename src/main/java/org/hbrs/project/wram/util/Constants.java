/**
 * @outhor Tom, Salah & Sophia
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 *
 */
package org.hbrs.project.wram.util;

/** Page, Role & Error Constants*/

public class Constants {
    public static String CURRENT_USER = "current_User";

    public static String CURRENT_PROJECT = "current_Project";

    public static class Pages {
        public static final String MAIN_VIEW = "";
        public static final String WELCOME_VIEW = "welcome";
        public static final String PROJECTS_OVERVIEW = "all-projects";//Navigation zur projects bei Manager
        public static final String LANDING_PAGE = "main";
        public static final String REGISTRATION = "registrieren"; //Navigation zur registrieren seite
        public static final String CREATEPROJECT = "ProjektErstellen";//Navigation zum Projekt Erstellen seite bei Manager
        public static final String CREATEENTWICKLERPROFIL="EntwicklerProfilErstellen";
        public static final String LOGIN_VIEW = "login";//Navigation zur Login seite
        public static final String VERIFY_VIEW = "verifizieren"; //Seite zur Verifikation
        public static final String Password_VIEW = "passwort_erneuern"; //Seite zur Verifikation
        public static final String Username_VIEW = "username_submitten"; //Seite zur Verifikation


        public static final String REVIEWERENTWICKLER = "Entwickler";
        public static final String PROJECT_DETAIL = "projektdetails";
        public static final String REVIEWERPROJEKTE = "projekte";
        public static final String REVIEWERENTWICKLERZUWEISEN = "entwicklerZuweisen";
    }


    public static class Errors {
        public static final String NOUSERFOUND = "nouser";
        public static final String SQLERROR = "sql";
        public static final String DATABASE = "database";
    }

}
