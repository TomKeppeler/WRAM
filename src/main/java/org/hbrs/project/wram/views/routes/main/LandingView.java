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
        String welcomText = " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent a dui metus. Ut fringilla eros ligula, at pellentesque tellus elementum in. Donec lobortis ullamcorper lacinia. Curabitur eget lacus ante. \n             Nullam egestas tempus erat, a fringilla nisl ultrices eleifend. Quisque blandit dolor eget enim elementum, at auctor libero bibendum. Etiam eu turpis eu sem euismod ultrices. Vestibulum ante ipsum primis in \n             faucibus orci luctus et ultrices posuere cubilia curae; Pellentesque urna nulla, elementum at tempor ac, ultrices vitae urna.\n             \n             In porttitor nisl nec tincidunt fringilla. Nulla facilisi. Nullam pretium velit rhoncus lectus porttitor ornare nec id augue. Maecenas non vulputate risus, non malesuada leo. Pellentesque habitant morbi tristique \n             senectus et netus et malesuada fames ac turpis egestas. Integer cursus aliquam nisi quis lacinia. Praesent gravida purus dolor, eget dapibus felis laoreet ac. Duis ut suscipit elit. Vestibulum auctor lacinia lectus in sollicitudin. \n             Integer enim orci, luctus non tristique sed, dapibus tempus tortor. Proin id est rhoncus nunc pellentesque dapibus sed vitae justo. In porta ac nunc eget consequat. Morbi egestas nisi at malesuada sagittis. Morbi iaculis felis erat, \n             in convallis dui posuere a.\n             \n             Praesent molestie diam sed metus elementum, lobortis lacinia augue dignissim. Cras feugiat cursus felis, nec dignissim libero pharetra sit amet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; \n             Maecenas ullamcorper ultrices lacinia. Proin felis eros, consectetur sed accumsan et, fermentum posuere dui. Donec lorem nibh, euismod vitae tempus vitae, porta quis sem. Morbi at sodales lacus, quis euismod justo. Morbi mi quam, pharetra \n             at vehicula non, maximus sed quam. Fusce cursus lorem nulla, a faucibus diam aliquam non. Nulla eu luctus nibh. Curabitur imperdiet velit dapibus, consequat arcu nec, scelerisque orci.\n             \n             Proin hendrerit, urna ut finibus finibus, felis metus laoreet dolor, vitae tempor ante orci vitae magna. Praesent tincidunt diam non commodo mollis.\n";
        textField.setText(welcomText);
        container.add(textField);
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
