package com.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public User user;

    @FXML
    Button home;

    @FXML
    Button login;

    @FXML
    Button register;

    @FXML
    Font font = new Font("微软雅黑",15);

    @FXML
    TextField username;

    @FXML
    TextField password;

    @FXML
    protected void login() throws IOException {
        String usn = username.getText();
        String psw = password.getText();
        System.out.println(usn + "|" + psw);
        User user = new User(null, usn, psw);
        boolean login = UserService.login(user);
        this.user = user;
        if(login){
            toHome();
        }
    }

    @FXML
    protected void register() throws IOException {
        var stage = ClientApplication.gameStage;
        stage.close();
        ClientApplication.register(stage);
    }

    @FXML
    protected void toHome() throws IOException {
        var stage = ClientApplication.gameStage;
        stage.close();
        ClientApplication.home(stage,this.user);
    }
}
