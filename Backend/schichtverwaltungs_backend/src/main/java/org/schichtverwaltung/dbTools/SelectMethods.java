package org.schichtverwaltung.dbTools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SelectMethods {

    public static InfoSet selectTable (String searchDescription, String searchValue,String table) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM " + table + " WHERE " + searchDescription + " = " + searchValue;

        return databaseTools.executeSelectSQL(statement);
    }

    public static InfoSet selectTableAll (String table) throws SQLException {

        DatabaseTools databaseTools =  new DatabaseTools();
        databaseTools.connectToDB();

        String statement = "SELECT * FROM " + table;

        return databaseTools.executeSelectSQL(statement);
    }
}
