package org.hbrs.project.wram.views.routes.manager;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.LoginControl;
import org.hbrs.project.wram.control.kundenprojekt.KundenprojektService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.kundenprojekt.KundenprojektDTO;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppView;
import static org.hbrs.project.wram.util.Constants.CURRENT_USER;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;

@PageTitle("Projekte Erstellen")
@Route(value = Constants.Pages.CREATEPROJECT, layout = AppView.class)
@Slf4j
public class CreateProjectForm extends Div implements BeforeEnterObserver {

    private H2 title;

    private TextField projektname;
    private TextArea skills;
    private TextArea projektbeschreibung;

    RadioButtonGroup<String> oeff;

    private Button bestätigungsknopf;

    private final Binder<KundenprojektDTO> kundenprojektDTOBinder = new Binder<>(KundenprojektDTO.class);

    @Autowired
    private KundenprojektService kundenprojektServices;

    @Autowired
    private LoginControl control;

    @Autowired
    private ManagerService managerService;

    @PostConstruct
    private void init() {

        add(createFormLayout());
        validateFields();
        clearForm();
        setMaxCharForFields();

        bestätigungsknopf.addClickListener(e -> {
            if(kundenprojektDTOBinder.validate().isOk()){
                saveKundenprojekt(createKundenprojekt());
                navigateToAppView();
            }else{Notification.show("Bitte überprüfen Sie die Eingaben.");}
        });

    }

    private void validateFields() {
        kundenprojektDTOBinder.forField(projektname)
                .withValidator(binderProjektname -> !binderProjektname.isEmpty(), "Bitte Projektname angeben").asRequired()
                .bind(KundenprojektDTO::getProjektname, KundenprojektDTO::setProjektname);
        kundenprojektDTOBinder.forField(skills)
                .withValidator(binderSkills -> !binderSkills.isEmpty(), "Bitte Skills angeben").asRequired()
                .bind(KundenprojektDTO::getSkills, KundenprojektDTO::setSkills);
        kundenprojektDTOBinder.forField(projektbeschreibung)
                .withValidator(binderProjektbeschreibung -> !binderProjektbeschreibung.isEmpty(), "Bitte Projekteschreibung angeben").asRequired()
                .bind(KundenprojektDTO::getProjektbeschreibung, KundenprojektDTO::setProjektbeschreibung);
    }

    private void navigateToAppView() {
        UI.getCurrent().navigate(Constants.Pages.PROJECTS_OVERVIEW); // Appview
        Notification.show("Projekt erfolgreich erstellt.", 3000, Notification.Position.MIDDLE);
    }

    public VerticalLayout createFormLayout() {
        VerticalLayout formLayout = new VerticalLayout();
        title = new H2("Erstelle ein neues Projekt.");
        projektname = new TextField();
        projektname.setPlaceholder("Projektname");

        skills = new TextArea();
        skills.setWidthFull();
        skills.setPlaceholder("Erforderliche Skills");

        projektbeschreibung = new TextArea();
        projektbeschreibung.setWidthFull();
        projektbeschreibung.setPlaceholder("Projektbeschreibung");
        // Radiobutton für rolle
        oeff = new RadioButtonGroup<>();
        oeff.setLabel("Soll das Projekt veröffentlicht werden?");
        oeff.setItems("veroeffentlichen", "noch nicht veroeffentlichen");
        oeff.setValue("veroeffentlichen");
        oeff.setEnabled(true);

        setRequiredIndicatorVisible(projektname, skills, projektbeschreibung);
        bestätigungsknopf = new Button("Jetzt Erstellen");
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
     //   bestätigungsknopf.addClickListener(saveKundenprojekt(createKundenprojekt()));
        RouterLink appView = new RouterLink("Zu meiner Projektübersicht", ProjectsOverview.class);
        formLayout.add(title, projektname, skills, projektbeschreibung, oeff, bestätigungsknopf, appView);

        // Max width of the Form
        formLayout.setMaxWidth("900px");

        return formLayout;
    }

    private Kundenprojekt createKundenprojekt() {
        log.info("Das ist die Rückgabe in create:"+ UI.getCurrent().getSession().getAttribute(CURRENT_USER));
        UUID userId = (UUID) UI.getCurrent().getSession().getAttribute(CURRENT_USER);

        Manager m = null;

        // Problem: Service Klasse ist immer null!!
        if (this.managerService != null) {
            m = this.managerService.getByUserId(userId);
        }
        if(m==null){
            log.info("Manager is Null...");
        }else {
            log.info("Manager with ID " +m.getId().toString());
        }
        return Kundenprojekt.builder().manager(m)
                .projektbeschreibung(this.projektbeschreibung.getValue())
                .projektname(this.projektname.getValue())
                .skills(this.skills.getValue())
                .publicProjekt(this.oeff.getValue().equals("veroeffentlichen")).build();
    }

    private void saveKundenprojekt(Kundenprojekt kundenprojekt) {
        this.kundenprojektServices.doCreateKundenprojekt(kundenprojekt);
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

    private void clearForm() {
        kundenprojektDTOBinder.setBean(new KundenprojektDTO());
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

    private void setMaxCharForFields() {
        int charLimitProjektName = 40;
        projektname.setMaxLength(charLimitProjektName);
        projektname.setHelperText("0/" + charLimitProjektName);
        projektname.setValueChangeMode(ValueChangeMode.EAGER);
        projektname.addValueChangeListener(e ->
                e.getSource().setHelperText(e.getValue().length() + "/" + charLimitProjektName)
        );

        int charLimit = 1000;
        skills.setMaxLength(255);
        skills.setHelperText("0/" + 255);
        skills.setValueChangeMode(ValueChangeMode.EAGER);
        skills.addValueChangeListener(e ->
                e.getSource().setHelperText(e.getValue().length() + "/" + 255)
        );

        projektbeschreibung.setMaxLength(charLimit);
        projektbeschreibung.setHelperText("0/" + charLimit);
        projektbeschreibung.setValueChangeMode(ValueChangeMode.EAGER);
        projektbeschreibung.addValueChangeListener(e ->
                e.getSource().setHelperText(e.getValue().length() + "/" + charLimit)
        );
    }
}