package org.hbrs.project.wram.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.LoginControl;
import org.hbrs.project.wram.control.entwickler.profile.EntwicklerProfileService;
import org.hbrs.project.wram.control.entwickler.user.EntwicklerService;
import org.hbrs.project.wram.control.kundenprojekt.KundenprojektService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.model.entwickler.profile.EntwicklerProfil;
import org.hbrs.project.wram.model.entwickler.profile.EntwicklerProfilDTO;
import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.kundenprojekt.KundenprojektDTO;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hbrs.project.wram.util.Constants.CURRENT_USER;
@PageTitle("EntwicklerProfilErstellen")
@Route(value = Constants.Pages.CREATEENTWICKLERPROFIL, layout = AppView.class)
@Slf4j
public class CreateEntwicklerProfil extends Div implements BeforeEnterObserver {

    private H3 title;
    private IntegerField photo;
    private TextField entwicklerTelnr;
    private TextField entwicklerskills;


    RadioButtonGroup<String> oeff;

    private Button bestätigungsknopf;

    private final Binder<EntwicklerProfilDTO> entwicklerProfilDTOBinder = new Binder<>(EntwicklerProfilDTO.class);

    @Autowired
    private EntwicklerProfileService entwicklerProfileService;

    @Autowired
    private LoginControl control;

    @Autowired
    private EntwicklerService entwicklerService;

    @PostConstruct
    private void init() {

        add(createFormLayout());
        bindFields();
        clearForm();

        bestätigungsknopf.addClickListener(e ->{
            // Kommentar: Listener in der Createformlayout funktionieren nicht, hängt vmtl mit postconstruct zusammen
            saveEntwicklerProfil(createEntwicklerProfil());
            navigateToAppView();
        });

    }

    private void navigateToAppView() {
        UI.getCurrent().navigate("Appview"); // Appview
        Notification.show("Entwicklerprofil erfolgreich erstellt.", 3000, Notification.Position.MIDDLE);
    }

    public VerticalLayout createFormLayout() {
        VerticalLayout formLayout = new VerticalLayout();
        title = new H3("Entwicklerprofil erstellen");
        photo = new IntegerField();
        photo.setValue(0);
        entwicklerTelnr = new TextField();
        entwicklerTelnr.setLabel("Telefonnummer");
        entwicklerskills = new TextField();
        entwicklerskills.setLabel("Skills");

        setRequiredIndicatorVisible(entwicklerTelnr, entwicklerskills,photo);

        bestätigungsknopf = new Button("Jetzt Erstellen");
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        //funktioniert nicht
      // bestätigungsknopf.addClickListener(saveEntwicklerProfil(createEntwicklerProfil()));

        RouterLink appView = new RouterLink("Zurück zur Uebersicht", AppView.class);

        formLayout.add(title,photo, entwicklerTelnr, entwicklerskills, bestätigungsknopf, appView);

        // Max width of the Form
        formLayout.setMaxWidth("900px");

        return formLayout;
    }

    private EntwicklerProfil createEntwicklerProfil() {
        log.info("Das ist die Rückgabe in create:"+ UI.getCurrent().getSession().getAttribute(CURRENT_USER));
        UUID userId = (UUID) UI.getCurrent().getSession().getAttribute(CURRENT_USER);

        Entwickler m = null;

        if (this.entwicklerService != null) {
            m=this.entwicklerService.getByUserId(userId);
        }
        if(m==null){
            log.info("Manager is Null...");
        }else {
            log.info("Manager with ID " +m.getId().toString());
        }

        return EntwicklerProfil.builder().entwickler(m).image(photo.getValue()).phone(entwicklerTelnr.getValue()).skills(entwicklerskills.getValue()).build();

    }

    private void saveEntwicklerProfil(EntwicklerProfil entwicklerProfil) {
        this.entwicklerProfileService.doCreatEntwickler(entwicklerProfil);
    }
    /*
    private ComponentEventListener<ClickEvent<Button>> saveEntwicklerProfil(EntwicklerProfil entwicklerProfil) {
        return event -> {
            this.entwicklerProfileService.doCreatEntwickler(entwicklerProfil);
                        };
    }*/

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

    private void bindFields() {




    }

    private void clearForm() {
        entwicklerProfilDTOBinder.setBean(new EntwicklerProfilDTO());
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }
}
