/**
 * @outhor Fabio & Sophia
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.views.routes.manager;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.project.wram.control.kundenprojekt.KundenprojektService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.manager.ManagerRepository;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CssImport("./styles/views/main/main-view.css")
@PageTitle("Meine Projekte")
@Route(value = Constants.Pages.PROJECTS_OVERVIEW, layout = AppView.class)

/**
 * Diese View dient dazu einem als Manager eingeloggtem User alle seine Kundenprojekte anzuzeigen.
 * Dabei wird die View innerhalb der AppView angezeigt.
 */
public class ProjectsOverview extends Div {

    /**
     * Hilfmethode für das erstellen des ComponentRenderer
     */
    private static final SerializableBiConsumer<Span, Kundenprojekt> statusComponentUpdater = (
            span, kundenprojekt) -> {
        boolean isAvailable = (kundenprojekt.isPublicProjekt());
        String theme = String.format("badge %s", isAvailable ? "success" : "error");
        span.getElement().setAttribute("theme", theme);

        if (isAvailable) {
            span.setText("Öffentlich");
        } else {
            span.setText("Nicht öffentlich");
        }
    };
    @Autowired
    KundenprojektService kundenprojektService;
    @Autowired
    ManagerService managerService;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    UserService userService;
    private H2 header;
    private List<Kundenprojekt> kundenprojektDTOS = new ArrayList<>();

    /**
     * Diese Methode dient dazu einen StatusComponentRenderer zur Anzeige des Status in der Grid zu erstellen
     * @return ComponentRenderer
     */
    private static ComponentRenderer<Span, Kundenprojekt> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }

    @PostConstruct
    public void init() {
        header = new H2("Deine Projekte auf einen Blick.");
        UUID uuidUser = (UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER);
        UUID uuidM = managerRepository.findByUserId(uuidUser).getId();
        kundenprojektDTOS = kundenprojektService.findAllKundenprojektByManagerId(uuidM);
        VerticalLayout layout = new VerticalLayout();
        layout.add(header, setUpGrid());
        add(layout);
    }

    /**
     * Diese Methode dient dazu, eine Tabelle mit allen Kundenprojekten vom zugehörigen Manager anzuzeigen.
     * @return Component Grid
     */
    private Component setUpGrid() {
        Grid<Kundenprojekt> grid = new Grid<>();

        // Befüllen der Tabelle mit den zuvor ausgelesenen Projekt
        ListDataProvider<Kundenprojekt> dataProvider = new ListDataProvider<>(kundenprojektDTOS);
        grid.setDataProvider(dataProvider);

        // Projekt name
        Grid.Column<Kundenprojekt> projektnameColumn = grid.addColumn(Kundenprojekt::getProjektname).setHeader("Projektname").setWidth("225px");
        // Projekt öffentlich
        //grid.addComponentColumn(kundenprojekt -> createStatusIcon(kundenprojekt.isPublicProjekt())).setHeader("Projekt öffentlich");
        grid.addColumn(createStatusComponentRenderer()).setHeader("Status").setAutoWidth(true);
        // Projekt bearbeiten
        Grid.Column<Kundenprojekt> editColumn = grid.addComponentColumn(kundenprojekt -> {
            Button bearbeiten = new Button(VaadinIcon.PENCIL.create());
            bearbeiten.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            bearbeiten.addClickListener(e ->
                    editJKundenprojekt(kundenprojekt)
            );
            return bearbeiten;
        });
//                .setWidth("225px").setFlexGrow(0);
        editColumn.setHeader("Kundenprojekt bearbeiten");
        return grid;
    }

    /**
     * Navigiert zur Seite, wo Kundenprojekt geändert werden kann.
     * @param kundenprojekt
     */
    private void editJKundenprojekt(Kundenprojekt kundenprojekt) {
        UI.getCurrent().getSession().setAttribute(Constants.CURRENT_PROJECT, kundenprojekt);
        UI.getCurrent().navigate(Constants.Pages.PROJECT_DETAIL);
    }

}
