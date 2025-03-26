package org.schichtverwaltung.functions;

import org.schichtverwaltung.dbTools.InfoSet;
import org.schichtverwaltung.exceptions.BackendException;
import org.schichtverwaltung.exceptions.ItemNotFoundException;

import java.sql.SQLException;

import static org.schichtverwaltung.dbTools.RemoveMethods.removeEvent;
import static org.schichtverwaltung.dbTools.SelectMethods.selectTable;

//Löschen einer Schicht
public class RemoveShift {

    //Ausführer der Methode zum Catchen und Verwalten von Exceptions
    public static void doRemoveShift (int eventID) throws BackendException, ItemNotFoundException {
        try {
            removeShift(eventID);
        } catch (SQLException sqlException) {
            throw new BackendException("MEGA SHIT " + sqlException.getMessage());
        }
    }

    private static void removeShift (int eventID) throws SQLException {

        InfoSet checkForEvent = selectTable("eventID", String.valueOf(eventID), "events");

        if(checkForEvent.getColumnValues("eventID").isEmpty()) {
            throw new ItemNotFoundException("EventID " + eventID + " not found!");
        }

        InfoSet infoSet = selectTable("eventID", String.valueOf(eventID), "events");

        if (infoSet.amountRows() == 1) {
            removeEvent(eventID);
        } else {
            throw new ItemNotFoundException("EventID " + eventID + " not found!");
        }
    }
}
