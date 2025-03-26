package org.schichtverwaltung.dbTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.schichtverwaltung.zUtils.YmalReader.getYamlValue;

public class DatabaseTools {

    private Connection connection = null;

    //Stellt Verbindung zur Datenbank her
    public void connectToDB () {
        var path = "jdbc:sqlite:" + getYamlValue("DBpath");
        try {
            connection = DriverManager.getConnection(path);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //Führt ein "normales" sql statement durch
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

    //Führt ein Select aus und macht aus dem ResultSet ein InfoSet, da das ResultSet nur solange vorhanden ist, wie die verbindung zu Datenbank vorhanden ist
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

    //Führt das Einfügen von Daten in die Datenbank aus und liefert die ID der erstellen Einträge zurück
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
