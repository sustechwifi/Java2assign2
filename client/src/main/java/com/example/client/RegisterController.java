package com.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.io.IOException;

public class RegisterController {
    @FXML
    Button login;

    @FXML
    Button register;

    @FXML
    TextField username;

    @FXML
    TextField password;

    @FXML
    TextField confirm;

    @FXML
    protected void register() throws IOException {
        String usn = username.getText();
        String psw = password.getText();
        if(!confirm.getText().equals(psw)){
            return;
        }
        System.out.println(usn + "|" + psw);
        if(UserService.checkName(usn)){
            UserService.register(new User(null,usn,psw));
        }
    }

    @FXML
    protected void back() throws IOException {
        var stage = ClientApplication.gameStage;
        stage.close();
        ClientApplication.login(stage);
    }
}
