package com.example.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ThreadHandler implements Runnable{
    Socket socket;
    boolean isFirst;
    Scanner in;
    PrintWriter out;

    public ThreadHandler(Socket socket,boolean isFirst) {
        this.socket = socket;
        this.isFirst = isFirst;
    }

    @Override
    public void run() {
        try {
            in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
            out = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream(),StandardCharsets.UTF_8),true);
            out.println("start");
            out.println(isFirst ? "1":"0");
            String line;
            while ((line = in.nextLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
