package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.*;

public class HomeController {
    public User user;
    ExecutorService executorService = new ThreadPoolExecutor(
            2,
            2,
            0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(512),
            new ThreadPoolExecutor.DiscardPolicy());

    @FXML
    Button start;

    @FXML
    Button getList;

    @FXML
    Button connect;

    @FXML
    Text info;

    @FXML
    TextField ip;

    @FXML
    TextArea list;

    @FXML
    Button chooseUser;

    @FXML
    TextField selectUser;

    @FXML
    Button setPrepared;

    private boolean isFirst;
    private Socket s;
    private Scanner in;
    private PrintWriter out;

    @FXML
    void connect() throws IOException {
        String address = ip.getText();
        s = new Socket();
        s.connect(new InetSocketAddress(address, 18080));
        in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8);
        out = new PrintWriter(new OutputStreamWriter(
                s.getOutputStream(), StandardCharsets.UTF_8), true);
        out.println(InetAddress.getLocalHost());
        out.println(user == null ? "xxx" : user.getUsername());
        Runnable r = () -> {
            try {
                String msg = in.nextLine();
                System.out.println(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        executorService.submit(r);
    }

    public void prepare() throws Exception {
        String msg = in.nextLine();
        System.out.println(msg);
        if ("connect".equals(msg)) {
            msg = in.nextLine();
            System.out.println(msg);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,msg,
                    new ButtonType("reject", ButtonBar.ButtonData.NO),
                    new ButtonType("accept", ButtonBar.ButtonData.YES));
            alert.titleProperty().set("inform");
            alert.headerTextProperty().set(msg);
            Optional<ButtonType> buttonType = alert.showAndWait();
            String res;
            if (buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)){
                out.println("accept");
                res = in.nextLine();
                System.out.println("game start");
                //startGame();
            }else {
                out.println("reject");
                res = "You have rejected";
            }
            System.out.println(res);
        }
    }

    @FXML
    void chooseOne(){
        String against = selectUser.getText();
        StringBuilder msg = new StringBuilder();
        msg.append("start\n").append(against);
        out.println(msg);
        String res = in.nextLine();
        System.out.println(res);
        System.out.println("game start");
        //startGame();

    }

    @FXML
    void getUserList() {
        if (s != null){
            out.println("get List");
            Runnable run = () -> {
                StringBuilder users = new StringBuilder();
                String line;
                while(!"EOF".equals(line = in.nextLine())){
                    if (line.contains(this.user.getUsername())) {
                        continue;
                    }
                    users.append(line).append("\n");
                }
                list.setText(users.toString());
            };
            executorService.submit(run);
        }
    }

    @FXML
    void startGame() throws Exception {
        var stage = ClientApplication.gameStage;
        stage.close();
        ClientApplication.startGame(stage, s, isFirst);
        System.out.println("isFirst = " + isFirst);
        System.out.println("server address:" + s);
    }


}
