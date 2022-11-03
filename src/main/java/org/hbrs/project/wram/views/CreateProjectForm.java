package org.hbrs.project.wram.views;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.hbrs.project.wram.control.LoginControl;
import org.hbrs.project.wram.control.kundenprojekt.KundenprojektService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.kundenprojekt.KundenprojektDTO;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.manager.ManagerRepository;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@PageTitle("ProjekteErstellen")
@Route(value=Constants.Pages.CREATEPROJECT)
public class CreateProjectForm extends Div implements BeforeEnterObserver {

    private H3 title;

    private TextField projektname;
    private TextField skills;
    private TextArea  projektbeschreibung;

    RadioButtonGroup<String> oeff;


    private Button bestätigungsknopf;

    private Binder<KundenprojektDTO> kundenprojektDTOBinder = new Binder<>(KundenprojektDTO.class);

    @Autowired
    private KundenprojektService kundenprojektServices;

    @Autowired
    private LoginControl control;

    @Autowired
    private ManagerService managerService;

    public CreateProjectForm() {

        add(createFormLayout());
        bindFields();
        clearForm();

        bestätigungsknopf.addClickListener(e->{
            navigateToAppView();
        });

    }

    private void navigateToAppView() {
        UI.getCurrent().navigate("Appview");   //Appview
        Notification.show("Projekt erfolgreich erstellt.", 3000, Notification.Position.MIDDLE);
    }
    public VerticalLayout createFormLayout(){
        VerticalLayout formLayout = new VerticalLayout();
        title = new H3("Projekt erstellen");
        projektname = new TextField();
        projektname.setValue("Projektname");
        skills = new TextField();
        skills.setValue("Erforderliche Skills");

        projektbeschreibung=new TextArea();
        projektbeschreibung.setWidthFull();
        projektbeschreibung.setValue("Projektbeschreibung");
        //Radiobutton für rolle
        oeff = new RadioButtonGroup<>();
        oeff.setLabel("Soll das Projekt veröffentlicht werden");
        oeff.setItems("veroeffentlichen","nicht veroeffentlichen");
        oeff.setValue("veroeffentlichen");
        oeff.setEnabled(true);

        setRequiredIndicatorVisible(projektname, skills,  projektbeschreibung);
        bestätigungsknopf = new Button("Jetzt Erstellen");
        bestätigungsknopf.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        bestätigungsknopf.addClickListener(saveKundenprojekt(createKundenprojekt(projektname.getValue(),projektbeschreibung.getValue(),oeff.getValue())));
        RouterLink appView = new RouterLink("Zurück zur Uebersicht", AppView.class);
        formLayout.add(title,projektname, skills, projektbeschreibung ,oeff, bestätigungsknopf, appView);

        // Max width of the Form
        formLayout.setMaxWidth("900px");


        return formLayout;
    }

    private Kundenprojekt createKundenprojekt(String projektname, String projektbeschreibung,String oeff) {
       // UUID u =(UUID)  UI.getCurrent().getSession().getAttribute("user");
        UserDTO u=(UserDTO) UI.getCurrent().getSession().getAttribute("current_User");
        UUID uuid=u.getId();
        Manager m=null;
        //Problem: Service Klasse ist immer null!!
        if(this.managerService!=null){
         m=this.managerService.getByUserId(uuid);
         System.out.println(m.getId().toString());
        }
        Boolean b= false;
        if(oeff.equals("veroeffentlichen")){
            b=true;
        }
        System.out.println(oeff);
        System.out.println(projektname);
        System.out.println(projektbeschreibung);
        return Kundenprojekt.builder().manager(m).projektbeschreibung(projektbeschreibung).projektname(projektname).publicProjekt(b).build();

    }

    private ComponentEventListener<ClickEvent<Button>> saveKundenprojekt(Kundenprojekt k) {
            return event-> {
                this.kundenprojektServices.doCreate(k);
            };
    }

    @Override
    /**
     * Methode wird vor der eigentlichen Darstellung der UI-Components aufgerufen.
     * Hier kann man die finale Darstellung noch abbrechen, wenn z.B. der Nutzer nicht eingeloggt ist
     * Dann erfolgt hier ein ReDirect auf die Login-Seite. Eine Navigation (Methode navigate)
     * ist hier nicht möglich, da die finale Navigation noch nicht stattgefunden hat.
     * Diese Methode in der AppLayout sichert auch den un-authorisierten Zugriff auf die innerliegenden
     * Views (hier: ShowCarsView und EnterCarView) ab.
     *
     */
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (control.getCurrentUser() == null) {
            beforeEnterEvent.rerouteTo(Constants.Pages.MAIN_VIEW);
        }
    }
    private void bindFields(){

    }

    private void clearForm(){
         kundenprojektDTOBinder.setBean(new KundenprojektDTO());
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }
}
