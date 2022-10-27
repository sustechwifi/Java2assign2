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

public class ClientGameThread extends Thread{
    private final Socket socket;
    private int pos;

    Scanner in;
    PrintWriter out;

    public ClientGameThread(int block) throws IOException {
        this.socket = HomeController.s;
        pos = block;
        in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
        out = new PrintWriter(new OutputStreamWriter(
                socket.getOutputStream(),StandardCharsets.UTF_8),true);
    }


    @Override
    public void run() {
        out.println(pos);
    }
}
