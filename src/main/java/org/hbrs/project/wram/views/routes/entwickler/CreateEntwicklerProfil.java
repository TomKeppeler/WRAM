/**
 * @outhor Lukas
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 *
 */
package org.hbrs.project.wram.views.routes.entwickler;

import static org.hbrs.project.wram.util.Constants.CURRENT_USER;

import java.util.UUID;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

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

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
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

    private H2 title;
    private TextField firstname;
    private TextField name;
    private TextField email;
    private TextField phone;
    private TextArea skills;

    private Image image = new Image("images/defaultP.png", "Profile Picture");
    private UploadButton uploadButton;
    
    private Button bestätigungsknopf;
    
    private final Binder<EntwicklerDTO> entwicklerDTOBinder = new Binder<>(EntwicklerDTO.class);
    
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

        bestätigungsknopf.addClickListener(e ->{
            if(entwicklerDTOBinder.validate().isOk()){
                saveEntwicklerProfil(createEntwicklerProfil());
                navigateToAppView();
            }else {
                Notification.show("Bitte überprüfen Sie die Eingaben.");
            }
        });
    }

    /**
     * Wenn ein Profil erstellt worden ist oder es ein update gab,
     * bekommt der Entwickler eine Benachrichtiung, dass es erfolgreich funktioniert hat.
     */
    private void navigateToAppView() {
        UI.getCurrent().navigate(Constants.Pages.WELCOME_VIEW); // Appview
        Notification.show("Entwicklerprofil erfolgreich erstellt.", 3000, Notification.Position.MIDDLE);
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
        email = new TextField("Email");
        phone = new TextField("Telefonnummer");
        skills = new TextArea("Skills");
        skills.setWidthFull();
        this.uploadButton = new UploadButton(aktuellerEntwickler.getId());
        this.image.setWidth("175px");
        this.image.setHeight("175px");
        bestätigungsknopf = new Button("Jetzt Erstellen/Updaten");
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        RouterLink backlink = new RouterLink("Zurück zur Uebersicht", LandingView.class);
        firstname.setValue(aktuellerEntwickler.getFirstname());
        name.setValue(aktuellerEntwickler.getName());
        email.setValue(aktuellerEntwickler.getUser().getEmail());
        email.setReadOnly(true);
        if(aktuellerEntwickler.getImage() != null){
            setProfileImage();
        }
        if(aktuellerEntwickler.getPhone()==null || aktuellerEntwickler.getPhone().length()==0){
            phone.setPlaceholder("Telefonnummer");
        }else {
            phone.setValue(aktuellerEntwickler.getPhone());
        }
        if(aktuellerEntwickler.getSkills()==null || aktuellerEntwickler.getSkills().length()==0){
            skills.setPlaceholder("Skills");
        }else{
            skills.setValue(aktuellerEntwickler.getSkills());
        }

        setRequiredIndicatorVisible(firstname, name);
        formLayout.add(this.image, this.uploadButton, title, firstname, name, email, phone, skills, bestätigungsknopf, backlink);
        formLayout.setMaxWidth("900px");
        return formLayout;
    }

    /**
     * @apiNote the image is fetcht from the database via the entwicklerService and set in the image component 
     * @autor: @tkeppe2s (Tom Keppeler)
     */
    private void setProfileImage() {
        Entwickler aktuellerEntwickler = entwicklerService.findEntwicklerByUserId((UUID) UI.getCurrent().getSession().getAttribute(CURRENT_USER));
        Image profilImage = Utils.generateImage(this.entwicklerService.getImage(aktuellerEntwickler.getId()));
        this.image.setSrc(profilImage.getSrc());
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
     * Views (hier: ShowCarsView und EnterCarView) ab.
     *
     */
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (control.getCurrentUser() == null) {
            beforeEnterEvent.rerouteTo(Constants.Pages.MAIN_VIEW);
        }
    }

    private void validateFields(){
        entwicklerDTOBinder.forField(firstname)
                .withValidator(binderFirstname -> !binderFirstname.isEmpty(), "Bitte Vorname angeben").asRequired()
                .bind(EntwicklerDTO::getFirstname, EntwicklerDTO::setFirstname);
        entwicklerDTOBinder.forField(name)
                .withValidator(binderName -> !binderName.isEmpty(), "Bitte Nachname angeben").asRequired()
                .bind(EntwicklerDTO::getName, EntwicklerDTO::setName);
        entwicklerDTOBinder.forField(phone)
                .withValidator(Utils::telefonnummerCheck, "Bitte korrekte Nummer angeben")
                .bind(EntwicklerDTO::getPhone, EntwicklerDTO::setPhone);

    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

    private void setMaxCharForFields() {
        skills.setMaxLength(255);
        skills.setHelperText(skills.getValue().length() + "/" + 255);
        skills.setValueChangeMode(ValueChangeMode.EAGER);
        skills.addValueChangeListener(e ->
                e.getSource().setHelperText(e.getValue().length() + "/" + 255)
        );
    }
}
