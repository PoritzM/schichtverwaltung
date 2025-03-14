package org.schichtverwaltung.dbTools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SelectMethods {

    public static List<Map<String, Object>> selectEvent (int eventID) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM events WHERE eventID = " + eventID;

        return databaseTools.executeSelectSQL(statement);
    }

    public static List<Map<String, Object>> selectDay (int dayID) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM days WHERE dayID = " + dayID;

        return databaseTools.executeSelectSQL(statement);
    }

    public static List<Map<String, Object>> selectService (int serviceID) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM services WHERE serviceID = " + serviceID;

        return databaseTools.executeSelectSQL(statement);
    }

    public static List<Map<String, Object>> selectTask (int taskID) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM tasks WHERE taskID = " + taskID;

        return databaseTools.executeSelectSQL(statement);
    }

    public static List<Map<String, Object>> selectWorker (int workerID) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM worker WHERE workerID = " + workerID;

        return databaseTools.executeSelectSQL(statement);
    }
}
