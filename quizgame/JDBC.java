package com.mycompany.quizgame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    // MySQL Configurations
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/question?serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "P@$SbazA434"; 

    public static Connection getConnection() throws SQLException {
        // Ta linia połączy Cię z bazą danych, jeśli sterownik jest poprawny
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}
