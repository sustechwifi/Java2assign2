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
    private int pos;
    private final int type;
    public int res;
    final Scanner in;
    final PrintWriter out;
    private String msg;

    public ClientGameThread(int pos, int state, Scanner in,PrintWriter out) throws IOException {
        this.type = state;
        this.pos = pos;
        this.in = in;
        this.out = out;
    }

    public ClientGameThread(int state,String msg,Scanner in,PrintWriter out) throws IOException {
        this.type = state;
        this.msg = msg;
        this.in = in;
        this.out = out;
    }

    public void send() {
        System.out.println("send " + pos + " to server");
        out.println("playing");
        out.println(pos);
        out.println("EOF");
    }

    public void over(){
        out.println("over");
        out.println(msg);
    }

    public void exit(){

    }

    public void back(){
        System.out.println("get back");
        out.println("remake");
        out.println(msg);
    }

    public void get() {
        try {
            String result, tmp = null;
            System.out.println(in);
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
            switch (this.type){
                case 1 -> get();
                case 2 -> send();
                case 3 -> over();
                case 4 -> exit();
                case 5 -> back();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
