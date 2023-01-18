/**
 * @outhor Fabio
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.views.routes.reviewer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.apache.commons.lang3.StringUtils;
import org.hbrs.project.wram.control.anfrage.AnfrageService;
import org.hbrs.project.wram.control.entwickler.EntwicklerService;
import org.hbrs.project.wram.control.reviewer.ReviewerService;
import org.hbrs.project.wram.model.anfrage.Anfrage;
import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.reviewer.Reviewer;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppView;
import org.hbrs.project.wram.views.routes.Notify;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hbrs.project.wram.views.routes.reviewer.ReviewerEntwicklerView.createStatusComponentRenderer;

/**
 * Diese View dient dazu einem als Reviwer eingeloggtem User die Möglichkeit zu geben, Entwickler zu Kundenprojekten zuzuweisen.
 * Dabei wird die View innerhalb der AppView angezeigt.
 */
@CssImport("./styles/views/main/main-view.css")
@Route(value = Constants.Pages.REVIEWERENTWICKLERZUWEISEN, layout = AppView.class)
@PageTitle("Entwickler zuweisen")
public class ReviewerEntwicklerZuweisenView extends Div {

    //Kundenprojekt, mit welchem die Seite aufgerufen wurde
    Kundenprojekt aktuellesProjekt = (Kundenprojekt) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_PROJECT);
    @Autowired
    private ReviewerService reviewerService;
    @Autowired
    private EntwicklerService entwicklerService;
    @Autowired
    private AnfrageService anfrageService;
    private H2 header;
    private List<Entwickler> entwicklers = new ArrayList<>();

    /**
     * Die Methode erzeugt ein ComponentRenderer, um in der Grid
     * alle Attribute des Entwicklers anzeigen zu können.
     *
     * @return
     */
    private static ComponentRenderer<ReviewerEntwicklerView.EntwicklerDetailsFormLayout, Entwickler> createEntwicklerDetailsRenderer() {
        return new ComponentRenderer<>(
                ReviewerEntwicklerView.EntwicklerDetailsFormLayout::new,
                ReviewerEntwicklerView.EntwicklerDetailsFormLayout::setEntwickler);
    }

    @PostConstruct
    public void init() {
        header = new H2("Entwickler zu Kundenprojekten zuweisen.");
        entwicklers = entwicklerService.findAllEntwickler();
        VerticalLayout layout = new VerticalLayout();
        layout.add(header, setUpUI(), setUpGrid());
        add(layout);
    }

    private Component setUpUI() {
        VerticalLayout layout = new VerticalLayout();
        RouterLink appView = new RouterLink("Zurück zu meiner Projektübersicht", ReviewerProjektView.class);
        H4 projektName = new H4("Projekt: " + aktuellesProjekt.getProjektname());
        TextArea projektSkills = new TextArea("Skills");
        projektSkills.setReadOnly(true);
        projektSkills.setWidthFull();
        projektSkills.setMaxWidth("900px");
        projektSkills.setValue(aktuellesProjekt.getSkills());

        layout.add(appView, projektName, projektSkills);
        return layout;
    }

    /**
     * Diese Methode dient dazu, eine Tabelle mit allen öffentlichen Kundenprojekten anzuzeigen.
     * Dabei werden Name und Skills eines Kundenprojektes angezeigt.
     * @return Component Grid
     */
    private Component setUpGrid() {
        Grid<Entwickler> grid = new Grid<>();

        // Befüllen der Tabelle mit den zuvor ausgelesenen Projekt
        ListDataProvider<Entwickler> dataProvider = new ListDataProvider<>(entwicklers);
        grid.setDataProvider(dataProvider);

        // vorname
        Grid.Column<Entwickler> firstnameColumn = grid.addColumn(Entwickler::getFirstname).setHeader("Vorname").setWidth("100px");

        // nachname
        Grid.Column<Entwickler> nameColumn = grid.addColumn(Entwickler::getName).setHeader("Nachname").setWidth("225px");

        // skills
        Grid.Column<Entwickler> skillColumn = grid.addColumn(Entwickler::getSkills).setHeader("Skills").setWidth("225px");

        // status
        grid.addColumn(createStatusComponentRenderer()).setHeader("Status").setAutoWidth(true);

        // Anfrage senden
        Grid.Column<Entwickler> anfrageSendenColumn = grid.addComponentColumn(entwickler -> {
            Button ansehen = new Button("Anfrage senden");
            ansehen.addClickListener(e ->
                    sendeAnfrage(entwickler)
            );
            return ansehen;
        }).setAutoWidth(true).setFlexGrow(0);


        // skills filter
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField skillsField = new TextField();
        skillsField.setClearButtonVisible(true);
        skillsField.addValueChangeListener(e -> {
            if (skillsField.getValue().equals("")) {
                dataProvider.clearFilters();
            } else {
                dataProvider.addFilter(
                        entwi -> StringUtils.containsIgnoreCase(entwi.getSkills(), skillsField.getValue()));
            }
        });
        skillsField.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(skillColumn).setComponent(skillsField);
        skillsField.setSizeFull();
        skillsField.setPlaceholder("Filter");

        //Details
        grid.setItemDetailsRenderer(createEntwicklerDetailsRenderer());
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeight("1000px");

        return grid;
    }

    private void sendeAnfrage(Entwickler entwickler) {
        if (anfrageService.anfrageAlreadyExists(entwickler, aktuellesProjekt)) {
            Notify.notifyAfterUpdateWithOkay(entwickler.getFirstname() + " wurde bereits für " + aktuellesProjekt.getProjektname() + " angefragt.");

        } else {
            //Reviewer
            Reviewer aktuellerReviewer = reviewerService.getByUserId((UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER));
            Anfrage a = Anfrage.builder()
                    .entwicklerProfil(entwickler)
                    .kundenprojekt(aktuellesProjekt)
                    .reviewer(aktuellerReviewer)
                    .build();
            anfrageService.doCreatAnfrage(a);
            Notify.notifyAfterUpdateWithOkay("Anfrage für " + aktuellesProjekt.getProjektname() + " an " + entwickler.getFirstname() + " wurde gesendet.");
        }
    }


}