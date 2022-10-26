package org.hbrs.project.wram.util;

import com.vaadin.flow.component.Component;

public class Constants {
    public static String CURRENT_USER = "current_User";

    public static class Pages {
        public static final String MAIN_VIEW = "";
        public static final String PROJECTS_OVERVIEW = "all-projects";
        public static final String LANDING_PAGE = "main";
    }

    public static class Roles {
        public static final String ADMIN = "admin";
        public static final String CURRENT_USER = "user";

    }

    public static class Errors {
        public static final String NOUSERFOUND = "nouser";
        public static final String SQLERROR = "sql";
        public static final String DATABASE = "database";
    }

}
