/**
 * @outhor Fabio
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.views.routes.manager;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.kundenprojekt.KundenprojektService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.kundenprojekt.KundenprojektDTO;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hbrs.project.wram.util.Constants.CURRENT_USER;

/**
 * Diese View dient dazu einem als Manager eingeloggtem User die Möglichkeit zu geben, Kundenprojekte upzudaten.
 * Die View wird aufgerufen, nachdem der Manager in seiner Projektübersicht bei einem Projekt auf bearbeiten klickt.
 * Dabei wird die View innerhalb der AppView angezeigt.
 */
@PageTitle("Projekt Details")
@Route(value = Constants.Pages.PROJECT_DETAIL, layout = AppView.class)
@Slf4j
public class ProjectDetailView extends Div {

    private final Binder<KundenprojektDTO> kundenprojektDTOBinder = new Binder<>(KundenprojektDTO.class);
    //Kundenprojekt, mit welchem die Seite aufgerufen wurde
    Kundenprojekt aktuellesProjekt = (Kundenprojekt) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_PROJECT);
    RadioButtonGroup<String> oeff;
    private H2 header;
    private RouterLink zurueckLink;
    private TextField projektname;
    private TextArea skills;
    private TextArea projektbeschreibung;
    private Button bestätigungsknopf;

    @Autowired
    private KundenprojektService kundenprojektServices;

    @Autowired
    private ManagerService managerService;

    @PostConstruct
    public void init() {
        add(createFormLayout());
        validateFields();
        setMaxCharForFields();

        bestätigungsknopf.addClickListener(e -> {
            if (kundenprojektDTOBinder.validate().isOk()) {
                saveKundenprojekt(createKundenprojekt());
                navigateToAppView();
            } else {
                Notification.show("Bitte überprüfe deine Eingaben.");
            }
        });
    }

    /**
     * Die Methode erzeugt ein VerticalLayout mit den Feldern Projektname, Skills und
     * Projektbeschreibung. Es werden die Werte des aktuellen Projektes eingesetzt.
     * Ein Bestätigungsbutton wird erzeugt.
     *
     * @return VerticalLayout
     */
    private VerticalLayout createFormLayout() {
        VerticalLayout layout = new VerticalLayout();

        header = new H2("Kundenprojekt Infos.");
        zurueckLink = new RouterLink("Zurück zu meiner Projektübersicht", ProjectsOverview.class);
        projektname = new TextField("Projektname");
        projektname.setWidth("550px");
        skills = new TextArea("Skills");
        skills.setWidthFull();
        skills.setPlaceholder("Erforderliche Skills");
        projektbeschreibung = new TextArea("Projektbeschreibung");
        projektbeschreibung.setWidthFull();
        projektbeschreibung.setPlaceholder("Projektbeschreibung");
        projektname.setValue(aktuellesProjekt.getProjektname());
        skills.setValue(aktuellesProjekt.getSkills());
        projektbeschreibung.setValue(aktuellesProjekt.getProjektbeschreibung());

        oeff = new RadioButtonGroup<>();
        oeff.setLabel("Status");
        oeff.setItems("öffentlich", "nicht öffentlich");
        if (aktuellesProjekt.isPublicProjekt()) {
            oeff.setValue("öffentlich");
        } else {
            oeff.setValue("nicht öffentlich");
        }
        oeff.setEnabled(true);

        bestätigungsknopf = new Button("Projekt aktualisieren");
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        setRequiredIndicatorVisible(projektname, skills, projektbeschreibung);
        layout.add(header, projektname, skills, projektbeschreibung, oeff, bestätigungsknopf, zurueckLink);
        layout.setMaxWidth("900px");
        return layout;
    }

    /**
     * Die Methode dient dazu, nach dem klicken auf den Button zum updaten von Kundenprojekten
     * zurück auf die View mit allen Projekten zu gelangen.
     */
    private void navigateToAppView() {
        UI.getCurrent().navigate(Constants.Pages.PROJECTS_OVERVIEW);
        notifyAfterUpdate();
        //Notification.show("Projekt wurde erfolgreich geupdated.", 3000, Notification.Position.MIDDLE);
    }

    /**
     * Die Methode nimmt ein kundenprojekt entegegen und speichert dieses mit Hilfe
     * eines KundenprojektServices über das Repository in der Datenbank ab.
     * Die Methode wird aufgerufen, wenn der Button geklickt wird.
     *
     * @param kundenprojekt
     */
    private void saveKundenprojekt(Kundenprojekt kundenprojekt) {
        this.kundenprojektServices.doCreateKundenprojekt(kundenprojekt);
    }

    /**
     * Die Methode dient dazu eine Entität der Klasse Kundenprojekt zu erstellen.
     * Die Werte werden aus den Eingabefeldern der View entnommen und mithilfe eines
     * Builders wird ein neues Kundenprojekt erstellt.
     *
     * @return kundenprojekt, welches anschließend gespeichert werden soll
     */
    private Kundenprojekt createKundenprojekt() {
        UUID userId = (UUID) UI.getCurrent().getSession().getAttribute(CURRENT_USER);

        Manager m = null;

        // Problem: Service Klasse ist immer null!!
        if (this.managerService != null) {
            m = this.managerService.getByUserId(userId);
        }

        Kundenprojekt k = Kundenprojekt.builder().manager(m)
                .projektbeschreibung(this.projektbeschreibung.getValue())
                .projektname(this.projektname.getValue())
                .skills(this.skills.getValue())
                .publicProjekt(this.oeff.getValue().equals("öffentlich")).build();
        //alte ID, damit Datensatz in der DB überschreiben wird
        k.setId(aktuellesProjekt.getId());
        return k;
    }

    /**
     * Die Methode dient dazu, ein Limit an Zeichen für die Eingabefelder zu setzen.
     * Zudem wird eine Statusanzeige als Helpertext unterhalb der Eingabefelder angezeigt.
     */
    private void setMaxCharForFields() {
        int charLimitProjektName = 70;
        projektname.setMaxLength(charLimitProjektName);
        projektname.setHelperText(projektname.getValue().length() + "/" + charLimitProjektName);
        projektname.setValueChangeMode(ValueChangeMode.EAGER);
        projektname.addValueChangeListener(e ->
                e.getSource().setHelperText(e.getValue().length() + "/" + charLimitProjektName)
        );

        int charLimit = 1000;
        skills.setMaxLength(255);
        skills.setHelperText(skills.getValue().length() + "/" + 255);
        skills.setValueChangeMode(ValueChangeMode.EAGER);
        skills.addValueChangeListener(e ->
                e.getSource().setHelperText(e.getValue().length() + "/" + 255)
        );

        projektbeschreibung.setMaxLength(charLimit);
        projektbeschreibung.setHelperText(projektbeschreibung.getValue().length() + "/" + charLimit);
        projektbeschreibung.setValueChangeMode(ValueChangeMode.EAGER);
        projektbeschreibung.addValueChangeListener(e ->
                e.getSource().setHelperText(e.getValue().length() + "/" + charLimit)
        );
    }

    /**
     * Die Methode dient dazu, die Textfelder beim updaten von Kundenprojekten zu validieren.
     * Dazu wird ein Binder verwendet, welcher jedes Eingabefeld prüft.
     */
    private void validateFields() {
        kundenprojektDTOBinder.forField(projektname)
                .withValidator(binderProjektname -> !binderProjektname.isEmpty(), "Bitte Projektnamen angeben").asRequired()
                .bind(KundenprojektDTO::getProjektname, KundenprojektDTO::setProjektname);
        kundenprojektDTOBinder.forField(skills)
                .withValidator(binderSkills -> !binderSkills.isEmpty(), "Bitte Skills angeben").asRequired()
                .bind(KundenprojektDTO::getSkills, KundenprojektDTO::setSkills);
        kundenprojektDTOBinder.forField(projektbeschreibung)
                .withValidator(binderProjektbeschreibung -> !binderProjektbeschreibung.isEmpty(), "Bitte Projektbeschreibung angeben").asRequired()
                .bind(KundenprojektDTO::getProjektbeschreibung, KundenprojektDTO::setProjektbeschreibung);
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

    /**
     * benachrichtigung bei einem update von Projekt
     */
    private void notifyAfterUpdate() {
        Dialog confirm = new Dialog();
        confirm.setId("confirm-profile-update");
        confirm.open();

        VerticalLayout dialoglayout = new VerticalLayout(
                new Text("Dein Projekt wurde erfolgreich aktualisiert."),
                new Button("Ok", e ->
                        confirm.close()
                )
        );
        dialoglayout.setId("confirm-dialog-layout");

        confirm.add(
                dialoglayout
        );
        confirm.setCloseOnEsc(true);
        confirm.setCloseOnOutsideClick(false);
    }
}
