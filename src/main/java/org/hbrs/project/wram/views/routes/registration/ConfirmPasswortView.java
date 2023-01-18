/**
 * @outhor lukas
 * @vision 1.0
 * @Zuletzt bearbeiret: 06.12.22 by Salah
 */
package org.hbrs.project.wram.views.routes.registration;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserRepository;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppViewOutside;
import org.hbrs.project.wram.views.routes.Notify;
import org.hbrs.project.wram.views.routes.main.LoginView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


@PageTitle("Passwort bestätigen")
@Route(value = Constants.Pages.Confirm_Password_VIEW, layout = AppViewOutside.class)
@Slf4j
public class ConfirmPasswortView extends Div implements HasUrlParameter<String> {


    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    private H3 title;


    private String username;

    private Button bestätigungsknopf;

    @PostConstruct
    private void init() {
        add(createFormLayout());
        this.setWidthFull();
        bestätigungsknopf.addClickListener(createUserAndRollEventListener());
    }


    /**
     * Erstelle temporäres passwort, falls username existiert
     *
     * @return ComponentEventListener<ClickEvent < Button>>
     */
    private ComponentEventListener<ClickEvent<Button>> createUserAndRollEventListener() {
        return e -> {
            User us = userRepository.findUserByUsername(username);
            us.setVerified(true);
            userRepository.save(us);
            UI.getCurrent().navigate(Constants.Pages.LOGIN_VIEW); // Login-View
            Notify.notifyAfterUpdateWithOkay("Hallo " + us.getUsername() + "!\nDein Passwort wurde erfolgreich aktualisiert. Du kannst dich nun einloggen.");

        };
    }

    /**
     * Diese Methode erzeugt das Formlayout, welches Username annimmt
     *
     * @return Instanz des Layouts
     */
    public Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        title = new H3("Bitte bestätige das neue Passwort");

        bestätigungsknopf = new Button("Jetzt Passwort bestätigen");
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        RouterLink loginView = new RouterLink("Zurück zum Login", LoginView.class);
        formLayout.add(title, bestätigungsknopf, loginView);

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
        TextField field = new TextField("Gegebenenfalls bist du schon verifiziert.\nLogge dich in diesem Fall ein.");
        div.add(field);
        layout.add(header, div);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        if (s != null) {
            this.username = s;
        }
    }
}
