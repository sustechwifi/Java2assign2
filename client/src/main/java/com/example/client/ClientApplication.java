package com.example.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {
    static Scene primaryScene;
    public static Stage gameStage;

    @Override
    public void start(Stage stage) throws IOException {

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
    }

    public static void register(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("register.fxml"));
        primaryScene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Register");
        stage.setResizable(true);
        stage.setScene(primaryScene);
        gameStage = stage;
        stage.show();
    }

    public static void home(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("home.fxml"));
        primaryScene = new Scene(fxmlLoader.load(), 600, 450);
        stage.setTitle("Home");
        stage.setResizable(true);
        stage.setScene(primaryScene);
        gameStage = stage;
        stage.show();
    }

    public static void startGame(Stage stage) throws IOException {
        gameStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("client.fxml"));
        primaryScene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Client!");
        stage.setResizable(true);
        stage.setScene(primaryScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}