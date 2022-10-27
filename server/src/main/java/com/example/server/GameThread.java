package com.example.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class GameThread extends Thread{
    private Thread client1;
    private Thread client2;

    Scanner in;
    PrintWriter out;

    public GameThread(Thread client1, Thread client2,Socket socket) throws IOException {
        this.client1 = client1;
        this.client2 = client2;
    }

    @Override
    public void run() {
        client1.start();
        client2.start();
    }
}
