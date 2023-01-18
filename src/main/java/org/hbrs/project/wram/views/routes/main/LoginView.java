/**
 * @outhor Lukas & Sophia
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.views.routes.main;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.LoginControl;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppViewOutside;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static org.hbrs.project.wram.util.Constants.CURRENT_USER;

/**
 * View zur Darstellung der Startseite. Diese zeigt dem Benutzer ein Login-Formular an.
 */
@PageTitle("Login")
@Route(value = Constants.Pages.LOGIN_VIEW)
@RouteAlias(value = Constants.Pages.LOGIN_VIEW, layout = AppViewOutside.class)
@Slf4j
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private LoginControl loginControl;

    @Autowired
    private UserService userService;

    private User user = null;

    /**
     * sets up the UI, namely the Login Form.
     * it also handles excpetions as loggin in with an unregistered username or wrong password.
     */
    @PostConstruct
    private void init() {
        setSizeFull();
        VerticalLayout layout = new VerticalLayout();
        LoginForm login = new LoginForm();

        //RouterLink register = new RouterLink("Jetzt registrieren", RegistrationForm.class);
        //HorizontalLayout registerlink = new HorizontalLayout( register);
        String username = "";
        login.addForgotPasswordListener(e -> {
            UI.getCurrent().navigate("passwort_erneuern"); // Login-View

        });
        login.addLoginListener(e -> {

            boolean isAuthenticated = false;


            try {

                isAuthenticated = (loginControl.authenticateUser(e.getUsername(), e.getPassword()));

            } catch (Exception exception) {
                // falls doch im DB vorhanden ist
                if (loginControl.isUsernameInUse(e.getUsername())) {
                    if (!userService.findUserByUsername(e.getUsername()).isVerified()) {
                        Dialog confirm = new Dialog();
                        confirm.setId("confirm-profile-update");
                        confirm.open();

                        VerticalLayout dialoglayout = new VerticalLayout(
                                new Text("Bitte verifiziere dich über den Link, den du als E-Mail erhalten hast."),
                                new Button("Ok", x -> {
                                    confirm.close();
                                    UI.getCurrent().getPage().reload();
                                }

                                )
                        );

                        confirm.add(
                                dialoglayout
                        );
                        confirm.setCloseOnEsc(true);
                        confirm.setCloseOnOutsideClick(false);


                    } else
                        login.setError(true);
                }

                //  falls user doch nicht im DB vorhanden ist
                else {
                    Dialog dialog = new Dialog();
                    VerticalLayout layoutDialog = new VerticalLayout();
                    layoutDialog.add(new Header(new H1("Jetzt registrieren.")));
                    layoutDialog.add(new Text("Du scheinst noch nicht registriert bzw. verifiziert zu sein."));
                    dialog.add(layoutDialog);
                    dialog.setWidth("320px");
                    dialog.setHeight("400px");
                    Button closeButton = new Button("Schließen");
                    Button registerButton = new Button("Registrieren");
                    registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    closeButton.addClickListener(event -> {
                        dialog.close();
                        UI.getCurrent().getPage().reload();
                    });

                    registerButton.addClickListener(event -> {
                        dialog.close();
                        UI.getCurrent().navigate(Constants.Pages.REGISTRATION);
                    });
                    HorizontalLayout buttonLayout = new HorizontalLayout(registerButton, closeButton);
                    buttonLayout.getStyle().set("flex-wrap", "wrap");
                    buttonLayout.setJustifyContentMode(JustifyContentMode.END);
                    dialog.add(buttonLayout);
                    dialog.open();
                }

            }
            if (isAuthenticated) {
                grabAndSetUserIntoSession();

              /*
              // bei Entwickler wird auf Entwickler Profil navigiert
              if (Objects.equals(userService.getRolle(), "e")){
                  UI.getCurrent().navigate(Constants.Pages.CREATEENTWICKLERPROFIL);
              }
              else {
              }
               */
                navigateToMainPage();
            }
        });
        layout.add(new Header(new H1("Willkommen in der Zukunft des Projektmanagements.")));
        layout.add(login);
        //layout.add(registerlink );
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        add(layout);
        this.setAlignItems(Alignment.CENTER);

    }

    /**
     * manages session during log in.
     * the global constant CURRENT_USER is used for session handling.
     */
    private void grabAndSetUserIntoSession() {
        //User und Entwickler/Manager/Reviews werden gesetzt
        // TODO: 27.10.2022 setAttribute für Entwickler/Manager/Reviews
        user = loginControl.getCurrentUser();

        UI.getCurrent().getSession().setAttribute(CURRENT_USER, user.getId());
        log.info(UI.getCurrent().getSession().getAttribute(CURRENT_USER).toString());

    }

    /**T
     * his method navigates to the individual Landing Page of the user.
     */
    private void navigateToMainPage() {
        UI.getCurrent().navigate(Constants.Pages.MAIN_VIEW);
    }

    /**
     * This method redirects users that are not logged in
     */
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (this.loginControl.getCurrentUser() != null) {
            this.grabAndSetUserIntoSession();
            UI.getCurrent().navigate(Constants.Pages.WELCOME_VIEW);
        }
    }

}
