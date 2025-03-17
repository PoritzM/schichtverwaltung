package org.schichtverwaltung.functions;

import org.schichtverwaltung.dbTools.InfoSet;

import java.sql.SQLException;
import java.util.Objects;

import static org.schichtverwaltung.dbTools.RemoveMethods.removeEvent;
import static org.schichtverwaltung.dbTools.SelectMethods.selectEvent;
import static org.schichtverwaltung.dbTools.UpdateMethods.updateEvent;

public class UpdateEvent {

    public static void updateShowEvent (int eventID, boolean showEvent) throws SQLException {

        InfoSet infoSet = selectEvent(eventID);

        if (!Objects.equals((String) infoSet.getColumnValues("showEvent").getFirst(), Boolean.toString(showEvent))) {
            if (infoSet.amountRows() == 1) {
                updateEvent(eventID, "showEvent", Boolean.toString(showEvent));
            } else {
                throw new RuntimeException("Event not found");
            }
        } else {
        System.out.println("Value is already set");
        }
    }

    public static void updateRegisterOnEvent (int eventID, boolean showEvent) throws SQLException {

        InfoSet infoSet = selectEvent(eventID);

        if (!Objects.equals((String) infoSet.getColumnValues("registerOnEvent").getFirst(), Boolean.toString(showEvent))) {
            if (infoSet.amountRows() == 1) {
                updateEvent(eventID, "registerOnEvent", Boolean.toString(showEvent));
            } else {
                throw new RuntimeException("Event not found");
            }
        } else {
            System.out.println("Value is already set");
        }
    }
}
