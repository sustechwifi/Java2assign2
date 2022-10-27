package com.example.client;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    public static boolean login(User user) {
        Connection connection = JdbcUtil.connection;
        String sql = "select username, password from game.public.user where username = ? and password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("login successfully");
                return true;
            } else {
                System.out.println("username or password is incorrect");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean checkName(String username) {
        Connection connection = JdbcUtil.connection;
        String sql = "select id from game.public.user where username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean register(User user) {
        Connection connection = JdbcUtil.connection;
        String sql = "insert into game.public.user (username, password) values(?,?)";
        try {
            if (!checkName(user.getUsername())) {
                System.out.println("the username is used");
                return false;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            System.out.println("register successfully");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User getUser(String username) {
        Connection connection = JdbcUtil.connection;
        String sql = "select * from game.public.user where username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet r = preparedStatement.executeQuery();
            r.next();
            return new User(
                    r.getInt(1), r.getString(2),
                    r.getString(3), r.getInt(4),
                    r.getInt(5)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

