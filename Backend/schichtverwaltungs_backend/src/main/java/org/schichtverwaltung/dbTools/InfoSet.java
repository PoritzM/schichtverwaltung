package org.schichtverwaltung.dbTools;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class InfoSet {

    private final ResultSet resultSet;

    private ArrayList<String> descriptions = new ArrayList<>();
    private ArrayList<ArrayList<Object>> infos = new ArrayList<>();

    public InfoSet(ResultSet resultSet) throws SQLException {
        this.resultSet = resultSet;

            descriptions = getDescriptions(resultSet);
            infos = getRows(resultSet);

//            printInfoSet();
    }

    private ArrayList<String> getDescriptions (ResultSet resultSet) throws SQLException {

        ArrayList<String> descriptions = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        int amountColumns = resultSetMetaData.getColumnCount();

        for (int index = 1; index <= amountColumns; index++) {

            String description = resultSetMetaData.getColumnName(index);
            descriptions.add(description);
        }

        return descriptions;
    }

    private ArrayList<ArrayList<Object>> getRows (ResultSet resultSet) throws SQLException {

        ArrayList<ArrayList<Object>> infos = new ArrayList<>();

        while (resultSet.next()) {

            ArrayList<Object> rowInfo = new ArrayList<>();

            for (String description : descriptions) {
                rowInfo.add(resultSet.getObject(description));
            }
            infos.add(rowInfo);
        }

        return infos;
    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public ArrayList<ArrayList<Object>> getInfos() {
        return infos;
    }

    public int amountRows () {
        return infos.size();
    }

    public ArrayList<Object> getColumnValues (String description) {
        ArrayList<Object> values = new ArrayList<>();

        int index = descriptions.indexOf(description);

        if (index == -1) {
            throw new RuntimeException("description not found " + description);
        }

        for (ArrayList<Object> rows : infos) {
            values.add(rows.get(index));
        }

        return values;
    }

    public void printInfoSet () {

        for (String description : descriptions) {
            System.out.print(description + ";");
        }

        System.out.println();

        for (ArrayList<Object> rows : infos) {
            for (Object row : rows) {
                System.out.print(row + ";");
            }
        }
        System.out.println();
    }
}
