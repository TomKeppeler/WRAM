package org.hbrs.project.wram.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import com.vaadin.flow.router.RouterLink;
import org.hbrs.project.wram.control.LoginControl;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hbrs.project.wram.util.Constant.CURRENT_USER;

/**
 * View zur Darstellung der Startseite. Diese zeigt dem Benutzer ein Login-Formular an.
 * ToDo: Integration einer Seite zur Registrierung von Benutzern
 */
@Route(value = "" )
@RouteAlias(value = "login")
public class MainView extends VerticalLayout {

    @Autowired
    private LoginControl loginControl;

    @Autowired
    private UserRepository repository;
    
    public MainView() {
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
              User currentUser = repository.findUserByUsernameAndPassword(e.getUsername(), e.getPassword());
              UserDTO user = new UserDTO();
              user.setUsername(currentUser.getUsername());
              user.setPasswort(currentUser.getPassword());
              grabAndSetUserIntoSession(user);
              navigateToMainPage();
          } else {
              login.setError(true);
              // ToDo: Login-Fehler verdeutlichen
          }


       });



        layout.add(
                login,
                registerlink
        );

        layout.setAlignItems( FlexComponent.Alignment.CENTER );

        add(layout);
       this.setAlignItems( Alignment.CENTER );
//        RegistrationForm rf =new RegistrationForm();
//        add(rf);

    }

    private void grabAndSetUserIntoSession(UserDTO user) {
        UI.getCurrent().getSession().setAttribute(CURRENT_USER, user);
    }


    private void navigateToMainPage() {
        // Navigation zur Startseite, hier auf die Teil-Komponente Show-Cars.
        // Die anzuzeigende Teil-Komponente kann man noch individualisieren, je nach Rolle,
        // die ein Benutzer besitzt
        Dialog dialog = new Dialog();
        dialog.add("Sie sind erfolgreich eingeloggt! Hier sollte Ihre perönliche Landing Page erscheinen.");
        dialog.setWidth("400px");
        dialog.setHeight("150px");
        Button closeButton = new Button("Schließen");
        closeButton.addClickListener(event -> dialog.close());
        dialog.open();
    }
}
