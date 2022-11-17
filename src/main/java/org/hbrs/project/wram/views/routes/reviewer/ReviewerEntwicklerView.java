package org.hbrs.project.wram.views.routes.reviewer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import org.hbrs.project.wram.control.entwickler.profile.EntwicklerProfileService;
import org.hbrs.project.wram.control.entwickler.user.EntwicklerService;
import org.hbrs.project.wram.control.kundenprojekt.KundenprojektService;
import org.hbrs.project.wram.model.entwickler.profile.EntwicklerProfil;
import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
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

    @Autowired
    private EntwicklerProfileService entwicklerProfileService;

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
        //todo Skills über profil einbinden
        Grid.Column<Entwickler> skillColumn = grid.addColumn(Entwickler::getId).setHeader("Skills").setWidth("225px");

        // verfügbar
        //todo: Verfügbarkeitsstaus einfügen
        Grid.Column<Entwickler> statusColumn = grid.addColumn(Entwickler::getId).setHeader("Verfügbarkeit").setWidth("225px");
        return grid;
    }


}