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
                .map((k) -> k.getUser() + k.getMsg())
                .toList()
                .forEach(i -> users.append(i).append("\n"));
        users.append("EOF");
        out.println(users);
    }

    public ClientBean(String user, String msg, Socket client, boolean isFirst, Scanner in, PrintWriter out) throws IOException {
        this.isFirst = isFirst;
        this.user = user;
        this.msg = msg;
        this.client = client;
        clientState = 0;
        this.in = in;
        this.out = out;
        System.out.println(in);
        this.out.println("Hello client,please wait patiently");
    }

    @SuppressWarnings("all")
    @Override
    public void run() {
        while (true) {
            String order = in.nextLine();
            switch (order) {
                case "get List" -> sendUserList();
                case "prepare" -> {
                    String user = in.nextLine();
                    ClientBean against = view.users.get(user);
                    against.clientState = 1;
                    against.msg = "(prepared)";
                    view.reLoadUserList();
                }
                case "start" -> {
                    String line = in.nextLine();
                    Optional<ClientBean> first = view.users.values().stream()
                            .filter(i -> i.user.equals(line) && i.clientState == 1)
                            .findFirst();
                    if (first.isPresent()) {
                        var another = first.get();
                        out.println("yes");
                        var against = another.getOut();
                        var againstIn = another.getIn();
                        against.println("connect");
                        against.println("Player[" + this.user + "] want to play with you.");
                        String res = "accept";
                        //res = againstIn.nextLine();
                        System.out.println(res);
                        against.println(res);
                        out.println(res);
                        this.clientState = 2;
                        this.msg = "(playing with "+another.user+")";
                        another.clientState = 2;
                        another.msg = "(playing with "+this.user+")";
                        view.reLoadUserList();
                        try {
                            new ServerGameThread(this.client,first.get().getClient()).start();
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        out.println("Player no exists or no prepares");
                    }
                }
                case "logout" ->{
                    String line = in.nextLine();
                    Optional<ClientBean> first = view.users.values().stream()
                            .filter(i -> i.user.equals(line))
                            .findFirst();
                    view.users.remove(first.get());
                    out.println("finished");
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
