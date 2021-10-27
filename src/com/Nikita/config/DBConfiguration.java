package com.Nikita.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfiguration {

    private final String databaseUrl = "jdbc:postgresql://127.0.0.1:5432/postgres?currentSchema=university";
    private final String user = "postgres";
    private final String password = "135246";
    Connection connection;

    public Connection getConnection() {
        {
            try {
                connection = DriverManager.getConnection(databaseUrl, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
