package org.schichtverwaltung.dbTools;

import java.sql.DriverManager;
import java.sql.SQLException;

public class InitConnection {

    public static void connectToDB () {
        String path = "..\\..\\schichtverwaltungs_db.db";
//        var path = "jdbc:sqlite:C:\\Users\\PC-Moritz\\Documents\\GitHub\\schichtverwaltung\\Backend\\schichtverwaltungs_db.db";

        try (var conn = DriverManager.getConnection(path)) {
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
