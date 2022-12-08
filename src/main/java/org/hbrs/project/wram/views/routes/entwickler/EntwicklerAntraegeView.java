/**
 * @outhor  Salah
 * @vision 1.0
 * @Zuletzt bearbeiret: 06.12.22 by Salah
 *
 */
package org.hbrs.project.wram.views.routes.entwickler;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppView;

import javax.annotation.PostConstruct;

/**
 * Diese View dient dazu einem als Entwickler zugewissene Anträge anzuschauen
 * Dabei wird die View innerhalb der AppView angezeigt.
 */

@PageTitle("EntwicklerAntraege")
@CssImport("./styles/views/main/main-view.css")
@Route(value = Constants.Pages.ENTWICKLERANTRAEGEVIEW, layout = AppView.class)
@Slf4j
public class EntwicklerAntraegeView extends Div {
    private H1 title;

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


        title = new H1("EntwicklerAntraege");

        formLayout.add(title);
        formLayout.setMaxWidth("900px");
        return formLayout;
    }

}
