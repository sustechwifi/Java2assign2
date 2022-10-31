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

public class ServerGameThread extends Thread {
    private Socket client1;
    private Socket client2;

    private final Scanner in1;
    private final PrintWriter out1;
    private final Scanner in2;
    private final PrintWriter out2;

    public ServerGameThread(Socket client1, Socket client2) throws IOException {
        this.client1 = client1;
        this.client2 = client2;
        in1 = new Scanner(client1.getInputStream(), StandardCharsets.UTF_8);
        out1 = new PrintWriter(new OutputStreamWriter(
                client1.getOutputStream(), StandardCharsets.UTF_8), true);
        in2 = new Scanner(client2.getInputStream(), StandardCharsets.UTF_8);
        out2 = new PrintWriter(new OutputStreamWriter(
                client2.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    @Override
    public void run() {
        System.out.println("client1 address:" + client1);
        System.out.println("client2 address:" + client2);
        String meg1, meg2;
        while (true) {
            try {
                while(!"EOF".equals(meg1 = in1.nextLine())){
                    System.out.println(meg1);
                    out2.println(meg1);
                }
                out2.println(meg1);
            } catch (Exception e) {
                e.printStackTrace();
                out2.println("ERROR\nClient dropped\nEOF");
                String response = "Client 1 (hold cross) is dropped!.";
                Alert alert = new Alert(Alert.AlertType.ERROR,response,
                        new ButtonType("accept", ButtonBar.ButtonData.YES));
                alert.titleProperty().set("inform");
                alert.headerTextProperty().set(response);
                alert.showAndWait();
            }
            try {
                while(!"EOF".equals(meg2 = in2.nextLine())){
                    System.out.println(meg2);
                    out1.println(meg2);
                }
                out1.println(meg2);
            } catch (Exception e) {
                e.printStackTrace();
                out1.println("ERROR\nClient dropped\nEOF");
                e.printStackTrace();
                String response = "Client 2 (hold circle) is dropped!.";
                Alert alert = new Alert(Alert.AlertType.ERROR,response,
                        new ButtonType("accept", ButtonBar.ButtonData.YES));
                alert.titleProperty().set("inform");
                alert.headerTextProperty().set(response);
                alert.showAndWait();
            }
        }
    }
}
