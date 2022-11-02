package com.example.server;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.example.server.ServerApplication.view;

public class ServerGameThread extends Thread {
    private Socket client1;
    private Socket client2;
    private String user1;
    private String user2;

    private final Scanner in1;
    private final PrintWriter out1;
    private final Scanner in2;
    private final PrintWriter out2;

    public ServerGameThread(Socket client1, String user1, Socket client2, String user2) throws IOException {
        this.client1 = client1;
        this.client2 = client2;
        this.user1 = user1;
        this.user2 = user2;
        in1 = new Scanner(client1.getInputStream(), StandardCharsets.UTF_8);
        out1 = new PrintWriter(new OutputStreamWriter(
                client1.getOutputStream(), StandardCharsets.UTF_8), true);
        in2 = new Scanner(client2.getInputStream(), StandardCharsets.UTF_8);
        out2 = new PrintWriter(new OutputStreamWriter(
                client2.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    @SuppressWarnings("all")
    @Override
    public void run() {
        System.out.println("client1 address:" + client1);
        System.out.println("client2 address:" + client2);
        String meg1, meg2;
        while (true) {
            try {
                meg1 = in1.nextLine();
                switch (meg1) {
                    case "playing" -> {
                        System.out.println("===== turn ====");
                        try {
                            while (!"EOF".equals(meg1 = in1.nextLine())) {
                                System.out.println(meg1);
                                out2.println(meg1);
                            }
                            out2.println(meg1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            out2.println("ERROR\nClient dropped\nEOF");
                            String response = "Client 1 (hold cross) is dropped!.";
                            Alert alert = new Alert(Alert.AlertType.ERROR, response,
                                    new ButtonType("accept", ButtonBar.ButtonData.YES));
                            alert.titleProperty().set("inform");
                            alert.headerTextProperty().set(response);
                            alert.showAndWait();
                        }
                    }
                    case "over" -> {
                        String msg = " (" + in1.nextLine()+ ")";
                        view.users.get(user1).setMsg(msg);
                        System.out.println("[game over]" + user1 + " is " +msg);
                        view.reLoadUserList();
                    }
                    case "remake" -> {
                        String line = in1.nextLine();
                        System.out.println("[remake]" + line);
                    }
                    case "exit" -> {
                        String line = in1.nextLine();
                        view.users.remove(this.user1);
                        view.reLoadUserList();
                        System.out.println("[exit]" + line);
                        out1.println("copy that");
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + meg1);
                }
                meg2 = in2.nextLine();
                switch (meg2) {
                    case "playing" -> {
                        try {
                            while (!"EOF".equals(meg2 = in2.nextLine())) {
                                System.out.println(meg2);
                                out1.println(meg2);
                            }
                            out1.println(meg2);
                        } catch (Exception e) {
                            e.printStackTrace();
                            out1.println("ERROR\nClient dropped\nEOF");
                            e.printStackTrace();
                            String response = "Client 2 (hold circle) is dropped!.";
                            Alert alert = new Alert(Alert.AlertType.ERROR, response,
                                    new ButtonType("accept", ButtonBar.ButtonData.YES));
                            alert.titleProperty().set("inform");
                            alert.headerTextProperty().set(response);
                            alert.showAndWait();
                        }
                    }
                    case "over" -> {
                        String msg = " (" + in2.nextLine()+ ")";
                        view.users.get(user2).setMsg(msg);
                        System.out.println("[game over]" + user2 + " is " +msg);
                        view.reLoadUserList();
                    }
                    case "remake" -> {
                        String line = in2.nextLine();
                        System.out.println("[remake]" + line);
                    }
                    case "exit" -> {
                        String line = in2.nextLine();
                        view.users.remove(this.user2);
                        view.reLoadUserList();
                        System.out.println("[exit]" + line);
                        out2.println("copy that");
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + meg2);
                }
            } catch (Exception e) {

            }
        }
    }
}
