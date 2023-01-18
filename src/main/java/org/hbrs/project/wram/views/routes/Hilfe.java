/**
 * @outhor Salah
 * @vision 1.0
 * @Zuletzt bearbeitet: 08.01.23 by Leon
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

@PageTitle("Hilfe")
@CssImport("./styles/views/main/main-view.css")
@Route(value = Constants.Pages.HilfeOUT, layout = AppViewOutside.class)
@Slf4j
public class Hilfe extends Div {

    private H2 title;

    @PostConstruct
    private void init() {
        add(createFormLayout());


    }

    /**
     * Diese Methode erzeugt das Formlayout Hife funktion darstellt.
     *
     * @return Instanz des Layouts
     */

    private void setupQuestionAnswer(Label question, Label answer) {
        answer.setVisible(false);
        question.getElement().addEventListener("click", e -> answer.setVisible(!answer.isVisible()));
    }

    public VerticalLayout createFormLayout() {
        VerticalLayout formLayout = new VerticalLayout();


        title = new H2("WAC FAQ-Hilfe:");
        Label question1 = new Label("Wie kann ich als Reviewer Entwickler zu Kundenprojekten zuweisen?");
        Label answer1 = new Label("Als Reviewer können Sie zunächst unter dem Reiter 'Kundenprojekte' einen tabellarischen Überblick über alle Projekte erhalten. \n Mit einem Klick auf das blaue '+' Zeichen unter Entwickler zuweisen werden Ihnen alle verfügbaren Entwickler angezeigt. \n Anschließend können sie mit einem einfach Klick auf 'Anfrage senden' eine Anfrage an den gewünschten Entwickler versenden.");
        Label question2 = new Label("Ich möchte mein Profilbild ändern. Wie gehts das?");
        Label answer2 = new Label("Um Ihr Profilbild zu ändern müssen Sie nach dem Login mit einem Klick auf 'Mein Profil' zu Ihrem Entwicklerprofil navigieren. \n  Dort können Sie nun ganz bequem per Drag & Drop oder über einen herkömmlichen Upload ihr neues Profilbild hochladen und speichern.");
        Label question3 = new Label("Ich möchte die Anwendung im Vollbildmodus nutzen. Wie blende ich das seitliche Navigationsmenü aus?");
        Label answer3 = new Label("Sie können das globale Navigationsmenü ganz einfach mit einem Klick auf das 'Hamburger-Menü-Icon' am oberen linken Bildschirmrand ausblenden.");
        Label question4 = new Label("Ich habe versehentlich eine Anfrage an den falschen Entwickler gesendet. Kann ich die Anfrage rückgängig machen?");
        Label answer4 = new Label("Als Manager können Sie eine Anfrage an einen Entwickler auch zurückziehen. \n  Navigieren Sie hierzu auf die Seite 'Anfrage and Entwickler senden' und klicken sie einfach auf den Button 'Anfrage zurücknehmen'.");
        Label question5 = new Label("Wofür steht WAC?");
        Label answer5 = new Label("WAC ist die Abkürzung für Web Application Company.");

        setupQuestionAnswer(question1, answer1);
        setupQuestionAnswer(question2, answer2);
        setupQuestionAnswer(question3, answer3);
        setupQuestionAnswer(question4, answer4);
        setupQuestionAnswer(question5, answer5);


        formLayout.add(title, question1, answer1, question2, answer2, question3, answer3, question4, answer4, question5, answer5);
        formLayout.setMaxWidth("900px");
        return formLayout;
    }

}
