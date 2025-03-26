package org.schichtverwaltung.dbTools;

import java.sql.SQLException;

//Methoden um Daten aus der Datenbank zu lesen
public class SelectMethods {

    //Um nur eine gewisse Spalte aus der Datenbank auszulesen
    public static InfoSet selectTable (String searchDescription, String searchValue,String table) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM " + table + " WHERE " + searchDescription + " = " + searchValue;

        return databaseTools.executeSelectSQL(statement);
    }

    //Um alles aus einer Tabelle der Datenbank zu lesen
    public static InfoSet selectTableAll (String table) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM " + table;

        return databaseTools.executeSelectSQL(statement);
    }
}
