/**
 * @outhor Lukas
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.views.routes.entwickler;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.LoginControl;
import org.hbrs.project.wram.control.entwickler.EntwicklerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.model.entwickler.EntwicklerDTO;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.util.Utils;
import org.hbrs.project.wram.views.common.layouts.AppView;
import org.hbrs.project.wram.views.common.layouts.components.UploadButton;
import org.hbrs.project.wram.views.routes.main.LandingView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hbrs.project.wram.util.Constants.CURRENT_USER;

/**
 * Diese View dient dazu einem als Entwickler eingeloggtem User ein Profil erstellen können.
 * Dabei wird die View innerhalb der AppView angezeigt.
 * Der Entwickler hat die Möglichkeit Informationen wie zum Beispiel seine Skills einzugeben.
 * Wenn ein Profil erstellt wurde, kann dies nachträglich bearbeitet werden.
 */

@PageTitle("Profil erstellen")
@CssImport("./styles/views/main/main-view.css")
@Route(value = Constants.Pages.CREATEENTWICKLERPROFIL, layout = AppView.class)
@Slf4j
public class CreateEntwicklerProfil extends Div implements BeforeEnterObserver {

    private final Binder<EntwicklerDTO> entwicklerDTOBinder = new Binder<>(EntwicklerDTO.class);
    private H2 title;
    private TextField firstname;
    private TextField name;
    private TextField email;
    private TextField username;
    private TextField phone;
    private TextArea skills;
    private Image image = new Image("images/defaultP.png", "Profile Picture");
    private UploadButton uploadButton;
    private Button bestätigungsknopf;
    @Autowired
    private EntwicklerService entwicklerService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginControl control;

    @PostConstruct
    private void init() {
        add(createFormLayout());
        validateFields();
        setMaxCharForFields();

        bestätigungsknopf.addClickListener(e -> {
            if (entwicklerDTOBinder.validate().isOk()) {
                saveEntwicklerProfil(createEntwicklerProfil());
                navigateToAppView();
            } else {
                Notification.show("Bitte überprüfe deine Eingaben.");
            }
        });
    }

    /**
     * Wenn ein Profil erstellt worden ist oder es ein update gab,
     * bekommt der Entwickler eine Benachrichtiung, dass es erfolgreich funktioniert hat.
     */
    private void navigateToAppView() {
        notifyAfterUpdate();
        //Notification.show("Entwicklerprofil erfolgreich erstellt.", 3000, Notification.Position.MIDDLE);
    }

    /**
     * Diese Methode erzeugt das Formlayout, welches Komponenten zur Eingabe von ProfilInformationen enthält.
     *
     * @return Instanz des Layouts
     */
    public VerticalLayout createFormLayout() {
        VerticalLayout formLayout = new VerticalLayout();
        Entwickler aktuellerEntwickler = entwicklerService.findEntwicklerByUserId((UUID) UI.getCurrent().getSession().getAttribute(CURRENT_USER));

        title = new H2("Entwicklerprofil");
        firstname = new TextField("Vorname");
        name = new TextField("Nachname");
        email = new TextField("E-Mail");
        username = new TextField("Username");
        phone = new TextField("Telefonnummer");
        skills = new TextArea("Skills");
        skills.setWidthFull();
        this.uploadButton = new UploadButton(aktuellerEntwickler.getId(), this.entwicklerService);
        styleUploadButton();

        bestätigungsknopf = new Button("Profil aktualisieren");
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        RouterLink backlink = new RouterLink("Zurück zur Übersicht", LandingView.class);
        firstname.setValue(aktuellerEntwickler.getFirstname());
        name.setValue(aktuellerEntwickler.getName());
        email.setValue(aktuellerEntwickler.getUser().getEmail());
        email.setReadOnly(true);
        username.setValue(aktuellerEntwickler.getUser().getUsername());
        username.setReadOnly(true);
        if (aktuellerEntwickler.getImage() != null) {
            setProfileImage();
        }
        if (aktuellerEntwickler.getPhone() == null || aktuellerEntwickler.getPhone().length() == 0) {
            phone.setPlaceholder("Telefonnummer");
        } else {
            phone.setValue(aktuellerEntwickler.getPhone());
        }
        if (aktuellerEntwickler.getSkills() == null || aktuellerEntwickler.getSkills().length() == 0) {
            skills.setPlaceholder("Skills");
        } else {
            skills.setValue(aktuellerEntwickler.getSkills());
        }

        setRequiredIndicatorVisible(firstname, name);
        formLayout.add(title, this.image, this.uploadButton, firstname, name, email, username, phone, skills, bestätigungsknopf, backlink);
        formLayout.setMaxWidth("900px");
        return formLayout;
    }

    /**
     * This method sets the style for the upload button.
     * @author: @tkeppe2s (Tom Keppeler)
     */
    private void styleUploadButton() {
        this.image.setHeight("300px");
        this.image.getStyle().set("border-radius", "30%");
        this.image.getStyle().set("margin", "1 auto");
        this.image.getStyle().set("border", "5px solid #ddd");
    }

    /**
     * @apiNote the image is fetcht from the database via the entwicklerService and set in the image component 
     * @autor: @tkeppe2s (Tom Keppeler)
     */
    private void setProfileImage() {
        Entwickler aktuellerEntwickler = entwicklerService.findEntwicklerByUserId((UUID) UI.getCurrent().getSession().getAttribute(CURRENT_USER));
        this.image = Utils.generateImage(this.entwicklerService.getImage(aktuellerEntwickler.getId()));
        styleUploadButton();
    }

    /**
     * Diese Methode dient dazu das Entwicklerprofil mithilfe eines Services in der Datenbank zu speichern.
     *
     * @param entwickler
     */
    private void saveEntwicklerProfil(Entwickler entwickler) {
        this.entwicklerService.doCreatEntwickler(entwickler);
    }

    /**
     * Diese Methode dient dazu ein Entwicklerprofil zu erstellen mithilfe eines Builders werden die Daten
     * aus den Textfeldern genommen.
     * @return
     */
    private Entwickler createEntwicklerProfil() {

        User user = userService.findUserById((UUID) UI.getCurrent().getSession().getAttribute(CURRENT_USER));
        Entwickler entwicklerAlt = entwicklerService.findEntwicklerByUserId((UUID) UI.getCurrent().getSession().getAttribute(CURRENT_USER));

        Entwickler entwicklerNeu = Entwickler.builder()
                .firstname(this.firstname.getValue())
                .name(this.name.getValue())
                .phone(this.phone.getValue())
                .skills(this.skills.getValue())
                .build();
        entwicklerNeu.setUser(user);
        entwicklerNeu.setKundenprojekt(entwicklerAlt.getKundenprojekt());
        entwicklerNeu.setId(entwicklerAlt.getId());
        entwicklerNeu.setImage(entwicklerAlt.getImage());
        return entwicklerNeu;
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
     */
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (control.getCurrentUser() == null) {
            beforeEnterEvent.rerouteTo(Constants.Pages.MAIN_VIEW);
        }
    }

    /**
     * Diese Methode dient dazu, die Pflichtfelder zu markieren und mit einem
     * Helpertext zu versehen, wenn diese leer gelassen worden sind.
     */
    private void validateFields() {
        entwicklerDTOBinder.forField(firstname)
                .withValidator(binderFirstname -> !binderFirstname.isEmpty(), "Bitte Vorname angeben").asRequired()
                .bind(EntwicklerDTO::getFirstname, EntwicklerDTO::setFirstname);
        entwicklerDTOBinder.forField(name)
                .withValidator(binderName -> !binderName.isEmpty(), "Bitte Nachname angeben").asRequired()
                .bind(EntwicklerDTO::getName, EntwicklerDTO::setName);
        entwicklerDTOBinder.forField(phone)
                .withValidator(Utils::telefonnummerCheck, "Bitte eine gültige Telefonnummer angeben")
                .bind(EntwicklerDTO::getPhone, EntwicklerDTO::setPhone);

    }

    /**
     * Diese Methode dient dazu, die Eingabefelder als Pflichtfelder zu markieren.
     *
     * @param components
     */
    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

    /**
     * Diese Methode dient dazu als Helpertext unter den Eingabefeldern anzuzeigen,
     * wie viele Zeichen erlaubt sind.
     */
    private void setMaxCharForFields() {
        skills.setMaxLength(255);
        skills.setHelperText(skills.getValue().length() + "/" + 255);
        skills.setValueChangeMode(ValueChangeMode.EAGER);
        skills.addValueChangeListener(e ->
                e.getSource().setHelperText(e.getValue().length() + "/" + 255)
        );
    }

    /**
     * benachrichtigung bei einem update von Profil
     */
    private void notifyAfterUpdate() {
        Dialog confirm = new Dialog();
        confirm.setId("confirm-profile-update");
        confirm.open();

        VerticalLayout dialoglayout = new VerticalLayout(
                new Text("Dein Profil wurde erfolgreich erstellt bzw. aktualisiert."),
                new Button("Ok", e -> {
                    confirm.close();
                    UI.getCurrent().getPage().reload();
                }
                )
        );
        dialoglayout.setId("confirm-dialog-layout");

        confirm.add(
                dialoglayout
        );
        confirm.setCloseOnEsc(true);
        confirm.setCloseOnOutsideClick(false);
    }
}
