package com.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
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
    protected void login() {
        String usn = username.getText();
        String psw = password.getText();
        System.out.println(usn + "|" + psw);
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
        ClientApplication.home(stage);
    }
}
