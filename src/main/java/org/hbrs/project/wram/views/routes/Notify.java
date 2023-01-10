/**
 * @outhor Salah
 * @vision 1.0
 * @Zuletzt bearbeiret: 06.12.22 by Salah
 */
package org.hbrs.project.wram.views.routes;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Notify {

    /**
     * benachrichtigung bei einem update
     */
    public static void notifyAfterUpdateWithOkay(String benachrichtigung) {
        Dialog confirm = new Dialog();
        confirm.setId("confirm-profile-update");
        confirm.open();

        VerticalLayout dialoglayout = new VerticalLayout(
                new Text(benachrichtigung),
                new Button("Ok", e ->
                        confirm.close()
                )
        );
        dialoglayout.setId("confirm-dialog-layout");

        confirm.add(
                dialoglayout
        );
        confirm.setCloseOnEsc(true);
        confirm.setCloseOnOutsideClick(false);
    }
}
