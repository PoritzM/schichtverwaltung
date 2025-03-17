package org.schichtverwaltung.dbTools;

public class UpdateMethods {

    public static void updateEvent (int eventID, String valueName, String value) {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "UPDATE events SET " + valueName + " = \"" + value + "\" WHERE eventID = " + eventID;

        databaseTools.executeSQL(statement);
    }
}
