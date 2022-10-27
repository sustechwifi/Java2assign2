package com.example.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class ClientApplication extends Application {
    static Scene primaryScene;
    public static Stage gameStage;

    @Override
    public void start(Stage stage) throws IOException {
        JdbcUtil.getConnection();
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

    public static void home(Stage stage, User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("home.fxml"));
        primaryScene = new Scene(fxmlLoader.load(), 600, 450);
        stage.setTitle("Home");
        stage.setResizable(true);
        stage.setScene(primaryScene);
        var controller = (HomeController)fxmlLoader.getController();
        if(user != null){
            user = UserService.getUser(user.getUsername());
            controller.user = user;
            controller.info.setText("username : "+user.getUsername() +
                                    "\ntotal win : "+user.getWin_count() +
                                    " | total game : "+user.getCount()
            );
            controller.info.setFont(new Font("仿宋",15));
            System.out.println(user);
        }
        gameStage = stage;
        stage.show();
    }


    public static void startGame(Stage stage, Socket s, boolean isFirst) throws IOException, InterruptedException {
        gameStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("client.fxml"));
        primaryScene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Client "+(isFirst ? "X" : "O"));
        stage.setResizable(true);
        stage.setScene(primaryScene);
        var controller = (GameController)fxmlLoader.getController();
        controller.s = s;
        stage.show();
        if (!isFirst){
            controller.get();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}