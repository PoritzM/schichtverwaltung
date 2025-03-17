package org.schichtverwaltung.functions;

import org.schichtverwaltung.dbTools.InfoSet;

import java.sql.SQLException;

import static org.schichtverwaltung.dbTools.RemoveMethods.removeEvent;
import static org.schichtverwaltung.dbTools.RemoveMethods.removeWorker;
import static org.schichtverwaltung.dbTools.SelectMethods.selectTable;

public class RemoveShift {

    public static void removeShift (int eventID) throws SQLException {

        InfoSet infoSet = selectTable("eventID", String.valueOf(eventID), "events");

        if (infoSet.amountRows() == 1) {
            removeEvent(eventID);
        } else {
            throw new RuntimeException("Event not found");
        }

    }
}
