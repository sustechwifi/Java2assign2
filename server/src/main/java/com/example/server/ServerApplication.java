package com.example.server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ServerApplication extends Application {
    public static ViewController view;
    public int onlineCount = 0;
    private ServerSocket serverSocket;

    ExecutorService executorService = new ThreadPoolExecutor(
            50,
            50,
            0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(512),
            new ThreadPoolExecutor.DiscardPolicy());

    class MyThread extends Thread {
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(18080);
                while (true) {
                    Socket incoming = serverSocket.accept();
                    System.out.println("welcome client:" + incoming);
                    var in = new Scanner(incoming.getInputStream(), StandardCharsets.UTF_8);
                    var out = new PrintWriter(new OutputStreamWriter(
                            incoming.getOutputStream(), StandardCharsets.UTF_8), true);
                    out.println("Connect to server successfully,please choose a player or click prepare button");
                    String clientAddress = in.nextLine();
                    String user = in.nextLine();
                    String response = "yes";
                    if (view.users.containsKey(user)){
                        response = "no";
                    }
                    out.println(response);
                    onlineCount++;
                    String msg = "(waiting)";
                    var newUser = new ClientBean(user, msg, incoming, (onlineCount % 2 == 0),in,out);
                    view.users.put(user, newUser);
                    view.reLoadUserList();
                    executorService.submit(newUser);
                }
            } catch (IOException e) {

            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        initWindow(stage);
        new MyThread().start();
    }

    public void initWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerApplication.class.getResource("connect.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Server");
        stage.setScene(scene);
        view = fxmlLoader.getController();
        stage.show();
        stage.setOnCloseRequest(event -> {
            System.out.print("监听到窗口关闭");
            Platform.exit();
            System.exit(0);
        });
    }


    public static void main(String[] args) {
        launch();
    }
}