package org.hbrs.project.wram.views;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.hbrs.project.wram.Control.RegisterControl;

import java.util.stream.Stream;
@Route(value="Registrieren")
public class RegistrationForm extends FormLayout {
    private H3 title;

    private TextField vorname;
    private TextField nachname;

    private EmailField email;

    private PasswordField passwort;
    private PasswordField passwortWiederholung;

    private Checkbox allowMarketing;

    private Span errorMessageField;

    private Button bestätigungsknopf;


    public RegistrationForm() {
        title = new H3("Registrieren Sie sich");
        vorname = new TextField("Vorname");
        nachname = new TextField("Nachname");
        email = new EmailField("Email");
        //Radiobutton für rolle
        RadioButtonGroup<String> rolle = new RadioButtonGroup<>();
        rolle.setLabel("Wählen Sie Ihre Rolle");
        rolle.setItems("Projektmanager", "Reviewer", "Entwickler");
        rolle.setValue("Projektmanager");
        rolle.setEnabled(true);
        add(rolle);

        passwort = new PasswordField("Passwort");
        passwortWiederholung = new PasswordField("Wiederholen Sie Ihr Passwort");

        setRequiredIndicatorVisible(vorname, nachname, email, passwort,rolle,
                passwortWiederholung);

        errorMessageField = new Span();


        bestätigungsknopf = new Button("Jetzt Registrieren");
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        bestätigungsknopf.addClickListener(e->{
           boolean b=RegisterControl.confirmPasswort(passwort.getValue(),passwortWiederholung.getValue());
            if(!b){
                Notification.show("Fehler im Passwort:Mindestlänge 8 Zeichen, zudem muss mindestens ein Grossbuchstabe und eine Ziffer vorhanden sein!");
            }else {
                RegisterControl.registerMethod(vorname.getValue(), nachname.getValue(), email.getValue(), passwort.getValue(), rolle.getValue());
            }
        });

        add(title, vorname, nachname, email, passwort,
                passwortWiederholung,rolle,errorMessageField,
                bestätigungsknopf);

        // Max width of the Form
        setMaxWidth("500px");

        // Allow the form layout to be responsive.
        // On device widths 0-490px we have one column.
        // Otherwise, we have two columns.
        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));

        // These components always take full width
        setColspan(title, 2);
        setColspan(email, 2);
        setColspan(errorMessageField, 2);
        setColspan(bestätigungsknopf, 2);
    }

    public PasswordField getPasswordField() { return passwort; }

    public PasswordField getPasswordConfirmField() { return passwortWiederholung; }

    public Span getErrorMessageField() { return errorMessageField; }

    public Button getSubmitButton() { return bestätigungsknopf; }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }
}
