/**
 * @outhor Fabio
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 *
 */
package org.hbrs.project.wram.views.routes.reviewer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.hbrs.project.wram.control.entwickler.EntwicklerService;
import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.util.Constants;
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
@Route(value = Constants.Pages.ENTWICKLERZUWEISEN, layout = AppView.class)
public class ReviewerEntwicklerView extends Div {


    private H2 header;

    private List<Entwickler> entwicklers = new ArrayList<>();

    @Autowired
    private EntwicklerService entwicklerService;

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
        skillsField.addValueChangeListener(e ->
                dataProvider.addFilter(
                        entwi -> StringUtils.containsIgnoreCase(entwi.getSkills(), skillsField.getValue()))
        );
        skillsField.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(skillColumn).setComponent(skillsField);
        skillsField.setSizeFull();
        skillsField.setPlaceholder("Filter");

        // verfügbar
        //Grid.Column<Entwickler> statusColumn = grid.addColumn(Entwickler::getKundenprojekt).setHeader("Verfügbarkeit").setWidth("225px");
        grid.addColumn(createStatusComponentRenderer()).setHeader("Status").setAutoWidth(true);

        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        return grid;
    }

    /**
     * Diese Methode dient dazu einen StatusComponentRenderer zur Anzeige des Status in der Grid zu erstellen
     * @return ComponentRenderer
     */
    private static ComponentRenderer<Span, Entwickler> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }

    /**
     * Hilfmethode für das erstellen des ComponentRenderer
     */
    private static final SerializableBiConsumer<Span, Entwickler> statusComponentUpdater = (
            span, entwickler) -> {
        boolean isAvailable = (entwickler.getKundenprojekt()==null);
        String theme = String.format("badge %s", isAvailable ? "success" : "error");
        span.getElement().setAttribute("theme", theme);

        if(isAvailable){
            span.setText("Verfügbar");
        }else{
            span.setText("Nicht verfügbar");
        }
    };


}