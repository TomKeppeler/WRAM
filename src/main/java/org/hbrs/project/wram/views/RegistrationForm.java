package org.hbrs.project.wram.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;

import java.util.stream.Stream;

import com.vaadin.flow.router.RouterLink;
import org.hbrs.project.wram.control.RegisterControl;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerDTO;
import org.hbrs.project.wram.model.manager.ManagerDTO;
import org.hbrs.project.wram.model.reviewer.ReviewerDTO;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.util.Utils;

@Route(value="Registrieren")
public class RegistrationForm extends VerticalLayout {
    private H3 title;

    private TextField vorname;
    private TextField nachname;

    private EmailField email;

    private PasswordField passwort;
    private PasswordField passwortWiederholung;

    RadioButtonGroup<String> rolle;

    private Checkbox allowMarketing;

    private Span errorMessageField;

    private Button bestätigungsknopf;

    private Binder<UserDTO> userDTOBinder = new Binder<>(UserDTO.class);
    private Binder<ManagerDTO> managerDTOBinder = new Binder<>(ManagerDTO.class);
    private Binder<ReviewerDTO> reviewerDTOBinder = new Binder<>(ReviewerDTO.class);
    private Binder<EntwicklerDTO> entwicklerDTOBinder = new Binder<>(EntwicklerDTO.class);


    public RegistrationForm() {

        add(
                createFormLayout()
        );

        userDTOBinder.forField(passwort)
                .withValidator(RegisterControl::passwortCheck, "Mind. 8 Zeichen, davon mind. eine Ziffer und ein Großbuchstabe")
                .bind(UserDTO::getPasswort, UserDTO::setPasswort);

        userDTOBinder.forField(passwortWiederholung)
                .withValidator(binderpasswortwiederholen -> binderpasswortwiederholen.equals(passwort.getValue()), "Passwörter stimmen nicht überein")
                .bind(UserDTO::getPasswort, UserDTO::setPasswort);

        // TODO: 25.10.2022 EmailValidator Klasse nutzen
        userDTOBinder.forField(email)
                .withValidator(Utils::emailadresseCheck, "Email Muster ungültig")
                .bind(UserDTO::getEmail, UserDTO::setEmail);


        bestätigungsknopf.addClickListener(e->{
            if(userDTOBinder.validate().isOk()){
                Notification.show("User in DB schreiben",3000,Notification.Position.MIDDLE);
                // TODO: 25.10.2022 Registrierung umsetzen 
                RegisterControl.registerMethod(vorname.getValue(), nachname.getValue(), email.getValue(), passwort.getValue(), rolle.getValue());
            }else{
                Notification.show("Bitte überprüfen Sie Ihre Eingaben!",3000,Notification.Position.MIDDLE);
            }

        });
    }

    public Component createFormLayout(){
        FormLayout formLayout = new FormLayout();
        title = new H3("Registrieren Sie sich");
        vorname = new TextField("Vorname");
        nachname = new TextField("Nachname");
        email = new EmailField("Email");
        //Radiobutton für rolle
        rolle = new RadioButtonGroup<>();
        rolle.setLabel("Wählen Sie Ihre Rolle");
        rolle.setItems("Projektmanager", "Reviewer", "Entwickler");
        rolle.setValue("Projektmanager");
        rolle.setEnabled(true);

        passwort = new PasswordField("Passwort");
        passwortWiederholung = new PasswordField("Wiederholen Sie Ihr Passwort");

        setRequiredIndicatorVisible(vorname, nachname, email, passwort,rolle,
                passwortWiederholung);

        errorMessageField = new Span();

        bestätigungsknopf = new Button("Jetzt Registrieren");
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        RouterLink loginView = new RouterLink("Zurück zum Login", MainView.class);

        formLayout.add(title, vorname, nachname, email, passwort,
                passwortWiederholung,rolle,errorMessageField,
                bestätigungsknopf, loginView);

        // Max width of the Form
        setMaxWidth("900px");

        // Allow the form layout to be responsive.
        // On device widths 0-490px we have one column.
        // Otherwise, we have two columns.

        //formLayout.setResponsiveSteps(
        //      new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
        //    new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        // These components always take full width
        formLayout.setColspan(title, 2);
        formLayout.setColspan(email, 2);
        formLayout.setColspan(errorMessageField, 2);
        formLayout.setColspan(bestätigungsknopf, 2);


        return formLayout;
    }

    public PasswordField getPasswordField() { return passwort; }

    public PasswordField getPasswordConfirmField() { return passwortWiederholung; }

    public Span getErrorMessageField() { return errorMessageField; }

    public Button getSubmitButton() { return bestätigungsknopf; }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }
}
