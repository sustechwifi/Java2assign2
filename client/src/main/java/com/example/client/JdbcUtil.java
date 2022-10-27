package com.example.client;

import java.sql.*;

public class JdbcUtil {
    private static String DRIVER = "org.postgresql.Driver";
    private static String USERNAME = "postgres";
    private static String PASSWORD = "20030118";
    private static String URL = "jdbc:postgresql://127.0.0.1:5432/game?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatement=true";
    public static Connection connection ;

    public static Connection getConnection(){
        try {
            Class.forName(DRIVER);
            connection =  DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("connect successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void close(Connection connection,
                             PreparedStatement preparedStatement,
                             ResultSet resultSet) {
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement != null ){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}



