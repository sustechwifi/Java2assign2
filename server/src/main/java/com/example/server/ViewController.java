package com.example.server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.*;

public class ViewController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button btn;

    public Map<String,ClientBean> users = new HashMap<>();


    @FXML
    private TextArea list;

    public void reLoadUserList(){
        StringBuilder sb = new StringBuilder();
        for (String user : users.keySet()) {
            ClientBean u = users.get(user);
            sb.append(u.getUser()).append(u.getMsg()).append(" \n");
        }
        list.setText(sb.toString());
    }

    public void getIp() throws UnknownHostException {
        welcomeText.setText(String.valueOf(Inet4Address.getLocalHost()));
    }


}