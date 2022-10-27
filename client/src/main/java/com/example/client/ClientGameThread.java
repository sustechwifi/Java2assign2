package com.example.client;

import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientGameThread extends Thread {
    private final int pos;
    private final boolean type;
    public static int res;
    final Scanner in;
    final PrintWriter out;

    public ClientGameThread(int pos, boolean state, Socket s) throws IOException {
        this.type = state;
        System.out.println("server address:" + s);
        this.pos = pos;
        in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8);
        out = new PrintWriter(new OutputStreamWriter(
                s.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    public void send() {
        System.out.println("send " + pos + " to server");
        out.println(pos);
    }

    public void get() {
        String result = in.nextLine();
        if (result != null) {
            res = Integer.parseInt(result);
        }
        System.out.println("get " + res + " from server");
    }

    @Override
    public void run() {
        if (type) {
            get();
        } else {
            send();
        }
    }
}
