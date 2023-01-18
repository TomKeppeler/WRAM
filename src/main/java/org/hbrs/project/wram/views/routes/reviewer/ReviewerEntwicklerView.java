/**
 * @outhor Fabio
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.views.routes.reviewer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.hbrs.project.wram.control.entwickler.EntwicklerService;
import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.util.Utils;
import org.hbrs.project.wram.views.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese View dient dazu einem als Reviwer eingeloggtem User alle Entwickler anzuzeigen.
 * Dabei wird die View innerhalb der AppView angezeigt.
 */
@CssImport("./styles/views/main/main-view.css")
@Route(value = Constants.Pages.REVIEWERENTWICKLER, layout = AppView.class)
@PageTitle("Entwickler")
public class ReviewerEntwicklerView extends Div {


    /**
     * Hilfmethode für das erstellen des ComponentRenderer
     */
    private static final SerializableBiConsumer<Span, Entwickler> statusComponentUpdater = (
            span, entwickler) -> {
        boolean isAvailable = (entwickler.getKundenprojekt() == null);
        String theme = String.format("badge %s", isAvailable ? "success" : "error");
        span.getElement().setAttribute("theme", theme);

        if (isAvailable) {
            span.setText("Verfügbar");
        } else {
            span.setText("Nicht verfügbar");
        }
    };
    private H2 header;
    private List<Entwickler> entwicklers = new ArrayList<>();
    @Autowired
    private EntwicklerService entwicklerService;

    /**
     * Die Methode erzeugt ein ComponentRenderer, um in der Grid
     * alle Attribute des Entwicklers anzeigen zu können.
     *
     * @return
     */
    private static ComponentRenderer<EntwicklerDetailsFormLayout, Entwickler> createEntwicklerDetailsRenderer() {
        return new ComponentRenderer<>(
                EntwicklerDetailsFormLayout::new,
                EntwicklerDetailsFormLayout::setEntwickler);
    }

    /**
     * Diese Methode dient dazu einen StatusComponentRenderer zur Anzeige des Status in der Grid zu erstellen
     *
     * @return ComponentRenderer
     */
    public static ComponentRenderer<Span, Entwickler> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }

    @PostConstruct
    public void init() {
        header = new H2("Alle Entwickler.");
        entwicklers = entwicklerService.findAllEntwickler();
        VerticalLayout layout = new VerticalLayout();
        layout.add(header, setUpGrid());
        add(layout);
    }

    /**
     * Diese Methode dient dazu, eine Tabelle mit allen Entwicklern anzuzeigen.
     * Dabei werden Vorname, Name, Skills und die Verfügbarkeit des Entwicklers angezeigt.
     *
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

        // verfügbar
        //Grid.Column<Entwickler> statusColumn = grid.addColumn(Entwickler::getKundenprojekt).setHeader("Verfügbarkeit").setWidth("225px");
        grid.addColumn(createStatusComponentRenderer()).setHeader("Status").setAutoWidth(true);

        grid.setItemDetailsRenderer(createEntwicklerDetailsRenderer());
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeight("1000px");

        return grid;
    }

    /**
     * Klasse zur darstellung des Entwicklerprofils in der Grid.
     * Hier werden alle Daten des Entwicklers angegeben.
     */
    protected static class EntwicklerDetailsFormLayout extends FormLayout {
        private final TextField vorname = new TextField("Vorname");
        private final TextField nachname = new TextField("Nachname");
        private final TextField email = new TextField("E-Mail");
        private final TextField telefonnummer = new TextField("Telefonnummer");
        private final TextArea skills = new TextArea("Skills");
        private Image image = new Image("images/defaultP.png", "Profilbild");
        private final Span placeholder = new Span();

        public EntwicklerDetailsFormLayout() {
            vorname.setReadOnly(true);
            nachname.setReadOnly(true);
            email.setReadOnly(true);
            telefonnummer.setReadOnly(true);
            skills.setReadOnly(true);

            setResponsiveSteps(new ResponsiveStep("0", 4));
            image.setMaxWidth("110px");
            image.setMinWidth("110px");
            image.setHeight("100px");
            //um einen Zeilenumbruch zwischen Bild und textfeldern zu erzeugen.
            setColspan(placeholder, 4);
            setColspan(vorname, 2);
            setColspan(nachname, 2);
            setColspan(email, 2);
            setColspan(telefonnummer, 2);
            setColspan(skills, 4);

        }

        public void setEntwickler(Entwickler entwickler) {
            if (entwickler.getImage() != null) {
                this.image = Utils.generateImage(entwickler.getImage());
                this.image.setMaxWidth("110px");
                image.setMinWidth("110px");
                image.setHeight("100px");
            }

            if (entwickler.getFirstname() != null) {
                vorname.setValue(entwickler.getFirstname());
            } else {
                vorname.setValue("-");
            }
            if (entwickler.getName() != null) {
                nachname.setValue(entwickler.getName());
            } else {
                nachname.setValue("-");
            }
            if (entwickler.getUser().getEmail() != null) {
                email.setValue(entwickler.getUser().getEmail());
            } else {
                email.setValue("-");
            }
            if (entwickler.getPhone() != null) {
                telefonnummer.setValue(entwickler.getPhone());
            } else {
                telefonnummer.setValue("-");
            }
            if (entwickler.getSkills() != null) {
                skills.setValue(entwickler.getSkills());
            } else {
                skills.setValue("-");
            }

            add(image, placeholder, vorname, nachname, email, telefonnummer, skills);
        }

    }


}