package com.example.client;

import java.io.PrintWriter;
import java.util.Scanner;

public class ClientGameThread extends Thread {
    private int pos;
    private final int type;
    public int res;
    final Scanner in;
    final PrintWriter out;
    private String msg;

    public ClientGameThread(int pos, int state, Scanner in,PrintWriter out) {
        this.type = state;
        this.pos = pos;
        this.in = in;
        this.out = out;
    }

    public ClientGameThread(int state,String msg,Scanner in,PrintWriter out) {
        this.type = state;
        this.msg = msg;
        this.in = in;
        this.out = out;
    }

    public void send() {
        out.println("playing");
        out.println(pos);
        out.println("EOF");
        System.out.println("Against player's turn");
    }

    public void over(){
        out.println("over");
        out.println(msg);
    }

    public void exit(){
        System.out.println("exit");
        out.println("exit");
        out.println(msg);
        try {
            var s = in.nextLine();
            System.out.println(s);
        } catch (Exception ignored) {
        }
    }

    public void back(){
        System.out.println("get back");
        out.println("remake");
        out.println(msg);
    }

    public void get() {
        try {
            String result, tmp = null;
            while (!"EOF".equals(result = in.nextLine())) {
                tmp = result;
            }
            res = Integer.parseInt(tmp);
            System.out.println("Your turn");
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
