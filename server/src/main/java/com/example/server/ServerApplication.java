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


public class ServerApplication extends Application {
    public static ConnectController view;
    private ServerSocket serverSocket;

    class MyThread extends Thread {
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(8080);
                List<Socket> clients = new ArrayList<>();
                while (true) {
                    Socket incoming = serverSocket.accept();
                    clients.add(incoming);
                    System.out.println("client address:"+incoming);
                    var in = new Scanner(incoming.getInputStream(), StandardCharsets.UTF_8);
                    var out = new PrintWriter(new OutputStreamWriter(
                            incoming.getOutputStream(),StandardCharsets.UTF_8),true);
                    String line = in.nextLine();
                    System.out.println(line);
                    view.users.add(in.nextLine());
                    view.reLoadUserList();
                    String chess = clients.size() == 1 ? "cross" : "circle";
                    out.println("Hello client,please wait patiently, you hold "+ chess);
                    if(clients.size() == 2){
                        new ServerGameThread(clients.get(0),clients.get(1)).start();
                        clients.clear();
                    }
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
        stage.setTitle("Hello!");
        stage.setScene(scene);
        view = fxmlLoader.getController();
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}