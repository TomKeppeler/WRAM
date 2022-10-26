package org.hbrs.project.wram.views;


import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;
import org.hbrs.project.wram.util.Constants;

@CssImport("./styles/views/main/main-view.css")
@Route(Constants.Pages.PROJECTS_OVERVIEW)
public class ProjectsOverview extends Div {

    private H1 header;

    public ProjectsOverview() {
        header = new H1("Alle Projekte");
        add(header);
    }
}
