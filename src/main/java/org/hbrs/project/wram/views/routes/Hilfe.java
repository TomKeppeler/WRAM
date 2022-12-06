/**
 * @outhor  Salah
 * @vision 1.0
 * @Zuletzt bearbeiret: 06.12.22 by Salah
 *
 */
package org.hbrs.project.wram.views.routes;


import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.views.common.layouts.AppViewOutside;

import javax.annotation.PostConstruct;

@PageTitle("Hilfe")
@CssImport("./styles/views/main/main-view.css")
@Route(value = "Hilfe", layout = AppViewOutside.class)
@Slf4j
public class Hilfe extends Div {

    private H2 title;

    @PostConstruct
    private void init() {
        add(createFormLayout());


    }

    /**
     * Diese Methode erzeugt das Formlayout Hife funktion darstellt.
     *
     * @return Instanz des Layouts
     */
    public VerticalLayout createFormLayout() {
        VerticalLayout formLayout = new VerticalLayout();


        title = new H2("Kein Hilfe vorhanden verpiss dich :$");

        formLayout.add(title);
        formLayout.setMaxWidth("900px");
        return formLayout;
    }
}
