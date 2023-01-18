/**
 * @outhor Lukas, Tom & Fabio
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.views.routes.registration;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.RegisterControl;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.entwickler.EntwicklerDTO;
import org.hbrs.project.wram.model.manager.ManagerDTO;
import org.hbrs.project.wram.model.reviewer.ReviewerDTO;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.model.user.UserRepository;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.util.Encryption;
import org.hbrs.project.wram.util.Utils;
import org.hbrs.project.wram.views.common.layouts.AppViewOutside;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

/**
 * Diese View dient dazu, sich Registrieren zu können.
 * Dabei kann man sich als Manager, Reviewer oder Entwickler registrieren.
 */
@PageTitle("Registrierung")
@Route(value = Constants.Pages.REGISTRATION, layout = AppViewOutside.class)
@Slf4j
public class RegistrationForm extends VerticalLayout {
    VerticalLayout layout = new VerticalLayout();

    @Autowired
    UserRepository userRepository;

    String rolleProjektmanager = "Projektmanager";
    String rolleReviewer = "Reviewer";
    String rolleEntwickler = "Entwickler";
    RadioButtonGroup<String> rolle;
    private H3 title;
    private TextField firstname;
    private TextField name;
    private TextField username;
    private EmailField email;
    private PasswordField passwort;
    private PasswordField passwortWiederholung;
    private Button bestätigungsknopf;

    private final Binder<UserDTO> userDTOBinder = new Binder<>(UserDTO.class);
    private final Binder<ManagerDTO> managerDTOBinder = new Binder<>(ManagerDTO.class);
    private final Binder<ReviewerDTO> reviewerDTOBinder = new Binder<>(ReviewerDTO.class);
    private final Binder<EntwicklerDTO> entwicklerDTOBinder = new Binder<>(EntwicklerDTO.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RegisterControl registerControl;

    @PostConstruct
    public void init() {
        add(createFormLayout());
        bindFields();
        clearForm();

        this.setHorizontalComponentAlignment(Alignment.CENTER);
        this.setWidthFull();
        bestätigungsknopf.addClickListener(createUserAndRollEventListener());
    }


    /**
     * Diese Methode dient dazu einen EventListener zu erzeugen, welcher intern alle
     * Methoden prüft, die bei der Registrierung wichtig sind.
     * Sollte alles ok sein, wird der User mit dem dazugehörigen zweiten Datensatz
     * mithilfe der Controlklasse in der Datenbank gespeichert.
     *
     * @return ComponentEventListener<ClickEvent < Button>>
     */
    private ComponentEventListener<ClickEvent<Button>> createUserAndRollEventListener() {
        return e -> {
            if (validateFields()) {
                UserDTO userDTO = userDTOBinder.getBean();
                userDTO.setPassword(Encryption.sha256(userDTO.getPassword()));
                if (this.userService.isEmailAlreadyInDatabase(userDTO)) {
                    Notification
                            .show("Es existiert bereits ein Account mit der E-Mail Adresse " + userDTO
                                    .getEmail(), 5000, Notification.Position.MIDDLE);
                } else if (this.userService.isUsernameAlreadyInDatabase(userDTO)) {
                    Notification.show("Es existiert bereits ein Account mit dem Username " + userDTO
                            .getUsername(), 5000, Notification.Position.MIDDLE);
                } else {
                    if (rolle.getValue().equals(rolleEntwickler)) {

                        if (registerControl.saveUserAndEntwickler(userDTO, entwicklerDTOBinder.getBean())) {
                          /*try {
                                userService.register(User.builder().username(userDTO.getUsername()).email(userDTO.getEmail()).password(userDTO.getPassword()).build(), "localhost:8080");
                            } catch (UnsupportedEncodingException ex) {
                                ex.printStackTrace();
                            } catch (MessagingException ex) {
                                ex.printStackTrace();
                            }*/
                            setAttributeAndNavigate(userDTO);

                        } else {
                            Notification.show("Etwas ist schiefgelaufen!", 3000, Notification.Position.MIDDLE);
                        }
                    } else if (rolle.getValue().equals(rolleProjektmanager)) {
                        if (registerControl.saveUserAndManager(userDTO, managerDTOBinder.getBean())) {
                            /*try {
                                  userService.register(userRepository.findUserById(userDTO.getId()),"localhost:8080");
                                //userService.register(User.builder().username(userDTO.getUsername()).email(userDTO.getEmail()).password(userDTO.getPassword()).build(), "localhost:8080");
                            } catch (UnsupportedEncodingException ex) {
                                ex.printStackTrace();
                            } catch (MessagingException ex) {
                                ex.printStackTrace();
                            }*/
                            setAttributeAndNavigate(userDTO);
                        } else {
                            Notification.show("Etwas ist schiefgelaufen!", 3000, Notification.Position.MIDDLE);
                        }
                    } else if (rolle.getValue().equals(rolleReviewer)) {
                        if (registerControl.saveUserAndReviewer(userDTO, reviewerDTOBinder.getBean())) {
                           /*try {
                                userService.register(User.builder().username(userDTO.getUsername()).email(userDTO.getEmail()).password(userDTO.getPassword()).build(), "localhost:8080");
                            } catch (UnsupportedEncodingException ex) {
                                ex.printStackTrace();
                            } catch (MessagingException ex) {
                                ex.printStackTrace();
                            }*/
                            setAttributeAndNavigate(userDTO);
                        } else {
                            Notification.show("Etwas ist schiefgelaufen!", 3000, Notification.Position.MIDDLE);
                        }
                    } else {
                        Notification.show("Fehler", 3000, Notification.Position.MIDDLE);
                    }
                }
            } else {
                Notification.show("Bitte überprüfe deine Eingaben!", 3000, Notification.Position.MIDDLE);
            }
        };
    }

    /**
     * Diese Methode prüft, ob alle Bindervalidierungen ok sind
     * @return boolean
     */
    private boolean validateFields() {
        return (entwicklerDTOBinder.validate().isOk() || managerDTOBinder.validate().isOk() || reviewerDTOBinder.validate().isOk()) & userDTOBinder.validate().isOk();
    }


    /**
     * Diese Methode dient dazu den User nach erfolgreicher Registrierung zur LoginView
     * weiterzuleiten und ihm eine Nachricht zu schicken, das alles funktioniert hat.
     *
     * @param userDTO des neuen Users
     */
    private void setAttributeAndNavigate(UserDTO userDTO) {
        UI.getCurrent().getSession().setAttribute(Constants.CURRENT_USER, userDTO.getId());
        UI.getCurrent().navigate(""); // Login-View
        Notification.show("Du hast dich erfolgreich registriert!", 3000,
                Notification.Position.MIDDLE);
    }


    /**
     * Diese Methode erzeugt das Formlayout, welches Komponenten zur Eingabe bei der Registrierung enthält.
     *
     * @return Instanz des Layouts
     */
    public Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        title = new H3("Willkommen bei WRAM.  ");
        firstname = new TextField("Vorname");
        name = new TextField("Nachname");
        email = new EmailField("E-Mail");
        username = new TextField("Username");
        passwort = new PasswordField("Passwort");
        passwortWiederholung = new PasswordField("Wiederhole dein Passwort");
        // Radiobutton für rolle
        rolle = new RadioButtonGroup<>();
        rolle.setLabel("Wähle deine Rolle");
        rolle.setItems(rolleProjektmanager, rolleReviewer, rolleEntwickler);
        rolle.setValue(rolleProjektmanager);
        rolle.setEnabled(true);

        setRequiredIndicatorVisible(firstname, name, username, email, passwort, rolle, passwortWiederholung);
        bestätigungsknopf = new Button("Jetzt Registrieren");
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        //RouterLink loginView = new RouterLink("Zurück zum Login", LoginView.class);
        formLayout.add(title, firstname, name, username, email, passwort, passwortWiederholung, rolle,
                bestätigungsknopf);
        //loginView

        // Max width of the Form
        formLayout.setMaxWidth("900px");
        formLayout.setColspan(title, 2);
        formLayout.setColspan(email, 2);
        formLayout.setColspan(bestätigungsknopf, 2);
        return formLayout;
    }

    /**
     * Die Methode dient dazu, die Textfelder beim der Registrierung zu validieren und zu binden.
     * Dazu wird ein Binder verwendet, welcher jedes Eingabefeld prüft.
     */
    private void bindFields() {
        entwicklerDTOBinder.forField(firstname)
                .withValidator(binderfirstname -> binderfirstname.length() > 0, "Bitte Vorname angeben")
                .withValidator(Utils::isAlpha, "Dein Vorname darf nur Buchstaben enthalten")
                .bind(EntwicklerDTO::getFirstname, EntwicklerDTO::setFirstname);
        entwicklerDTOBinder.forField(name)
                .withValidator(bindername -> bindername.length() > 0, "Bitte Nachname angeben")
                .withValidator(Utils::isAlpha, "Dein Nachname darf nur Buchstaben enthalten")
                .bind(EntwicklerDTO::getName, EntwicklerDTO::setName);

        managerDTOBinder.forField(firstname)
                .withValidator(binderfirstname -> binderfirstname.length() > 0, "Bitte Vorname angeben")
                .withValidator(Utils::isAlpha, "Dein Vorname darf nur Buchstaben enthalten")
                .bind(ManagerDTO::getFirstname, ManagerDTO::setFirstname);
        managerDTOBinder.forField(name)
                .withValidator(bindername -> bindername.length() > 0, "Bitte Nachname angeben")
                .withValidator(Utils::isAlpha, "Dein Nachname darf nur Buchstaben enthalten")
                .bind(ManagerDTO::getName, ManagerDTO::setName);

        reviewerDTOBinder.forField(firstname)
                .withValidator(binderfirstname -> binderfirstname.length() > 0, "Bitte Vorname angeben")
                .withValidator(Utils::isAlpha, "Dein Vorname darf nur Buchstaben enthalten")
                .bind(ReviewerDTO::getFirstname, ReviewerDTO::setFirstname);
        reviewerDTOBinder.forField(name)
                .withValidator(bindername -> bindername.length() > 0, "Bitte Nachname angeben")
                .withValidator(Utils::isAlpha, "Dein Nachname darf nur Buchstaben enthalten")
                .bind(ReviewerDTO::getName, ReviewerDTO::setName);

        userDTOBinder.forField(username)
                .withValidator(e -> e.length() > 0, "Bitte Username angeben")
                .bind(UserDTO::getUsername, UserDTO::setUsername);
        userDTOBinder.forField(passwort)
                .withValidator(e -> e.length() > 0, "Bitte Passwort angeben")
                .withValidator(RegisterControl::passwortCheck,
                        "Mind. 8 Zeichen, davon mind. eine Ziffer und ein Großbuchstabe")
                .bind(UserDTO::getPassword, UserDTO::setPassword);
        userDTOBinder.forField(passwortWiederholung)
                .withValidator(binderpasswortwiederholen -> binderpasswortwiederholen.length() > 0,
                        "Bitte Passwort wiederholen")
                .withValidator(binderpasswortwiederholen -> binderpasswortwiederholen.equals(passwort.getValue()),
                        "Passwörter stimmen nicht überein")
                .bind(UserDTO::getPassword, UserDTO::setPassword);
        userDTOBinder.forField(email)
                .withValidator(e -> e.length() > 0, "Bitte E-Mail angeben")
                .withValidator(Utils::emailadresseCheck, "Deine E-Mail ist ungültig")
                .bind(UserDTO::getEmail, UserDTO::setEmail);
    }

    /**
     * Diese Methode dient dazu die Textfelder zu leeren.
     */
    private void clearForm() {
        userDTOBinder.setBean(new UserDTO());
        entwicklerDTOBinder.setBean(new EntwicklerDTO());
        managerDTOBinder.setBean(new ManagerDTO());
        reviewerDTOBinder.setBean(new ReviewerDTO());
    }


    /**
     * Diese Methode dient dazu, die Eingabefelder als Pflichtfelder zu markieren.
     *
     * @param components
     */
    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }
}
