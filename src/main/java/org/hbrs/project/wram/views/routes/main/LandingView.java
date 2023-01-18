/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeitet: 10.1.23 by Leon
 */
package org.hbrs.project.wram.views.routes.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppView;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * Diese View dient dazu eine Landingpage darzustellen, welche beim Einloggen angezeigt wird.
 * Dabei wird die View innerhalb der AppView angezeigt.
 */

@Route(value = Constants.Pages.MAIN_VIEW, layout = AppView.class)
@RouteAlias(value = "landing", layout = AppView.class)
@PageTitle("") // note to myself:Change back to ""
public class LandingView extends VerticalLayout implements BeforeEnterObserver {

    @PostConstruct
    private void init() {

        add(doCreateUI());
    }

    /**
     * Diese Methode dient dazu die UI in Form einer Komponente zu erstellen, welches anschließend der View
     * hinzugefügt wird.
     *
     * @return component
     */
    private Component doCreateUI() {
        VerticalLayout container = new VerticalLayout();
        Span textField = new Span();
        H2 title = new H2("WAC - Verwalten Sie Ihre Projekte innerhalb einer einzigen Plattform!");
        String welcomeText = "Willkommen bei der Zukunft des Projektmanagements! \n ";
        textField.setText(welcomeText);
        Label footer = new Label("Sie haben Fragen zu WAC? Kontaktieren Sie uns! \n Unter wac.wram@web.de!");
        footer.addClassName("contact-footer");
        container.add(title, textField, footer);
        container.setAlignItems(Alignment.CENTER);
        return container;
    }

    /**
     * Diese Methode dient dazu, zu überprüfen, ob ein User wirklich eingeloggt ist, und falls nicht, wird
     * auf die LoginView zurück navigiert.
     * Die Methode wird vor dem Anzeigen der View angezeigt.
     */
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        UUID userId = (UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER);
        if (userId == null) {
            UI.getCurrent().navigate(Constants.Pages.LOGIN_VIEW);
        }
    }

}
