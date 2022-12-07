/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 *
 */
package org.hbrs.project.wram.views.routes.main;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import org.hbrs.project.wram.views.common.layouts.AppView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.hbrs.project.wram.util.Constants;

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

        container.add(new Header(new H1("Herzlich Willkommen bei WAC!")));
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
        if(userId == null) {
            UI.getCurrent().navigate(Constants.Pages.LOGIN_VIEW);
        }
    }

}
