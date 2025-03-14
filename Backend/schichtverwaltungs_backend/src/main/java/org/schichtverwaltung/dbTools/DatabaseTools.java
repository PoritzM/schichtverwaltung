package org.schichtverwaltung.dbTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseTools {

    private Connection connection = null;


    public void connectToDB () {
        var path = "jdbc:sqlite:C:\\Users\\PC-Moritz\\Documents\\GitHub\\schichtverwaltung\\Backend\\schichtverwaltungs_db.db";

        try {
            connection = DriverManager.getConnection(path);
//            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
//            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void executeSQL (String statement) {
        if (connection == null) {
            throw new RuntimeException("Database Connection not initialised");
        }

        try (Statement statement1 = connection.createStatement();) {
            statement1.executeUpdate(statement);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> executeSelectSQL(String statement) throws SQLException {
        if (connection == null) {
            throw new RuntimeException("Database Connection not initialised");
        }

        List<Map<String, Object>> results = new ArrayList<>();

        try (Statement statement1 = connection.createStatement();
             ResultSet resultSet = statement1.executeQuery(statement)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    row.put(columnName, columnValue);
                }
                results.add(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        connection.close();
        return results;
    }

    public static Object sucheWertInMapListe(List<Map<String, Object>> daten, String spaltenname, Object suchwert) {
        for (Map<String, Object> zeile : daten) {
            if (zeile.containsKey(spaltenname) && zeile.get(spaltenname).equals(suchwert)) {
                // Wert gefunden
                return zeile.get(spaltenname); // Gibt den gefundenen Wert zurück
            }
        }
        // Wert nicht gefunden
        return null;
    }

//    public InfoSet executeSelectSQL (String statement) {
//        if (connection == null) {
//            throw new RuntimeException("Database Connection not initialised");
//        }
//
//        try {
//            Statement statement1 = connection.createStatement();
//            ResultSet resultSet = statement1.executeQuery(statement);
////            connection.close();
//            return new InfoSet(resultSet, statement1);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public int executeInsertSQLreturnID (String sql) {

        connectToDB();

        if (connection == null) {
            throw new RuntimeException("Database Connection not initialised");
        }

        int id = -1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.executeUpdate();

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid();")) {

                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return id;
    }
}
