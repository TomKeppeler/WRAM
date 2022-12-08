/**
 * @outhor  Salah
 * @vision 1.0
 * @Zuletzt bearbeiret: 06.12.22 by Salah
 *
 */
package org.hbrs.project.wram.views.routes.entwickler;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.anfrage.AnfrageService;
import org.hbrs.project.wram.control.entwickler.EntwicklerService;
import org.hbrs.project.wram.control.kundenprojekt.KundenprojektService;
import org.hbrs.project.wram.model.anfrage.Anfrage;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Diese View dient dazu einem als Entwickler zugewissene Anträge anzuschauen
 * Dabei wird die View innerhalb der AppView angezeigt.
 */

@PageTitle("EntwicklerAnfrage")
@CssImport("./styles/views/main/main-view.css")
@Route(value = Constants.Pages.ENTWICKLERANFRAGEVIEW, layout = AppView.class)
@Slf4j
public class EntwicklerAnfragView extends Div {
    private H2 header;

    private List<Anfrage> anfrage = new ArrayList<>();

    @Autowired
    private AnfrageService anfrageService;

    @Autowired
    private EntwicklerService entwicklerService;

    @PostConstruct
    public void init() {
        header = new H2("Neue Antfragen");
        UUID userID =(UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER);

        //TODo nur entwickler zugewissene Anfragen Anzeigen
        //anfrage =  anfrageService.findAllAnfraegeojektByEntwickler();
        anfrage = anfrageService.findAllByEntwicklerId(entwicklerService.getByUserId(userID).getId());
        VerticalLayout layout = new VerticalLayout();
        layout.add(header);
        add(layout, setUpGrid());
    }

    /**
     * Diese Methode dient dazu, eine Tabelle mit allen Anfragen ein Entwickler.
     * Dabei werden Name und Skills eines Kundenprojektes angezeigt.
     * @return Component Grid
     */
    private Component setUpGrid() {
        Grid<Anfrage> grid = new Grid<>();

        // Befüllen der Tabelle mit den zuvor ausgelesenen Anfrage
        ListDataProvider<Anfrage> dataProvider = new ListDataProvider<>(anfrage);
        grid.setDataProvider(dataProvider);

        // Projekt name
        Grid.Column<Anfrage> KundenprojektColumn = grid.addColumn(Anfrage::getKundenprojekt).setHeader("Kundenprojekt").setWidth("225px");


        Grid.Column<Anfrage> ReviewerColumn = grid.addColumn(Anfrage::getReviewer).setHeader("Zuweisender Reviewer").setWidth("100px");

        //Reason
        Grid.Column<Anfrage> ReasonColumn = grid.addColumn(Anfrage::getReason).setHeader("Reason").setWidth("225px");

        // Projektdaten ausklappen
        //grid.setItemDetailsRenderer(createProjektDetailsRenderer());
        //grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeight("1000px");

        return grid;
    }


    private static ComponentRenderer<EntwicklerAnfragView.ProjektDetailsFormLayout, Kundenprojekt> createProjektDetailsRenderer() {
        return new ComponentRenderer<>(
                EntwicklerAnfragView.ProjektDetailsFormLayout::new,
                EntwicklerAnfragView.ProjektDetailsFormLayout::setProjekt);
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
            if(kundenprojekt.getProjektname()!=null){projektname.setValue(kundenprojekt.getProjektname());}else{projektname.setValue("-");}
            if(kundenprojekt.getProjektbeschreibung()!=null){projektbeschreibung.setValue(kundenprojekt.getProjektbeschreibung());}else{projektbeschreibung.setValue("-");}
            if(kundenprojekt.getSkills()!=null){skills.setValue(kundenprojekt.getSkills());}else{skills.setValue("-");}
        }

    }


}
