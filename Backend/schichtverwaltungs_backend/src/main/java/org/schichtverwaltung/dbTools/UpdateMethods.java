package org.schichtverwaltung.dbTools;

import org.schichtverwaltung.zUtils.TimeStamps;

//Methoden um Daten in der Datenbank zu aktualisieren
public class UpdateMethods {

    //Genutzt für Show oder Register zu bearbeiten
    public static void updateEvent (int eventID, String valueName, String value) {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "UPDATE events SET " + valueName + " = \"" + value + "\" WHERE eventID = " + eventID;
        databaseTools.executeSQL(statement);

        //aktualisieren des EditTimeStamps
        DatabaseTools databaseTools2 =  new DatabaseTools();
        databaseTools2.connectToDB();
        TimeStamps timeStamps = new TimeStamps();
        String statementTimeStamp = "UPDATE events SET timeStampEdit = \"" + timeStamps.getTimeStampEdit() + "\" WHERE eventID = " + eventID;
        databaseTools2.executeSQL(statementTimeStamp);
    }

    //Um allgemein Daten in einer Tabelle zu aktualisieren
    public static void updateTable (String table, String valueName, String value, String idName, String idValue) {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "UPDATE " + table +  " SET " + valueName + " = \"" + value + "\" WHERE " + idName +  " = " + idValue;
        databaseTools.executeSQL(statement);

        //aktualisieren des EditTimeStamps
        DatabaseTools databaseTools2 =  new DatabaseTools();
        databaseTools2.connectToDB();
        TimeStamps timeStamps = new TimeStamps();
        String statementTimeStamp = "UPDATE " + table +  " SET timeStampEdit = \"" + timeStamps.getTimeStampEdit() + "\" WHERE " + idName +  " = " + idValue;
        databaseTools2.executeSQL(statementTimeStamp);
    }
}
