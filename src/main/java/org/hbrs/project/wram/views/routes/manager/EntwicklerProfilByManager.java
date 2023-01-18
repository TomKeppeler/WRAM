/**
 * @outhor Salah
 * @vision 1.0
 * @Zuletzt bearbeiret: 13.12.22 by Salah
 */

package org.hbrs.project.wram.views.routes.manager;


import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.control.LoginControl;
import org.hbrs.project.wram.control.entwickler.EntwicklerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.model.entwickler.EntwicklerDTO;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.util.Utils;
import org.hbrs.project.wram.views.common.layouts.AppView;
import org.hbrs.project.wram.views.routes.main.LandingView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@PageTitle("Entwickler Profil")
@CssImport("./styles/views/main/main-view.css")
@Route(value = Constants.Pages.ENTWICKLER_PROFIL_BY_MANAGER, layout = AppView.class)
@Slf4j
public class EntwicklerProfilByManager extends Div {

    private static Entwickler aktuellerEntwickler = null;
    private final Binder<EntwicklerDTO> entwicklerDTOBinder = new Binder<>(EntwicklerDTO.class);
    private H2 title;
    private TextField email;
    private TextField phone;
    private TextArea skills;
    private Image image = new Image("images/defaultP.png", "Profile Picture");
    @Autowired
    private EntwicklerService entwicklerService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginControl control;

    static void setEntwickler(Entwickler entwickler1) {
        aktuellerEntwickler = entwickler1;
    }

    @PostConstruct
    private void init() {

        add(createFormLayout());
    }

    /**
     * Diese Methode erzeugt das Formlayout, welches Komponenten von ProfilInformationen.
     *
     * @return Instanz des Layouts
     */
    public VerticalLayout createFormLayout() {
        VerticalLayout formLayout = new VerticalLayout();


        title = new H2("Entwickler Profil von " + aktuellerEntwickler.getFirstname() + " " + aktuellerEntwickler.getName());
        email = new TextField("E-Mail");
        phone = new TextField("Telefonnummer");
        skills = new TextArea("Skills");
        skills.setWidthFull();


        RouterLink backlink = new RouterLink("Zurück zur Startseite", LandingView.class);

        email.setValue(aktuellerEntwickler.getUser().getEmail());
        email.setReadOnly(true);

        if (aktuellerEntwickler.getImage() != null) {
            setProfileImage();
        }
        if (aktuellerEntwickler.getPhone() == null || aktuellerEntwickler.getPhone().length() == 0) {
            phone.setPlaceholder("Telefonnummer");
        } else {
            phone.setValue(aktuellerEntwickler.getPhone());
        }
        phone.setReadOnly(true);
        if (aktuellerEntwickler.getSkills() == null || aktuellerEntwickler.getSkills().length() == 0) {
            skills.setPlaceholder("Skills");
        } else {
            skills.setValue(aktuellerEntwickler.getSkills());
        }
        skills.setReadOnly(true);


        formLayout.add(title, this.image, email, phone, skills, backlink);
        formLayout.setMaxWidth("900px");
        return formLayout;
    }

    /**
     * @apiNote the image is fetcht from the database via the entwicklerService and set in the image component
     * @autor: @tkeppe2s (Tom Keppeler)
     */
    private void setProfileImage() {
        this.image = Utils.generateImage(this.entwicklerService.getImage(aktuellerEntwickler.getId()));

    }


}
