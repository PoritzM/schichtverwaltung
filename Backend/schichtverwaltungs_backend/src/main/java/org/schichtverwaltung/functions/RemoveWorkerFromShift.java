package org.schichtverwaltung.functions;

import org.schichtverwaltung.dbTools.InfoSet;
import org.schichtverwaltung.exceptions.BackendException;
import org.schichtverwaltung.exceptions.ItemNotFoundException;

import java.sql.SQLException;

import static org.schichtverwaltung.dbTools.RemoveMethods.removeWorker;
import static org.schichtverwaltung.dbTools.SelectMethods.selectTable;

public class RemoveWorkerFromShift {

    public static void doRemoveWorkerFromShift (int workerID) throws BackendException, ItemNotFoundException {
        try {
            removeWorkerFromShift(workerID);
        } catch (SQLException sqlException) {
            throw new BackendException("MEGA SHIT " + sqlException.getMessage());
        }
    }

    public static void removeWorkerFromShift (int workerID) throws SQLException {

        InfoSet checkForEvent = selectTable("workerID", String.valueOf(workerID), "worker");

        if(checkForEvent.getColumnValues("eventID").isEmpty()) {
            throw new ItemNotFoundException("WorkerID " + workerID + " not found!");
        }

        InfoSet infoSet = selectTable("workerID", String.valueOf(workerID), "worker");

        if (infoSet.amountRows() == 1) {
            removeWorker(workerID);
        } else {
            throw new ItemNotFoundException("WorkerID " + workerID + " not found!");
        }

    }
}
