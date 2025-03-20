package org.schichtverwaltung.functions;

import org.schichtverwaltung.dbTools.InfoSet;
import org.schichtverwaltung.exceptions.BackendException;
import org.schichtverwaltung.exceptions.ItemNotFoundException;
import org.schichtverwaltung.exceptions.ValueAlreadySetException;
import org.schichtverwaltung.zUtils.ReturnInfos;

import java.sql.SQLException;
import java.util.Objects;

import static org.schichtverwaltung.dbTools.RemoveMethods.removeEvent;
import static org.schichtverwaltung.dbTools.SelectMethods.selectTable;
import static org.schichtverwaltung.dbTools.UpdateMethods.updateEvent;

public class UpdateEvent {

    public static void doUpdateShowEvent (int eventID, boolean newShowEvent) throws BackendException, ItemNotFoundException, ValueAlreadySetException {
        try {
            updateShowEvent(eventID, newShowEvent);
        } catch (SQLException exception) {
            throw new BackendException("OHGHHH FUCKCKCK " + exception.getMessage());
        }
    }

    public static void doUpdateRegisterOnEvent (int eventID, boolean newRegisterOnEvent) throws BackendException, ItemNotFoundException, ValueAlreadySetException {
        try {
            updateRegisterOnEvent(eventID, newRegisterOnEvent);
        } catch (SQLException exception) {
            throw new BackendException("OHGHHH FUCKCKCK " + exception.getMessage());
        }
    }

    public static void updateShowEvent (int eventID, boolean showEvent) throws SQLException, ItemNotFoundException, ValueAlreadySetException {

        InfoSet checkForEvent = selectTable("eventID", String.valueOf(eventID), "events");

        if(checkForEvent.getColumnValues("eventID").isEmpty()) {
            throw new ItemNotFoundException("EventID " + eventID + " not found!");
        }

        InfoSet infoSet = selectTable("eventID", String.valueOf(eventID), "events");

        if (!Objects.equals((String) infoSet.getColumnValues("showEvent").get(0), Boolean.toString(showEvent))) {
            if (infoSet.amountRows() == 1) {
                updateEvent(eventID, "showEvent", Boolean.toString(showEvent));
            } else {
                throw new ItemNotFoundException("EventID " + eventID + " not found!");
            }
        } else {
            throw new ValueAlreadySetException("Show Event already set to " + showEvent);
        }
    }

    public static void updateRegisterOnEvent (int eventID, boolean registerOnEvent) throws SQLException, ItemNotFoundException, ValueAlreadySetException {

        InfoSet checkForEvent = selectTable("eventID", String.valueOf(eventID), "events");

        if(checkForEvent.getColumnValues("eventID").isEmpty()) {
            throw new ItemNotFoundException("EventID " + eventID + "not found!");
        }

        InfoSet infoSet = selectTable("eventID", String.valueOf(eventID), "events");

        if (!Objects.equals((String) infoSet.getColumnValues("registerOnEvent").get(0), Boolean.toString(registerOnEvent))) {
            if (infoSet.amountRows() == 1) {
                updateEvent(eventID, "registerOnEvent", Boolean.toString(registerOnEvent));
            } else {
                throw new ItemNotFoundException("EventID " + eventID + "not found!");
            }
        } else {
            throw new ValueAlreadySetException("Register on Event already set to " + registerOnEvent);
        }
    }
}
