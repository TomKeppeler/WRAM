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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.model.user.UserRepository;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.util.Encryption;
import org.hbrs.project.wram.views.common.layouts.AppViewOutside;
import org.hbrs.project.wram.views.routes.main.LoginView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;


@PageTitle("passwort_erneuern")
@Route(value = Constants.Pages.Password_VIEW,layout = AppViewOutside.class)
@Slf4j
public class UpdatePasswordView extends Div  {


    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    private H3 title = new H3("Erstellen und bestätigen sie ein neues passwort");
    private TextField username = new TextField("Username");
    private PasswordField passwort = new PasswordField("Passwort");
    private PasswordField passwortWiederholung = new PasswordField("Wiederholen Sie Ihr Passwort");

    private Button bestätigungsknopf = new Button("Abschicken");
    private boolean added = false;


    @PostConstruct
    private void init() {

        if(!added) {
            add(createFormLayout());
            added = true;
        }
        this.setWidthFull();
        bestätigungsknopf.addClickListener(createUserAndRollEventListener());
    }

    /**
     * Erzeuge neues Passwort und speichere es ab
     *
     * @return ComponentEventListener<ClickEvent<Button>>
     */
    private ComponentEventListener<ClickEvent<Button>> createUserAndRollEventListener() {
        return e -> {
            String tmpuser=username.getValue();
            User user=userRepository.findUserByUsername(tmpuser);
            String newPassword=passwortWiederholung.getValue();
            if(user==null){
                 Notification.show("Username falsch!!!!");
             }
             if(newPassword!=null&&service.verifyNewPassword(newPassword)&&userRepository.findUserByPassword(newPassword)==null&&passwort.getValue().equals(passwortWiederholung.getValue())) {
                 user.setPassword(Encryption.sha256(passwortWiederholung.getValue()));
                 user.setVerified(false);
                 userRepository.save(user);
                 try {
                     service.generatePassword(tmpuser,"localhost:8080");
                 } catch (UnsupportedEncodingException ex) {
                     ex.printStackTrace();
                 } catch (MessagingException ex) {
                     ex.printStackTrace();
                 }

             }else{
                 Notification.show("passwort exisitert schon oder ist null");
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
        formLayout.add(title,username, passwort, passwortWiederholung,bestätigungsknopf, loginView);

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
        TextField field = new TextField("Gegebenenfalls sind Sie schon verifiziert.\nLoggen Sie sich in diesem Fall ein.");
        div.add(field);
        layout.add(header, div);
    }

}
