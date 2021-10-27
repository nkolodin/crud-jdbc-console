package com.Nikita.service;

import com.Nikita.config.DBConfiguration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqlFileExecutor {

    Parser parser = new Parser();
    DBConfiguration DBConfiguration = new DBConfiguration();

    public void sqlExecutor() {
        try {
            Statement statement = DBConfiguration.getConnection().createStatement();
            List<String> sqlQueries = parser.parse();
            sqlQueries.forEach(s -> {
                try {
                    statement.execute(s);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
