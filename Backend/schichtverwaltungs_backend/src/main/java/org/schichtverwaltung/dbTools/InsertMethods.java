package org.schichtverwaltung.dbTools;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

//Einzelnen Insert Methoden fürs Hinzufügen von Daten in die Datenbank
public class InsertMethods {

    public static int insertEvent (String eventName, boolean showEvent, boolean registerOnEvent, Date timeStampCreate, Date timeStampEdit) {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "INSERT INTO events (eventName, showEvent, registerOnEvent, timeStampCreate, timeStampEdit) " +
                "VALUES (\"" + eventName + "\", \"" + showEvent + "\", \"" + registerOnEvent + "\", \"" + timeStampCreate + "\", \"" + timeStampEdit + "\")";

        return databaseTools.executeInsertSQLreturnID(statement);
    }

    public static int insertDay (int eventID, LocalDate day, Date timeStampCreate, Date timeStampEdit) {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "INSERT INTO days (eventID, day, timeStampCreate, timeStampEdit) " +
                "VALUES (\"" + eventID + "\", \"" + day + "\", \"" + timeStampCreate + "\", \"" + timeStampEdit + "\")";

        return databaseTools.executeInsertSQLreturnID(statement);
    }

    public static int insertService (int eventID, int dayID, String serviceDescription, Date timeStampCreate, Date timeStampEdit, LocalTime timeStart, LocalTime timeEnd) {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "INSERT INTO services (eventID, dayID, serviceDescription, timeStampCreate, timeStampEdit, timeStart, timeEnd) " +
                "VALUES (\"" + eventID + "\", \"" + dayID + "\", \"" + serviceDescription +  "\", \"" + timeStampCreate + "\", \"" + timeStampEdit + "\", \"" + timeStart +  "\", \"" + timeEnd + "\")";

        return databaseTools.executeInsertSQLreturnID(statement);
    }

    public static int insertTask (int eventID, int dayID, int serviceID, String taskDescription, int neededWorker, Date timeStampCreate, Date timeStampEdit) {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "INSERT INTO tasks (eventID, dayID, serviceID, taskDescription, neededWorker, timeStampCreate, timeStampEdit) " +
                "VALUES (\"" + eventID + "\", \"" + dayID + "\", \"" + serviceID +  "\", \"" + taskDescription + "\", \"" + neededWorker + "\", \"" + timeStampCreate +  "\", \"" + timeStampEdit + "\")";

        return databaseTools.executeInsertSQLreturnID(statement);
    }

    public static int insertWorker (int eventID, int dayID, int serviceID, int taskID, String workerName, Date timeStampCreate, Date timeStampEdit) {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "INSERT INTO worker (eventID, dayID, serviceID, taskID, workerName, timeStampCreate, timeStampEdit) " +
                "VALUES (\"" + eventID + "\", \"" + dayID + "\", \"" + serviceID +  "\", \"" + taskID + "\", \"" + workerName + "\", \"" + timeStampCreate +  "\", \"" + timeStampEdit + "\")";

        return databaseTools.executeInsertSQLreturnID(statement);
    }
}
