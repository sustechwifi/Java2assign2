package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HomeController {
    @FXML
    Button start;

    @FXML
    Button connect;

    @FXML
    TextField ip;

    public static Socket s;

    private boolean isFirst;

    @FXML
    void connect() throws Exception {
        String address = ip.getText();
        s = new Socket();
        try {
            s.connect(new InetSocketAddress(address,8080));
            var in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8);
            var out = new PrintWriter(new OutputStreamWriter(
                    s.getOutputStream(),StandardCharsets.UTF_8),true);
            out.println(InetAddress.getLocalHost());
            while (true){
                Thread.sleep(200);
                String msg = in.nextLine();
                if("start".equals(msg)) {
                    isFirst = "1".equals(in.nextLine());
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("game start!");
        startGame();
    }

    @FXML
    void startGame() throws IOException {
        var stage = ClientApplication.gameStage;
        stage.close();
        ClientApplication.startGame(stage);
        System.out.println(isFirst);
    }


}
