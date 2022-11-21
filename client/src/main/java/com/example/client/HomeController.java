package com.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    Button logout;

    public Socket s;
    public Scanner in;
    public PrintWriter out;

    @FXML
    Text info;



    @FXML
    TextArea list;

    @FXML
    Button chooseUser;

    @FXML
    TextField selectUser;

    @FXML
    Button setPrepared;

    private boolean isFirst;


    @FXML
    public void prepare() throws Exception {
        out.println("prepare");
        out.println(this.user.getUsername());
        String msg = in.nextLine();
        if ("connect".equals(msg)) {
            msg = in.nextLine();
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
                isFirst = false;
                startGame();
            }else {
                out.println("reject");
                res = "You have rejected";
            }
        }
    }

    @FXML
    void chooseOne() throws Exception {
        String against = selectUser.getText();
        StringBuilder msg = new StringBuilder();
        msg.append("start\n").append(against);
        out.println(msg);
        String response = in.nextLine();
        if ("yes".equals(response)){
            String res = in.nextLine();
            isFirst = true;
            startGame();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,response,
                    new ButtonType("accept", ButtonBar.ButtonData.YES));
            alert.titleProperty().set("inform");
            alert.headerTextProperty().set(response);
            alert.showAndWait();
        }
    }

    @FXML
    void logout(){
        if (s != null) {
            out.println("logout");
            out.println(this.user.getUsername());
            String s = in.nextLine();
            System.out.println(s);
            ClientApplication.close();
            System.exit(0);
        }
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
        ClientApplication.startGame(stage, s, isFirst,this.user,in,out);
        System.out.println("isFirst = " + isFirst);
        System.out.println("server address:" + s);
    }


}
