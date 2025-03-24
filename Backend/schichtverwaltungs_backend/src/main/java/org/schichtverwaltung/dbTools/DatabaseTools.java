package org.schichtverwaltung.dbTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseTools {

    private Connection connection = null;


    public void connectToDB () {
        var path = "jdbc:sqlite:C:\\Users\\dinhc\\OneDrive\\Dokumente\\GitHub\\schichtverwaltung\\Backend\\schichtverwaltungs_db.db";
//        var path = "jdbc:sqlite:F:\\Documents\\GitHub\\schichtverwaltung\\Backend\\schichtverwaltungs_db.db";

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

        try (Statement statement1 = connection.createStatement()) {
            statement1.executeUpdate("PRAGMA foreign_keys = ON;");
            statement1.executeUpdate(statement);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public InfoSet executeSelectSQL (String statement) {
        if (connection == null) {
            throw new RuntimeException("Database Connection not initialised");
        }

        try {
            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement1.executeQuery(statement);

            InfoSet infoSet = new InfoSet(resultSet);
            connection.close();
            return infoSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
