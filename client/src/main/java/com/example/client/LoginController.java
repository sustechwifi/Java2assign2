package com.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LoginController {
    public User user;

    ExecutorService executorService = new ThreadPoolExecutor(
            2,
            2,
            0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(512),
            new ThreadPoolExecutor.DiscardPolicy());

    @FXML
    Button login;

    @FXML
    Button register;

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
        if (!login){
            String msg = "Username no exists or password is wrong!";
            Alert alert = new Alert(Alert.AlertType.ERROR,msg,
                    new ButtonType("confirm", ButtonBar.ButtonData.YES));
            alert.titleProperty().set("inform");
            alert.headerTextProperty().set(msg);
            alert.showAndWait();
            return;
        }
        this.user = user;
        if(connect()){
            toHome();
        }
    }

    @FXML
    TextField ip;

    private Socket s;
    private Scanner in;
    private PrintWriter out;


    boolean connect(){
        if (user == null) {
            return false;
        }
        String address = ip.getText();
        s = new Socket();
        try {
            s.connect(new InetSocketAddress(address, 18080));
            in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8);
            out = new PrintWriter(new OutputStreamWriter(
                    s.getOutputStream(), StandardCharsets.UTF_8), true);
            System.out.println(in.nextLine());
            //Connect to server successfully,please choose a player or click prepare button
            out.println(InetAddress.getLocalHost());
        } catch (IOException e) {
            String msg = "The server is no working!";
            Alert alert = new Alert(Alert.AlertType.WARNING,msg,
                    new ButtonType("confirm", ButtonBar.ButtonData.YES));
            alert.titleProperty().set("inform");
            alert.headerTextProperty().set(msg);
            alert.showAndWait();
            return false;
        }
        out.println(user.getUsername());
        String res = in.nextLine();
        if ("no".contains(res)){
            String msg = "The user already logins!";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,msg,
                    new ButtonType("confirm", ButtonBar.ButtonData.YES));
            alert.titleProperty().set("inform");
            alert.headerTextProperty().set(msg);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    protected void register() throws IOException {
        var stage = ClientApplication.gameStage;
        stage.close();
        ClientApplication.register(stage);
    }

    protected void toHome() throws IOException {
        var stage = ClientApplication.gameStage;
        stage.close();
        ClientApplication.home(stage,this.user,this.in,this.out,this.s);
    }
}
