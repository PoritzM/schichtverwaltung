package org.schichtverwaltung.dbTools;

import org.schichtverwaltung.zUtils.TimeStamps;

public class UpdateMethods {

    public static void updateEvent (int eventID, String valueName, String value) {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "UPDATE events SET " + valueName + " = \"" + value + "\" WHERE eventID = " + eventID;
        databaseTools.executeSQL(statement);

        DatabaseTools databaseTools2 =  new DatabaseTools();
        databaseTools2.connectToDB();
        TimeStamps timeStamps = new TimeStamps();
        String statementTimeStamp = "UPDATE events SET timeStampEdit = \"" + timeStamps.getTimeStampEdit() + "\" WHERE eventID = " + eventID;
        databaseTools2.executeSQL(statementTimeStamp);
    }

    public static void updateTable (String table, String valueName, String value, String idName, String idValue) {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "UPDATE " + table +  " SET " + valueName + " = \"" + value + "\" WHERE " + idName +  " = " + idValue;

        databaseTools.executeSQL(statement);

        DatabaseTools databaseTools2 =  new DatabaseTools();
        databaseTools2.connectToDB();
        TimeStamps timeStamps = new TimeStamps();
        String statementTimeStamp = "UPDATE " + table +  " SET timeStampEdit = \"" + timeStamps.getTimeStampEdit() + "\" WHERE " + idName +  " = " + idValue;
        databaseTools2.executeSQL(statementTimeStamp);
    }
}
