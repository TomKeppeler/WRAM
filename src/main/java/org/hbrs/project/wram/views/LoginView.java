package org.hbrs.project.wram.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import org.hbrs.project.wram.control.LoginControl;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.model.user.UserRepository;
import org.hbrs.project.wram.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hbrs.project.wram.util.Constants.CURRENT_USER;

/**
 * View zur Darstellung der Startseite. Diese zeigt dem Benutzer ein Login-Formular an.
 * ToDo: Integration einer Seite zur Registrierung von Benutzern
 */
@Route(value = Constants.Pages.MAIN_VIEW )
@RouteAlias(value = "login")
public class LoginView extends VerticalLayout {

    @Autowired
    private LoginControl loginControl;

    @Autowired
    private UserRepository repository;
    
    public LoginView() {
      setSizeFull();
       VerticalLayout layout = new VerticalLayout();
       LoginForm login = new LoginForm();

       RouterLink register = new RouterLink("Jetzt registrieren", RegistrationForm.class);
       HorizontalLayout registerlink = new HorizontalLayout( register);

       login.addLoginListener(e -> {
          boolean isAuthenticated = false;

          try {
              isAuthenticated = loginControl.authenticateUser(e.getUsername(), e.getPassword());
          } catch (Exception exception) {
            Dialog dialog = new Dialog();
            dialog.add( new Text(exception.getMessage()));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            Button closeButton = new Button("Schließen");
            closeButton.addClickListener(event -> dialog.close());
            dialog.open();
          }
          if (isAuthenticated) {
              grabAndSetUserIntoSession();
              navigateToMainPage();
          } else {
              login.setError(true);
              // ToDo: Login-Fehler verdeutlichen
          }
       });
        layout.add(login, registerlink);
        layout.setAlignItems( FlexComponent.Alignment.CENTER );

        add(layout);
       this.setAlignItems( Alignment.CENTER );

    }

    private void grabAndSetUserIntoSession() {
        final UserDTO user = loginControl.getCurrentUser();
        UI.getCurrent().getSession().setAttribute(CURRENT_USER, user);
    }

    private void navigateToMainPage() {
        // ToDo: Navigation zur individuelle Landing Page (je nach Rolle)
        UI.getCurrent().navigate(Constants.Pages.LANDING_PAGE);
    }
}
