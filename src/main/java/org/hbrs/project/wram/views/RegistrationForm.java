package org.hbrs.project.wram.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
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
import java.util.stream.Stream;
import com.vaadin.flow.router.RouterLink;
import org.hbrs.project.wram.control.RegisterControl;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerDTO;
import org.hbrs.project.wram.model.manager.ManagerDTO;
import org.hbrs.project.wram.model.reviewer.ReviewerDTO;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Registrierung")
@Route(value=Constants.Pages.REGISTRATION)
public class RegistrationForm extends VerticalLayout {

    String rolleProjektmanager = "Projektmanager";
    String rolleReviewer = "Reviewer";
    String rolleEntwickler = "Entwickler";
    private H3 title;

    private TextField firstname;
    private TextField name;
    private TextField username;
    private EmailField email;
    private PasswordField passwort;
    private PasswordField passwortWiederholung;
    RadioButtonGroup<String> rolle;

    private Button bestätigungsknopf;

    private Binder<UserDTO> userDTOBinder = new Binder<>(UserDTO.class);
    private Binder<ManagerDTO> managerDTOBinder = new Binder<>(ManagerDTO.class);
    private Binder<ReviewerDTO> reviewerDTOBinder = new Binder<>(ReviewerDTO.class);
    private Binder<EntwicklerDTO> entwicklerDTOBinder = new Binder<>(EntwicklerDTO.class);

    @Autowired
    private RegisterControl registerControl;

    public RegistrationForm() {

        add(createFormLayout());
        bindFields();
        clearForm();

        bestätigungsknopf.addClickListener(e->{
            if(userDTOBinder.validate().isOk()){
                if (registerControl.isEmailAlreadyInDatabase(userDTOBinder.getBean())){
                    Notification.show("Es existiert bereits ein Account mit der E-mail adresse "+ userDTOBinder.getBean()
                            .getEmail(), 3000,Notification.Position.MIDDLE);
                }else if(registerControl.isUsernameAlreadyInDatabase(userDTOBinder.getBean())){
                    Notification.show("Es existiert bereits ein Account mit dem Username "+ userDTOBinder.getBean()
                            .getUsername(), 3000,Notification.Position.MIDDLE);
                } else {
                    if (rolle.getValue().equals(rolleEntwickler)) {
                        if (registerControl.saveUserAndEntwickler(userDTOBinder.getBean(), entwicklerDTOBinder.getBean())) {
                            setAttributeAndNavigate(userDTOBinder.getBean());
                        } else Notification.show("Etwas ist schiefgelaufen!", 3000, Notification.Position.MIDDLE);
                    }else if (rolle.getValue().equals(rolleProjektmanager)){
                        if(registerControl.saveUserAndManager(userDTOBinder.getBean(), managerDTOBinder.getBean())){
                            setAttributeAndNavigate(userDTOBinder.getBean());
                        }else Notification.show("Etwas ist schiefgelaufen!", 3000, Notification.Position.MIDDLE);
                    }else if (rolle.getValue().equals(rolleReviewer)){
                        if(registerControl.saveUserAndReviewer(userDTOBinder.getBean(), reviewerDTOBinder.getBean())){
                            setAttributeAndNavigate(userDTOBinder.getBean());
                        }else Notification.show("Etwas ist schiefgelaufen!", 3000, Notification.Position.MIDDLE);
                    }else{
                        Notification.show("Fehler",3000,Notification.Position.MIDDLE);
                    }
                }
            }
            else{Notification.show("Bitte überprüfen Sie Ihre Eingaben!",3000,Notification.Position.MIDDLE);}
        });
    }

    private void setAttributeAndNavigate(UserDTO userDTO) {
        //UI.getCurrent().getSession().setAttribute(Constants.CURRENT_USER, userDTO);
        UI.getCurrent().navigate("");   //Login-View
        Notification.show("Sie haben sich erfolgreich registriert und können sich nun einloggen.", 3000, Notification.Position.MIDDLE);
    }
    public Component createFormLayout(){
        FormLayout formLayout = new FormLayout();
        title = new H3("Registrieren Sie sich");
        firstname = new TextField("Vorname");
        name = new TextField("Nachname");
        email = new EmailField("Email");
        username= new TextField("Username");
        passwort = new PasswordField("Passwort");
        passwortWiederholung = new PasswordField("Wiederholen Sie Ihr Passwort");
        //Radiobutton für rolle
        rolle = new RadioButtonGroup<>();
        rolle.setLabel("Wählen Sie Ihre Rolle");
        rolle.setItems(rolleProjektmanager, rolleReviewer, rolleEntwickler);
        rolle.setValue(rolleProjektmanager);
        rolle.setEnabled(true);

        setRequiredIndicatorVisible(firstname, name, username, email, passwort,rolle, passwortWiederholung);
        bestätigungsknopf = new Button("Jetzt Registrieren");
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        RouterLink loginView = new RouterLink("Zurück zum Login", LoginView.class);
        formLayout.add(title, firstname, name,username, email, passwort, passwortWiederholung,rolle, bestätigungsknopf, loginView);

        // Max width of the Form
        setMaxWidth("900px");
        formLayout.setColspan(title, 2);
        formLayout.setColspan(email, 2);
        formLayout.setColspan(bestätigungsknopf, 2);
        return formLayout;
    }

    private void bindFields(){
        entwicklerDTOBinder.forField(firstname)
                .withValidator(binderfirstname -> binderfirstname.length() > 0, "Bitte Vornamen angeben!")
                .withValidator(Utils::isAlpha, "Vorname darf nur Buchstaben enthalten")
                .bind(EntwicklerDTO::getFirstname, EntwicklerDTO::setFirstname);
        entwicklerDTOBinder.forField(name)
                .withValidator(bindername -> bindername.length() > 0, "Bitte Vornamen angeben!")
                .withValidator(Utils::isAlpha, "Nachname darf nur Buchstaben enthalten")
                .bind(EntwicklerDTO::getName, EntwicklerDTO::setName);

        managerDTOBinder.forField(firstname)
                .withValidator(binderfirstname -> binderfirstname.length() > 0, "Bitte Vornamen angeben!")
                .withValidator(Utils::isAlpha, "Vorname darf nur Buchstaben enthalten")
                .bind(ManagerDTO::getFirstname, ManagerDTO::setFirstname);
        managerDTOBinder.forField(name)
                .withValidator(bindername -> bindername.length() > 0, "Bitte Vornamen angeben!")
                .withValidator(Utils::isAlpha, "Nachname darf nur Buchstaben enthalten")
                .bind(ManagerDTO::getName, ManagerDTO::setName);

        reviewerDTOBinder.forField(firstname)
                .withValidator(binderfirstname -> binderfirstname.length() > 0, "Bitte Vornamen angeben!")
                .withValidator(Utils::isAlpha, "Vorname darf nur Buchstaben enthalten")
                .bind(ReviewerDTO::getFirstname, ReviewerDTO::setFirstname);
        reviewerDTOBinder.forField(name)
                .withValidator(bindername -> bindername.length() > 0, "Bitte Vornamen angeben!")
                .withValidator(Utils::isAlpha, "Nachname darf nur Buchstaben enthalten")
                .bind(ReviewerDTO::getName, ReviewerDTO::setName);


        userDTOBinder.forField(username)
                .withValidator(e -> e.length() > 0, "Bitte Username angeben!")
                .bind(UserDTO::getUsername, UserDTO::setUsername);
        userDTOBinder.forField(passwort)
                .withValidator(e -> e.length() > 0, "Bitte Passwort angeben!")
                .withValidator(RegisterControl::passwortCheck, "Mind. 8 Zeichen, davon mind. eine Ziffer und ein Großbuchstabe")
                .bind(UserDTO::getPassword, UserDTO::setPassword);
        userDTOBinder.forField(passwortWiederholung)
                .withValidator(binderpasswortwiederholen -> binderpasswortwiederholen.length() > 0, "Bitte Passwort wiederholen!")
                .withValidator(binderpasswortwiederholen -> binderpasswortwiederholen.equals(passwort.getValue()), "Passwörter stimmen nicht überein")
                .bind(UserDTO::getPassword, UserDTO::setPassword);
        userDTOBinder.forField(email)
                .withValidator(e -> e.length() > 0, "Bitte Email angeben!")
                .withValidator(Utils::emailadresseCheck, "Email Muster ungültig")
                .bind(UserDTO::getEmail, UserDTO::setEmail);
    }

    private void clearForm(){
        userDTOBinder.setBean(new UserDTO());
        entwicklerDTOBinder.setBean(new EntwicklerDTO());
        managerDTOBinder.setBean(new ManagerDTO());
        reviewerDTOBinder.setBean(new ReviewerDTO());
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }
}
