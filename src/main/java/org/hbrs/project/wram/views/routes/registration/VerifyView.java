/**
 * @outhor sophia
 * @vision 1.0
 * @Zuletzt bearbeiret: 06.12.22 by Salah
 */
package org.hbrs.project.wram.views.routes.registration;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppViewOutside;
import org.hbrs.project.wram.views.routes.main.LoginView;
import org.springframework.beans.factory.annotation.Autowired;


@PageTitle("Verifizieren")
@Route(value = Constants.Pages.VERIFY_VIEW, layout = AppViewOutside.class)
@Slf4j
public class VerifyView extends Div implements HasUrlParameter<String> {

    private String verificationCode;
    private String userId;

    @Autowired
    private UserService service;

    /**
     * * Übergabe des durch die URL transferierten Verificationcodes
     *
     * @param beforeEvent aktueller benutzer
     * @param s           übergabeparameter für verificationcode
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        if (s == null) {
            verificationCode = null;
            userId = null;
        } else {
            verificationCode = s;
            this.init();
        }
    }

    private void init() {
        if (!verifyUser(verificationCode)) {
            setUpErrorLayout();
        }
        VerticalLayout layout = new VerticalLayout();
        H1 header = new H1("Du bist erfolgreich verifiziert!.");
        layout.add(header);
        RouterLink link = new RouterLink("Jetzt einloggen.", LoginView.class);
        layout.add(link);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        this.add(layout);
    }

    private void setUpErrorLayout() {
        VerticalLayout layout = new VerticalLayout();
        H1 header = new H1("Etwas ist schief gelaufen.");
        Div div = new Div();
        TextField field = new TextField("Gegebenenfalls bist du schon verifiziert.\nLogge dich in diesem Fall einfach ein.");
        div.add(field);
        layout.add(header, div);
    }

    /**
     * * Schaue ob code valide ist
     *
     * @param code verifikationscode
     */
    private boolean verifyUser(String code) {
        return service.verify(code);
    }


}
