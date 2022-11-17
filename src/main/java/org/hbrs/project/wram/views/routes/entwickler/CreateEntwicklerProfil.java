package org.hbrs.project.wram.views.routes.entwickler;

import static org.hbrs.project.wram.util.Constants.CURRENT_USER;

import java.util.UUID;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.hbrs.project.wram.control.LoginControl;
import org.hbrs.project.wram.control.entwickler.profile.EntwicklerProfileService;
import org.hbrs.project.wram.control.entwickler.user.EntwicklerService;
import org.hbrs.project.wram.model.entwickler.profile.EntwicklerProfil;
import org.hbrs.project.wram.model.entwickler.profile.EntwicklerProfilDTO;
import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import lombok.extern.slf4j.Slf4j;

/**
 * Diese View dient dazu einem als Entwickler eingeloggtem User ein Profil erstellen können.
 * Dabei wird die View innerhalb der AppView angezeigt.
 * Der Entwickler hat die Möglichkeit Informationen wie zum Beispiel seine Skills einzugeben.
 * Wenn ein Profil erstellt wurde, kann dies nachträglich bearbeitet werden.
 */

@PageTitle("EntwicklerProfilErstellen")
@CssImport("./styles/views/main/main-view.css")
@Route(value = Constants.Pages.CREATEENTWICKLERPROFIL, layout = AppView.class)
@Slf4j
public class CreateEntwicklerProfil extends Div implements BeforeEnterObserver {

    private H3 title;
    // private IntegerField photo;
    private TextField entwicklerTelnr; //todo telefon feld ergänzen
    private TextField entwicklerskills;


    RadioButtonGroup<String> oeff;

    private Button bestätigungsknopf;

    private final Binder<EntwicklerProfilDTO> entwicklerProfilDTOBinder = new Binder<>(EntwicklerProfilDTO.class);

    @Autowired
    private EntwicklerProfileService entwicklerProfileService;

    @Autowired
    private LoginControl control;

    @Autowired
    private EntwicklerService entwicklerService;

    @PostConstruct
    private void init() {

        add(createFormLayout());
        clearForm();

        bestätigungsknopf.addClickListener(e ->{
            // Kommentar: Listener in der Createformlayout funktionieren nicht, hängt vmtl mit postconstruct zusammen
            saveEntwicklerProfil(createEntwicklerProfil());
            navigateToAppView();
        });
    }

    /**
     * Wenn ein Profil erstellt worden ist oder es ein update gab,
     * bekommt der Entwickler eine Benachrichtiung, dass es erfolgreich funktioniert hat.
     */
    private void navigateToAppView() {
        UI.getCurrent().navigate("Appview"); // Appview
        Notification.show("Entwicklerprofil erfolgreich erstellt.", 3000, Notification.Position.MIDDLE);
    }

    /**
     * Diese Methode erzeugt das Formlayout, welches Komponenten zur Eingabe von ProfilInformationen enthält.
     *
     * @return Instanz des Layouts
     */
    public VerticalLayout createFormLayout() {
        VerticalLayout formLayout = new VerticalLayout();

        Image image = new Image("src/main/resources/image/avatar-1.jpg", "Profile Image");
        image.setId("profileImage");
        title = new H3("Entwicklerprofil erstellen");
        entwicklerTelnr = new TextField();
        entwicklerTelnr.setLabel("Telefonnummer");
        entwicklerskills = new TextField();
        entwicklerskills.setLabel("Skills");

        setRequiredIndicatorVisible(entwicklerTelnr, entwicklerskills);

        bestätigungsknopf = new Button("Jetzt Erstellen");
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        //funktioniert nicht
      // bestätigungsknopf.addClickListener(saveEntwicklerProfil(createEntwicklerProfil()));

        RouterLink appView = new RouterLink("Zurück zur Uebersicht", AppView.class);

        formLayout.add(title/*, image*/, entwicklerTelnr, entwicklerskills, bestätigungsknopf, appView);

        // Max width of the Form
        formLayout.setMaxWidth("900px");
        return formLayout;
    }

    /**
     * Diese Methode dient dazu ein Entwicklerprofil zu erstellen mithilfe eines Builders werden die Daten
     * aus den Textfeldern genommen.
     * @return
     */
    private EntwicklerProfil createEntwicklerProfil() {
        log.info("Das ist die Rückgabe in create:" + UI.getCurrent().getSession().getAttribute(CURRENT_USER));
        UUID userId = (UUID) UI.getCurrent().getSession().getAttribute(CURRENT_USER);

        Entwickler entwickler = null;

        if (this.entwicklerService != null) {
            entwickler=this.entwicklerService.getByUserId(userId);
        }
        if(entwickler==null){
            log.info("Entwickler is Null...");
        }else {
            log.info("Entwickler with ID " +entwickler.getId().toString());
        }

        return EntwicklerProfil.builder().entwickler(entwickler).image(0).phone(entwicklerTelnr.getValue()).skills(entwicklerskills.getValue()).build();

    }

    /**
     * Diese Methode dient dazu das Entwicklerprofil mithilfe eines Services in der Datenbank zu speichern.
     *
     * @param entwicklerProfil
     */
    private void saveEntwicklerProfil(EntwicklerProfil entwicklerProfil) {
        //TODO: prüfen der eingabe
        this.entwicklerProfileService.doCreatEntwickler(entwicklerProfil);
    }
    /*
    private ComponentEventListener<ClickEvent<Button>> saveEntwicklerProfil(EntwicklerProfil entwicklerProfil) {
        return event -> {
            this.entwicklerProfileService.doCreatEntwickler(entwicklerProfil);
                        };
    }*/

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
            beforeEnterEvent.rerouteTo(Constants.Pages.MAIN_VIEW);
        }
    }

    private void clearForm() {
        entwicklerProfilDTOBinder.setBean(new EntwicklerProfilDTO());
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }
}
