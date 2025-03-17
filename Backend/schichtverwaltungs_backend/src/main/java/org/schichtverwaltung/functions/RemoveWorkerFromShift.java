package org.schichtverwaltung.functions;

import org.schichtverwaltung.dbTools.InfoSet;

import java.sql.SQLException;

import static org.schichtverwaltung.dbTools.RemoveMethods.removeWorker;
import static org.schichtverwaltung.dbTools.SelectMethods.selectWorker;

public class RemoveWorkerFromShift {

    public static void removeWorkerFromShift (int workerID) throws SQLException {

        InfoSet infoSet = selectWorker(workerID);

        if (infoSet.amountRows() == 1) {
            removeWorker(workerID);
        } else {
            throw new RuntimeException("Worker not found");
        }

    }
}
