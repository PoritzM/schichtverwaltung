package org.schichtverwaltung.dbTools;

import java.sql.ResultSet;
import java.sql.Statement;

public class InfoSet {

    private final ResultSet resultSet;
    private final Statement statement;

    public InfoSet(ResultSet resultSet, Statement statement) {
        this.resultSet = resultSet;
        this.statement = statement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public Statement getStatement() {
        return statement;
    }
}
