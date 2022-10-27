package com.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class GameController {
    private boolean isCircleTurner = false;
    private static final String CIRCLE_TURNER = "O";
    private static final String CROSS_TURNER = "X";
    @FXML
    private Label welcomeText;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button btn6;

    @FXML
    private Button btn7;

    @FXML
    private Button btn8;

    @FXML
    private Button btn9;

    int curr = 0;

    private void pass(){

    }

    @FXML
    protected void onButtonClick1() {
        btn1.setFont(new Font("微软雅黑",40));
        if (isCircleTurner) {
            btn1.setText(CIRCLE_TURNER);
        }else{
            btn1.setText(CROSS_TURNER);
        }
        isCircleTurner = !isCircleTurner;
        btn1.setDisable(true);
        System.out.println(1);
    }
    @FXML
    protected void onButtonClick2() {
        btn2.setFont(new Font("微软雅黑",40));
        if (isCircleTurner) {
            btn2.setText(CIRCLE_TURNER);
        }else{
            btn2.setText(CROSS_TURNER);
        }
        isCircleTurner = !isCircleTurner;
        btn2.setDisable(true);
       System.out.println(2);
    }
    @FXML
    protected void onButtonClick3() {
        btn3.setFont(new Font("微软雅黑",40));
        if (isCircleTurner) {
            btn3.setText(CIRCLE_TURNER);
        }else{
            btn3.setText(CROSS_TURNER);
        }
        isCircleTurner = !isCircleTurner;
        btn3.setDisable(true);
       System.out.println(3);
    }
    @FXML
    protected void onButtonClick4() {
        btn4.setFont(new Font("微软雅黑",40));
        if (isCircleTurner) {
            btn4.setText(CIRCLE_TURNER);
        }else{
            btn4.setText(CROSS_TURNER);
        }
        isCircleTurner = !isCircleTurner;
        btn4.setDisable(true);
       System.out.println(4);
    }

    @FXML
    protected void onButtonClick5() {
        btn5.setFont(new Font("微软雅黑",40));
        if (isCircleTurner) {
            btn5.setText(CIRCLE_TURNER);
        }else{
            btn5.setText(CROSS_TURNER);
        }
        isCircleTurner = !isCircleTurner;
        btn5.setDisable(true);
        System.out.println(5);
    }
    @FXML
    protected void onButtonClick6() {
        btn6.setFont(new Font("微软雅黑",40));
        if (isCircleTurner) {
            btn6.setText(CIRCLE_TURNER);
        }else{
            btn6.setText(CROSS_TURNER);
        }
        isCircleTurner = !isCircleTurner;
        btn6.setDisable(true);
       System.out.println(6);
    }

    @FXML
    protected void onButtonClick7() {
        btn7.setFont(new Font("微软雅黑",40));
        if (isCircleTurner) {
            btn7.setText(CIRCLE_TURNER);
        }else{
            btn7.setText(CROSS_TURNER);
        }
        isCircleTurner = !isCircleTurner;
        btn7.setDisable(true);
       System.out.println(7);
    }

    @FXML
    protected void onButtonClick8() {
        btn8.setFont(new Font("微软雅黑",40));
        if (isCircleTurner) {
            btn8.setText(CIRCLE_TURNER);
        }else{
            btn8.setText(CROSS_TURNER);
        }
        isCircleTurner = !isCircleTurner;
        btn8.setDisable(true);
       System.out.println(8);
    }

    @FXML
    protected void onButtonClick9() {
        btn9.setFont(new Font("微软雅黑",40));
        if (isCircleTurner) {
            btn9.setText(CIRCLE_TURNER);
        }else{
            btn9.setText(CROSS_TURNER);
        }
        isCircleTurner = !isCircleTurner;
        btn9.setDisable(true);
       System.out.println(9);
    }
    
}