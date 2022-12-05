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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserRepository;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.util.Encryption;
import org.hbrs.project.wram.views.routes.main.LoginView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;


@PageTitle("username_submitten")
@Route(value = Constants.Pages.Username_VIEW)
@Slf4j
public class SubmitUsernameView extends Div  {



    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    private H3 title;
    private TextField email;



    private Button bestätigungsknopf;

    /**
     * * Übergabe des durch die URL transferierten Verificationcodes
     * @param   beforeEvent   aktueller benutzer
     * @param   s übergabeparameter für verificationcode
     */

    @PostConstruct
    private void init() {

        add(createFormLayout());
       /* clearForm();*/

       // this.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        this.setWidthFull();
        bestätigungsknopf.addClickListener(createUserAndRollEventListener());

    }



    /**
     * Erstelle temporäres passwort, falls username existiert
     * @return ComponentEventListener<ClickEvent<Button>>
     */
    private ComponentEventListener<ClickEvent<Button>> createUserAndRollEventListener() {
        return e -> {
                String tmpusername=email.getValue();
              if(userRepository.findUserByUsername(tmpusername)!=null){
                  try {
                      service.generatePassword(tmpusername,"localhost:8080");
                  } catch (UnsupportedEncodingException ex) {
                      ex.printStackTrace();
                  } catch (MessagingException ex) {
                      ex.printStackTrace();
                  }
              }else{
                  Notification.show("email abgelehnt");
              }
        };
    }
    /**
     * Diese Methode erzeugt das Formlayout, welches Username annimmt
     *
     * @return Instanz des Layouts
     */
    public Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        title = new H3("Geben sie den Username ein");

        email= new TextField("Username");


        email.setRequiredIndicatorVisible(true);
        bestätigungsknopf = new Button("Jetzt Username abgeben");
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        RouterLink loginView = new RouterLink("Zurück zum Login", LoginView.class);
        formLayout.add(title,email, bestätigungsknopf, loginView);

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
