/**
 * @outhor Salah
 * @vision 1.0
 * @Zuletzt bearbeiret: 08.01.23 by Leon
 */
package org.hbrs.project.wram.views.routes;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.common.layouts.AppViewOutside;

import javax.annotation.PostConstruct;

@PageTitle("Über uns")
@CssImport("./styles/views/main/main-view.css")
@Route(value = Constants.Pages.UeberUnsOut, layout = AppViewOutside.class)
@Slf4j
public class UeberUns extends Div {
    private H2 title;

    @PostConstruct
    private void init() {
        add(createFormLayout());


    }

    /**
     * Diese Methode erzeugt das Formlayout darstellt.
     *
     * @return Instanz des Layouts
     */
    public VerticalLayout createFormLayout() {
        VerticalLayout formLayout = new VerticalLayout();


        title = new H2("WAC");
        Label heading = new Label("Über uns");
        heading.addClassName("about-us-heading");
        Label text = new Label("WAC ist eine Firma, die sich auf die Erstellung moderner Web-Applikationen spezialisiert hat. \n Unser agiles, junges Team passt sich jeder Herausforderung an und strebt stets danach die Wünsche des Kunden zu erfüllen. \n Kontaktieren Sie uns unter wac.wram@web.de damit wir uns darüber austauschen können, wie wir auch Ihrem Unternehmen helfen können!");
        text.addClassName("about-us-text");
        Label footer = new Label("Copyright 2022. All rights reserved.");
        footer.addClassName("about-us-footer");

        formLayout.add(title, heading, text, footer);
        formLayout.setMaxWidth("900px");
        return formLayout;
    }


}
