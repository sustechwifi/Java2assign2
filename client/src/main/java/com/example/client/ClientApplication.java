package com.example.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApplication extends Application {
    static Scene primaryScene;
    static ClientApplication app;
    public static Stage gameStage;

    @Override
    public void start(Stage stage) throws IOException {
        JdbcUtil.getConnection();
        app = this;
        login(stage);
    }

    public static void login(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("login.fxml"));
        primaryScene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Login");
        stage.setResizable(true);
        stage.setScene(primaryScene);
        gameStage = stage;
        stage.show();
        stage.setOnCloseRequest(event -> {
            System.out.print("监听到窗口关闭");
            close();
        });
    }

    public static void register(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("register.fxml"));
        primaryScene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Register");
        stage.setResizable(true);
        stage.setScene(primaryScene);
        gameStage = stage;
        stage.show();
        stage.setOnCloseRequest(event -> {
            System.out.print("监听到窗口关闭");
            close();
        });
    }

    public static void home(Stage stage, User user, Scanner in, PrintWriter out, Socket s) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("home.fxml"));
        primaryScene = new Scene(fxmlLoader.load(), 600, 450);
        stage.setTitle("Home");
        stage.setResizable(true);
        stage.setScene(primaryScene);
        var controller = (HomeController) fxmlLoader.getController();
        if (user != null) {
            user = UserService.getUser(user.getUsername());
            controller.user = user;
            controller.in = in;
            controller.out = out;
            controller.s = s;
            controller.info.setText("username : " + user.getUsername() +
                    "\ntotal win : " + user.getWin_count() +
                    " | total game : " + user.getCount()
            );
            controller.info.setFont(new Font("仿宋", 15));
        }
        gameStage = stage;
        stage.show();
        stage.setOnCloseRequest(event -> {
            System.out.print("监听到窗口关闭");
            close();
        });
    }


    public static void startGame(Stage stage, Socket s, boolean isFirst, User user,Scanner in,PrintWriter out) throws Exception {
        gameStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("client.fxml"));
        primaryScene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Client " + (isFirst ? "X" : "O"));
        stage.setResizable(true);
        stage.setScene(primaryScene);
        var controller = (GameController) fxmlLoader.getController();
        controller.s = s;
        controller.in = in;
        controller.out = out;
        controller.user = user;
        controller.isCircle = !isFirst;
        stage.show();
        if (!isFirst) {
            controller.get();
        }else {
            controller.init();
        }
        stage.setOnCloseRequest(event -> {
            System.out.print("监听到窗口关闭");
            close();
        });
    }

    public static void close() {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}