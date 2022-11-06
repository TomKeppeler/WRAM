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
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.kundenprojekt.KundenprojektService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.kundenprojekt.KundenprojektDTO;
import org.hbrs.project.wram.model.manager.ManagerRepository;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@CssImport("./styles/views/main/main-view.css")
@Route(value = Constants.Pages.PROJECTS_OVERVIEW, layout = AppView.class)

public class ProjectsOverview extends Div {

    private H1 header;

    private List<KundenprojektDTO> kundenprojektDTOS;

    @Autowired
    KundenprojektService kundenprojektService;


    @Autowired
    ManagerService managerService = new ManagerService();

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    UserService userService;
    public ProjectsOverview() {
        header = new H1("Alle Projekte werden bald hir gezeigt");
        /*UUID uuidUser =(UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER);
        UUID  uuidM = managerRepository.findByUserId(uuidUser).getId();
        kundenprojektDTOS =  kundenprojektService.findAllKundenprojektByManagerId(uuidM);*/
        add(header);//,setUpGrid()
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

}
