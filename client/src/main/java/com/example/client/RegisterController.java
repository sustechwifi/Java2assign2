package com.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    protected void register() {
        String usn = username.getText();
        String psw = password.getText();
        if(!confirm.getText().equals(psw)){
            String msg = "The two password are no equal!";
            Alert alert = new Alert(Alert.AlertType.ERROR,msg,
                    new ButtonType("confirm", ButtonBar.ButtonData.YES));
            alert.titleProperty().set("inform");
            alert.headerTextProperty().set(msg);
            alert.showAndWait();
            return;
        }
        System.out.println(usn + "|" + psw);
        if(UserService.checkName(usn)){
            UserService.register(new User(null,usn,psw));
            String msg = "Register successfully!";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,msg,
                    new ButtonType("confirm", ButtonBar.ButtonData.YES));
            alert.titleProperty().set("inform");
            alert.headerTextProperty().set(msg);
            alert.showAndWait();
        }else {
            String msg = "The username is always used!";
            Alert alert = new Alert(Alert.AlertType.WARNING,msg,
                    new ButtonType("confirm", ButtonBar.ButtonData.YES));
            alert.titleProperty().set("inform");
            alert.headerTextProperty().set(msg);
            alert.showAndWait();
            return;
        }
    }

    @FXML
    protected void back() throws IOException {
        var stage = ClientApplication.gameStage;
        stage.close();
        ClientApplication.login(stage);
    }
}
