package org.hbrs.project.wram.views.routes.registration;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import org.hbrs.project.wram.control.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class VerifyView extends Div implements HasUrlParameter<String> {

    private String verificationCode;
    private String userId;

    @Autowired
    private UserService service;

    private void init() {
        if (!verifyUser(verificationCode)) {
            setUpErrorLayout();
        }
        VerticalLayout layout = new VerticalLayout();
        H1 header = new H1("Sie sind erfolgreich verifiziert!.");
        layout.add(header);
    }

    private void setUpErrorLayout() {
        VerticalLayout layout = new VerticalLayout();
        H1 header = new H1("Etwas ist schief gelaufen.");
        Div div = new Div();
        TextField field = new TextField("Gegebenenfalls sind Sie schon verifiziert.\nLoggen Sie sich in diesem Fall ein.");
        div.add(field);
        layout.add(header, div);
    }

    private boolean verifyUser(String code) {
        return service.verify(code);
    }

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
}
