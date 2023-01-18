/**
 * @outhor Lukas
 * @vision 1.0
 * @Zuletzt bearbeiret: 06.12.22 by Salah
 */
package org.hbrs.project.wram.views.routes.registration;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserRepository;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.util.Encryption;
import org.hbrs.project.wram.views.common.layouts.AppViewOutside;
import org.hbrs.project.wram.views.routes.Notify;
import org.hbrs.project.wram.views.routes.main.LoginView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;


@PageTitle("Passwort erneuern")
@Route(value = Constants.Pages.Password_VIEW, layout = AppViewOutside.class)
@Slf4j
public class UpdatePasswordView extends Div {


    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    private final H3 title = new H3("Passwort ändern? Kein Problem.");
    private final TextField username = new TextField("Username");
    private final PasswordField passwort = new PasswordField("Passwort");
    private final PasswordField passwortWiederholung = new PasswordField("Wiederhole dein Passwort");

    private final Button bestätigungsknopf = new Button("Jetzt ändern");
    private boolean added = false;


    @PostConstruct
    private void init() {

        if (!added) {
            add(createFormLayout());
            added = true;
        }
        this.setWidthFull();
        bestätigungsknopf.addClickListener(createUserAndRollEventListener());
    }

    /**
     * Erzeuge neues Passwort und speichere es ab
     *
     * @return ComponentEventListener<ClickEvent < Button>>
     */
    private ComponentEventListener<ClickEvent<Button>> createUserAndRollEventListener() {
        return e -> {
            String tmpuser = username.getValue();
            User user = userRepository.findUserByUsername(tmpuser);
            String newPassword = passwortWiederholung.getValue();
            if (user == null) {
                Notify.notifyAfterUpdateWithOkay("Username falsch!!!!");
            }
            if (newPassword != null && service.verifyNewPassword(newPassword) && userRepository.findUserByPassword(newPassword) == null && passwort.getValue().equals(passwortWiederholung.getValue())) {
                user.setPassword(Encryption.sha256(passwortWiederholung.getValue()));
                user.setVerified(false);
                userRepository.save(user);
                try {
                    service.generatePassword(tmpuser, "http://sepp-test.inf.h-brs.de:8080/WAC-0.0.1-SNAPSHOT");
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                } catch (MessagingException ex) {
                    ex.printStackTrace();
                }

            } else if (!passwortWiederholung.getValue().equals(passwort.getValue())) {
                Notify.notifyAfterUpdateWithOkay("Deine Passwort Eingaben stimmen nicht überein");
            } else {
                //Todo
                Notification.show("Das Passwort existiert schon oder ist leer");
            }
        };
    }

    /**
     * Diese Methode erzeugt das Formlayout, welches Komponenten zur Eingabe bei des neuen Passworts enthält.
     *
     * @return Instanz des Layouts
     */
    public Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        username.setRequiredIndicatorVisible(true);
        passwort.setRequiredIndicatorVisible(true);
        passwortWiederholung.setRequiredIndicatorVisible(true);
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        RouterLink loginView = new RouterLink("Zurück zum Login", LoginView.class);
        formLayout.add(title, username, passwort, passwortWiederholung, bestätigungsknopf, loginView);

        // Max width of the Form
        formLayout.setMaxWidth("900px");
        formLayout.setColspan(title, 2);
        formLayout.setColspan(bestätigungsknopf, 2);
        return formLayout;
    }

    private void setUpErrorLayout() {
        VerticalLayout layout = new VerticalLayout();
        H1 header = new H1("Etwas ist schief gelaufen.");
        Div div = new Div();
        TextField field = new TextField("Gegebenenfalls bist du schon verifiziert.\nLogge dich in diesem Fall einfach ein.");
        div.add(field);
        layout.add(header, div);
    }

}
