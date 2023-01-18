/**
 * @outhor Fabio
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.views.routes.reviewer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.project.wram.control.kundenprojekt.KundenprojektService;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese View dient dazu einem als Reviwer eingeloggtem User alle öffentlichen Kundenprojekte anzuzeigen.
 * Dabei wird die View innerhalb der AppView angezeigt.
 */
@CssImport("./styles/views/main/main-view.css")
@Route(value = Constants.Pages.REVIEWERPROJEKTE, layout = AppView.class)
@PageTitle("Kundenprojekte")
public class ReviewerProjektView extends Div {


    private H2 header;

    private List<Kundenprojekt> kundenprojektDTOS = new ArrayList<>();

    @Autowired
    private KundenprojektService kundenprojektService;

    private static ComponentRenderer<ReviewerProjektView.ProjektDetailsFormLayout, Kundenprojekt> createProjektDetailsRenderer() {
        return new ComponentRenderer<>(
                ReviewerProjektView.ProjektDetailsFormLayout::new,
                ReviewerProjektView.ProjektDetailsFormLayout::setProjekt);
    }

    @PostConstruct
    public void init() {
        header = new H2("Alle Kundenprojekte.");
        kundenprojektDTOS = kundenprojektService.findAllPublicKundenprojekt();
        VerticalLayout layout = new VerticalLayout();
        layout.add(header, setUpGrid());
        add(layout);
    }

    /**
     * Diese Methode dient dazu, eine Tabelle mit allen öffentlichen Kundenprojekten anzuzeigen.
     * Dabei werden Name und Skills eines Kundenprojektes angezeigt.
     * @return Component Grid
     */
    private Component setUpGrid() {
        Grid<Kundenprojekt> grid = new Grid<>();

        // Befüllen der Tabelle mit den zuvor ausgelesenen Projekt
        ListDataProvider<Kundenprojekt> dataProvider = new ListDataProvider<>(kundenprojektDTOS);
        grid.setDataProvider(dataProvider);

        // Projekt name
        Grid.Column<Kundenprojekt> projektnameColumn = grid.addColumn(Kundenprojekt::getProjektname).setHeader("Projektname").setWidth("100px");

        // Skills
        Grid.Column<Kundenprojekt> skillsColumn = grid.addColumn(Kundenprojekt::getSkills).setHeader("Skills").setWidth("225px");

        // Entwickler zuweisen
        Grid.Column<Kundenprojekt> entwicklerAnfragenColumn = grid.addComponentColumn(kundenprojekt -> {
            Button bearbeiten = new Button(VaadinIcon.PLUS_CIRCLE.create());
            bearbeiten.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            bearbeiten.addClickListener(e ->
                    entwicklerAnfragen(kundenprojekt)
            );
            return bearbeiten;
        });
        entwicklerAnfragenColumn.setHeader("Entwickler zuweisen");


        // Projektdaten ausklappen
        grid.setItemDetailsRenderer(createProjektDetailsRenderer());
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeight("1000px");

        return grid;
    }

    private void entwicklerAnfragen(Kundenprojekt kundenprojekt) {
        UI.getCurrent().getSession().setAttribute(Constants.CURRENT_PROJECT, kundenprojekt);
        UI.getCurrent().navigate(Constants.Pages.REVIEWERENTWICKLERZUWEISEN);
    }

    private static class ProjektDetailsFormLayout extends FormLayout {
        private final TextField projektname = new TextField("Projektname");
        private final TextArea projektbeschreibung = new TextArea("Projektbeschreibung");
        private final TextArea skills = new TextArea("Skills");

        public ProjektDetailsFormLayout() {
            projektname.setReadOnly(true);
            projektbeschreibung.setReadOnly(true);
            skills.setReadOnly(true);

            setResponsiveSteps(new ResponsiveStep("0", 4));
            setColspan(projektname, 2);
            setColspan(projektbeschreibung, 4);
            setColspan(skills, 4);
            add(projektname, projektbeschreibung, skills);
        }

        public void setProjekt(Kundenprojekt kundenprojekt) {
            if (kundenprojekt.getProjektname() != null) {
                projektname.setValue(kundenprojekt.getProjektname());
            } else {
                projektname.setValue("-");
            }
            if (kundenprojekt.getProjektbeschreibung() != null) {
                projektbeschreibung.setValue(kundenprojekt.getProjektbeschreibung());
            } else {
                projektbeschreibung.setValue("-");
            }
            if (kundenprojekt.getSkills() != null) {
                skills.setValue(kundenprojekt.getSkills());
            } else {
                skills.setValue("-");
            }
        }

    }

}