package com.example.client;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

public class ClientGameThread extends Thread {
    private final int pos;
    private final boolean type;
    public int res;
    private GameController controller;
    final Scanner in;
    final PrintWriter out;

    public ClientGameThread(int pos, boolean state, Socket s, GameController gameController) throws IOException {
        this.type = state;
        this.controller = gameController;
        System.out.println("server address:" + s);
        this.pos = pos;
        in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8);
        out = new PrintWriter(new OutputStreamWriter(
                s.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    public void send() throws IOException{
        System.out.println("send " + pos + " to server");
        out.println(pos);
        out.println("EOF");
    }

    public void get() {
        try {
            String result, tmp = null;
            while (!"EOF".equals(result = in.nextLine())) {
                tmp = result;
            }
            res = Integer.parseInt(tmp);
            System.out.println("get " + res + " from server");
        } catch (NumberFormatException e) {
            res = -1;
        } catch (Exception e) {
            res = -2;
        }
    }

    @Override
    public void run() {
        try {
            if (type) {
                get();
            } else {
                send();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
