package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class HomeController {
    public User user;

    @FXML
    Button start;

    @FXML
    Button connect;

    @FXML
    Text info;

    @FXML
    TextField ip;

    @FXML
    TextArea list;

    private boolean isFirst;
    private Socket s;

    @FXML
    void connect() {
        String address = ip.getText();
        s = new Socket();
        try {
            s.connect(new InetSocketAddress(address,8080));
            var in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8);
            var out = new PrintWriter(new OutputStreamWriter(
                    s.getOutputStream(),StandardCharsets.UTF_8),true);
            out.println(InetAddress.getLocalHost());
            out.println(user == null ? "xxx" : user.getUsername());
            while (true){
                String msg = in.nextLine();
                if (msg != null) {
                    System.out.println(msg);
                }
                if("start".equals(msg)) {
                    isFirst = "1".equals(in.nextLine());
                    break;
                }
            }
            System.out.println("game start!");
            startGame();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void startGame() throws IOException, InterruptedException {
        var stage = ClientApplication.gameStage;
        stage.close();
        ClientApplication.startGame(stage,s,isFirst);
        System.out.println("isFirst = "+isFirst);
        System.out.println("server address:"+s);
    }


}
