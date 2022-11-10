package org.hbrs.project.wram.views.routes.manager;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.AllArgsConstructor;
import org.hbrs.project.wram.control.LoginControl;
import org.hbrs.project.wram.control.kundenprojekt.KundenprojektService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.kundenprojekt.KundenprojektDTO;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@PageTitle("Meine Projekte")
@CssImport("./styles/views/main/main-view.css")
@Route(value = Constants.Pages.PROJECTS_OVERVIEW, layout = AppView.class)
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ProjectsOverview extends Div implements BeforeEnterObserver {

    private final H1 header = new H1("Alle Projekte werden bald hir gezeigt");

    private List<KundenprojektDTO> kundenprojektDTOS = new ArrayList<>();

    private LoginControl control;

    private transient KundenprojektService kundenprojektService;

    private transient ManagerService managerService;

    private transient UserService userService;

    public ProjectsOverview() {
        UUID uuidUser =(UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER);
        UUID  uuidM = managerService.getByUserId(uuidUser).getId();
        kundenprojektService.findAllKundenprojektByManagerId(uuidM);
        add(header,setUpGrid());
    }



   private Component setUpGrid() {
        Grid<KundenprojektDTO> grid = new Grid<>();

        // Befüllen der Tabelle mit den zuvor ausgelesenen Projekt
        ListDataProvider<KundenprojektDTO> dataProvider = new ListDataProvider<>(kundenprojektDTOS);
        grid.setDataProvider(dataProvider);

        // Projekt name
        Grid.Column<KundenprojektDTO> ProjektnameColumn = grid.addColumn(KundenprojektDTO::getProjektname).setHeader("Projektname").setWidth("225px");
        return  grid;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (control.getCurrentUser() == null) {
            beforeEnterEvent.rerouteTo(Constants.Pages.MAIN_VIEW);
        }
    }
}
