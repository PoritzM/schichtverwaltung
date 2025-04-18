package org.schichtverwaltung.dbTools;

//Methoden um Daten aus der Datenbank zu löschen
public class RemoveMethods {

    public static void removeEvent (int eventID) {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "DELETE FROM events WHERE eventID = " + eventID;

        databaseTools.executeSQL(statement);
    }

    public static void removeWorker (int workerID) {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "DELETE FROM worker WHERE workerID = " + workerID;

        databaseTools.executeSQL(statement);
    }

    //Flexibel einsetzbare Remove Methode
    public static void removeEntire (String table, String idName, String idValue) {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "DELETE FROM " + table + " WHERE " + idName +  " = " + idValue;

        databaseTools.executeSQL(statement);
    }
}
