package com.example.server;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConnectController {
    @FXML
    private Label welcomeText;

    public List<String> users = new ArrayList<>();

    @FXML
    private TextArea list;

    public void reLoadUserList(){
        StringBuilder sb = new StringBuilder();
        for (String user : users) {
            sb.append(user).append(" \n");
        }
        list.setText(sb.toString());
    }


}