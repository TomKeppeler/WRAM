package org.hbrs.project.wram.views.routes.manager;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.hbrs.project.wram.control.kundenprojekt.KundenprojektService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.manager.ManagerRepository;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@CssImport("./styles/views/main/main-view.css")
@PageTitle("Meine Projekte")
@Route(value = Constants.Pages.PROJECTS_OVERVIEW, layout = AppView.class)

/**
 * Diese View dient dazu einem als Manager eingeloggtem User alle seine Kundenprojekte anzuzeigen.
 * Dabei wird die View innerhalb der AppView angezeigt.
 */
public class ProjectsOverview extends Div {

    private H2 header;

    private List<Kundenprojekt> kundenprojektDTOS = new ArrayList<>();

    @Autowired
    KundenprojektService kundenprojektService;

    @Autowired
    ManagerService managerService;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {
        header = new H2("Deine Projekte auf einen Blick.");
        UUID uuidUser = (UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER);
        UUID uuidM = managerRepository.findByUserId(uuidUser).getId();
        kundenprojektDTOS =  kundenprojektService.findAllKundenprojektByManagerId(uuidM);
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
        grid.addComponentColumn(kundenprojekt -> createStatusIcon(kundenprojekt.isPublicProjekt())).setHeader("Projekt öffentlich");
        // Projekt bearbeiten
        Grid.Column<Kundenprojekt> editColumn = grid.addComponentColumn(kundenprojekt -> {
           Button bearbeiten = new Button( VaadinIcon.PENCIL.create());
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

    /**
     * Diese Methode dient dazu in der Grid IconSymbole anzuzeigen.
     * @param status
     * @return
     */
    private Icon createStatusIcon(boolean status) {
        boolean isAvailable = status;
        Icon icon;
        if (isAvailable) {
            icon = VaadinIcon.CHECK.create();
            icon.getElement().getThemeList().add("badge success");
        } else {
            icon = VaadinIcon.CLOSE_SMALL.create();
            icon.getElement().getThemeList().add("badge error");
        }
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }
}
