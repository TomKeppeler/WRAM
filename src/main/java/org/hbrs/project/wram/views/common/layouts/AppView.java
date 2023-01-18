/**
 * @outhor Tom, Salah & Sophia
 * @vision 1.0
 * @Zuletzt bearbeitet: 10.01.22 by Leon
 */
package org.hbrs.project.wram.views.common.layouts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.PWA;
import org.hbrs.project.wram.control.LoginControl;
import org.hbrs.project.wram.control.entwickler.EntwicklerService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.control.reviewer.ReviewerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.util.Utils;
import org.hbrs.project.wram.views.routes.entwickler.CreateEntwicklerProfil;
import org.hbrs.project.wram.views.routes.entwickler.EntwicklerAnfrageView;
import org.hbrs.project.wram.views.routes.main.LandingView;
import org.hbrs.project.wram.views.routes.manager.BearbeiteteAnfragen;
import org.hbrs.project.wram.views.routes.manager.CreateProjectForm;
import org.hbrs.project.wram.views.routes.manager.ProjectsOverview;
import org.hbrs.project.wram.views.routes.manager.ZuweisungSenden;
import org.hbrs.project.wram.views.routes.reviewer.ReviewerEntwicklerView;
import org.hbrs.project.wram.views.routes.reviewer.ReviewerProjektView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main view is a top-level placeholder for other views.
 * It can be embedded, by changing the value declarer of @Route to this class
 */
@CssImport("./styles/views/main/main-view.css")
@Route(Constants.Pages.LANDING_PAGE)
@RouteAlias(value = "Appview")
@PWA(name = "WRAM", shortName = "WRAM", enableInstallPrompt = false)
@JsModule("./styles/shared-styles.js")
public class AppView extends AppLayout implements BeforeEnterObserver {

    private static final Logger logger = Logger.getGlobal();
    private Tabs menu;
    private H1 viewTitle;
    private H1 helloUser;
    @Autowired
    private LoginControl control;
    @Autowired
    private UserService userService;

    @Autowired
    private ManagerService managerService;
    @Autowired
    private EntwicklerService entwicklerService;
    @Autowired
    private ReviewerService reviewerService;

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @PostConstruct
    private void init() {
        setUpUI();
    }

    /**
     * responsible for adding all generated Components to this View
     */
    public void setUpUI() {
        // Anzeige des Toggles über den Drawer
        setPrimarySection(Section.DRAWER);

        // Erstellung der horizontalen Statusleiste (Header)
        addToNavbar(true, createHeaderContent());

        // Erstellung der vertikalen Navigationsleiste (Drawer)
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    /**
     * @return boolean: checks if User is in session.
     */
    private boolean checkIfUserIsLoggedIn() {
        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite
        // gelenkt
        UUID userID = (UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER);
        boolean checkUser = (userID == null);
        if (checkUser) {
            UI.getCurrent().navigate(Constants.Pages.LOGIN_VIEW);
        }
        return checkUser;
    }

    /**
     * Erzeugung der horizontalen Leiste (Header).
     *
     * @return
     */
    private Component createHeaderContent() {
        // Ein paar Grund-Einstellungen. Alles wird in ein horizontales Layout gesteckt.
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);

        // Hinzufügen des Toogle ('Big Mac') zum Ein- und Ausschalten des Drawers
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        viewTitle.setWidthFull();
        layout.add(viewTitle);

        // Interner Layout
        HorizontalLayout topRightPanel = new HorizontalLayout();
        topRightPanel.setWidthFull();
        topRightPanel.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        topRightPanel.setAlignItems(FlexComponent.Alignment.CENTER);

        // Der Name des Users wird später reingesetzt, falls die Navigation stattfindet
        helloUser = new H1();
        topRightPanel.add(helloUser);

        // Logout-Button am rechts-oberen Rand.
        MenuBar bar = new MenuBar();
        bar.addItem("Logout", e -> logoutUser());
        topRightPanel.add(bar);

        layout.add(topRightPanel);
        return layout;
    }

    private void logoutUser() {
        UI.getCurrent().getSession().close();
        control.logoutUser();
        UI.getCurrent().navigate(Constants.Pages.MAIN_VIEW);
    }

    /**
     * Hinzufügen der vertikalen Leiste (Drawer)
     * Diese besteht aus dem Logo ganz oben links sowie den Menu-Einträgen (menu
     * items).
     * Die Menu Items sind zudem verlinkt zu den internen Tab-Components.
     *
     * @param menu
     * @return
     */
    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        HorizontalLayout logoLayout = new HorizontalLayout();

        // Hinzufügen des Logos
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "WRAM logo"));
        // logoLayout.add(new H1("WRAM"));

        // Hinzufügen des Menus inklusive der Tabs
        layout.add(logoLayout, menu);
        return layout;
    }

    /**
     * Erzeugung des Menu auf der vertikalen Leiste (Drawer)
     *
     * @return
     */
    private Tabs createMenu() {

        // Anlegen der Grundstruktur
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");

        // Anlegen der einzelnen Menuitems
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {

        Tab[] tabs = new Tab[0];
        Icon icon = new Icon("home-o");
        tabs = Utils.append(tabs, createTab("Home", LandingView.class));

        // Manager Tabs
        if (userService.getRolle() == "m") {
            logger.log(Level.INFO, "User is \"Manager\"!");
            tabs = Utils.append(tabs, createTab("Meine Projekte", ProjectsOverview.class));
            tabs = Utils.append(tabs, createTab("Neues Projekt erstellen", CreateProjectForm.class));
            tabs = Utils.append(tabs, createTab("Anfrage an Entwickler senden", ZuweisungSenden.class));
            tabs = Utils.append(tabs, createTab("Bearbeitete Anfrage", BearbeiteteAnfragen.class));
        }
        // Entwickler Tabs
        else if (userService.getRolle() == "e") {

            //Tab profile = new Tab(VaadinIcon.USER.create());
            //profile.add(new RouterLink("Mein Profile", CreateEntwicklerProfil.class));
            logger.log(Level.INFO, "User is \"Entwickler\"!");
            tabs = Utils.append(tabs, createTab("Mein Profil", CreateEntwicklerProfil.class));
            //tabs = Utils.append( tabs , profile);
            tabs = Utils.append(tabs, createTab("Projektanfragen", EntwicklerAnfrageView.class));

        }

        // Reviewer Tabs
        else if (userService.getRolle() == "r") {
            logger.log(Level.INFO, "User is \"Reviewer\"!");
            tabs = Utils.append(tabs, createTab("Entwickler", ReviewerEntwicklerView.class));
            tabs = Utils.append(tabs, createTab("Kundenprojekte", ReviewerProjektView.class));
        }

        //ToDo
        //tabs = Utils.append( tabs , createTab("Hilfe", Hilfe.class));
        //tabs = Utils.append( tabs , createTab("Über uns", UeberUns.class));


        return tabs;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();

        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite
        // gelenkt
        /*if (!checkIfUserIsLoggedIn())
            return;*/

        // Der aktuell-selektierte Tab wird gehighlighted.
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);

        // Setzen des aktuellen Names des Tabs
        viewTitle.setText(getCurrentPageTitle());

        // Setzen des Vornamens von dem aktuell eingeloggten Benutzer
        helloUser.setText("Willkommen, " + getname() + " bei WAC!");
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        // ToDo: when logging out as
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    private String getCurrentNameOfUser() {
        User currentUser = control.getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUsername();
        }
        return null;
    }

    @Override
    /**
     * Methode wird vor der eigentlichen Darstellung der UI-Components aufgerufen.
     * Hier kann man die finale Darstellung noch abbrechen, wenn z.B. der Nutzer
     * nicht eingeloggt ist
     * Dann erfolgt hier ein ReDirect auf die Login-Seite. Eine Navigation (Methode
     * navigate)
     * ist hier nicht möglich, da die finale Navigation noch nicht stattgefunden
     * hat.
     * Diese Methode in der AppLayout sichert auch den un-authorisierten Zugriff auf
     * die innerliegenden
     * Views (hier: ShowCarsView und EnterCarView) ab.
     *
     */
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (control.getCurrentUser() == null) {
            beforeEnterEvent.rerouteTo(Constants.Pages.LOGIN_VIEW);
        }
    }

    private String getname() {
        if (userService.getRolle() == "m") return managerService.findManagerByUserId(control.getCurrentUser()
                .getId()).getFirstname();
        if (userService.getRolle() == "e") return entwicklerService.findEntwicklerByUserId(control.getCurrentUser()
                .getId()).getFirstname();
        return reviewerService.findReviewerByUserId(control.getCurrentUser()
                .getId()).getFirstname();
    }


}
