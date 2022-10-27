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

    private ServerSocket serverSocket;

    class MyThread extends Thread {
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(8080);
                List<Thread> threads = new ArrayList<>();
                while (true) {
                    Socket incoming = serverSocket.accept();
                    var t = new Thread(new ThreadHandler(incoming,threads.size() == 1));
                    threads.add(t);
                    var in = new Scanner(incoming.getInputStream(), StandardCharsets.UTF_8);
                    var out = new PrintWriter(new OutputStreamWriter(
                            incoming.getOutputStream(),StandardCharsets.UTF_8),true);
                    String line = in.nextLine();
                    System.out.println(line);
                    String chess = threads.size() == 1 ? "cross" : "circle";
                    out.println("Hello client,please wait patiently, you hold "+ chess);
                    if(threads.size() == 2){
                        new GameThread(threads.get(0),threads.get(1),incoming).start();
                        threads.clear();
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
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}