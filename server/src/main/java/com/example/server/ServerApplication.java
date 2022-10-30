package com.example.server;

import javafx.application.Application;
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
            2,
            2,
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
                    System.out.println("client address:" + incoming);
                    var in = new Scanner(incoming.getInputStream(), StandardCharsets.UTF_8);
                    String clientAddress = in.nextLine();
                    String user = in.nextLine();
                    System.out.println(clientAddress);
                    onlineCount++;
                    String msg = user +
                            (onlineCount % 2 == 0 ? " | cross | " : " | circle | ")
                            + "(waiting)";
                    var newUser = new ClientBean(user, msg, incoming, onlineCount % 2 == 0,in);
                    view.users.put(user, newUser);
                    view.reLoadUserList();
                    executorService.submit(newUser);
                }
            } catch (IOException e) {
                e.printStackTrace();
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
    }


    public static void main(String[] args) {
        launch();
    }
}