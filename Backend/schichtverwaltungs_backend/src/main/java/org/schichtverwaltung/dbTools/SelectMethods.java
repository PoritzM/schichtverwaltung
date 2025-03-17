package org.schichtverwaltung.dbTools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SelectMethods {

    public static InfoSet selectEvent (int eventID) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM events WHERE eventID = " + eventID;

        return databaseTools.executeSelectSQL(statement);
    }

    public static InfoSet selectDay (int dayID) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM days WHERE dayID = " + dayID;

        return databaseTools.executeSelectSQL(statement);
    }

    public static InfoSet selectService (int serviceID) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM services WHERE serviceID = " + serviceID;

        return databaseTools.executeSelectSQL(statement);
    }

    public static InfoSet selectTask (int taskID) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM tasks WHERE taskID = " + taskID;

        return databaseTools.executeSelectSQL(statement);
    }

    public static InfoSet selectWorker (int workerID) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM worker WHERE workerID = " + workerID;

        return databaseTools.executeSelectSQL(statement);
    }
}
