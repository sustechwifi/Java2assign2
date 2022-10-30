package com.example.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

import static com.example.server.ServerApplication.view;

public class ClientBean extends Thread {
    private String user;
    private String msg;

    /**
     * state
     * 0 waiting; 1 prepared; 2 playing; 3 finished; 4 error
     */
    private int clientState;
    private Socket client;
    private final Scanner in;
    private final PrintWriter out;
    private boolean isFirst;

    public void sendUserList() {
        StringBuilder users = new StringBuilder();
        view.users.values()
                .stream()
                .map(ClientBean::getMsg)
                .toList()
                .forEach(i -> users.append(i).append("\n"));
        users.append("EOF");
        out.println(users);
    }

    public void changeUserState(String user, String msg) {
        if (view.users.get(user) != null) {
            view.users.get(user).msg = msg;
        }
    }

    public ClientBean(String user, String msg, Socket client, boolean isFirst, Scanner in) throws IOException {
        this.isFirst = isFirst;
        this.user = user;
        this.msg = msg;
        this.client = client;
        clientState = 0;
        this.in = in;
        System.out.println(in);
        out = new PrintWriter(new OutputStreamWriter(
                client.getOutputStream(), StandardCharsets.UTF_8), true);
        String chess = isFirst ? "cross" : "circle";
        out.println("Hello client,please wait patiently, you hold " + chess);
    }

    public void startGame(ClientBean against) throws IOException {
        changeUserState(against.user, " playing with" + this.user);
        changeUserState(this.user, " playing with" + against.user);
        view.reLoadUserList();
        new ServerGameThread(this.client, against.client).start();
    }


    @Override
    public void run() {
        while (true) {
            String order = in.nextLine();
            switch (order) {
                case "get List" -> sendUserList();
                case "start" -> {
                    String line = in.nextLine();
                    Optional<ClientBean> first = view.users.values().stream().filter(i -> line.contains(i.user)).findFirst();
                    if (first.isPresent()) {
                        var against = first.get().getOut();
                        var againstIn = first.get().getIn();
                        against.println("connect");
                        against.println("Player[" + this.user + "] want to play with you.");

                        String res = "accept";
                                //againstIn.nextLine();
                        System.out.println(res);
                        against.println(res);
                        out.println(res);
                    } else {
                        out.println("Wrong player name");
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + order);
            }
        }
    }

    public String getUser() {
        return user;
    }

    public String getMsg() {
        return msg;
    }

    public int getClientState() {
        return clientState;
    }

    public Socket getClient() {
        return client;
    }

    public Scanner getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public boolean isFirst() {
        return isFirst;
    }
}
