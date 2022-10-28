package com.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.Socket;
import java.util.Arrays;

public class GameController {
    private boolean isCircleTurner = false;
    private static final String CIRCLE_TURNER = "O";
    private static final String CROSS_TURNER = "X";

    // 0 no start; 1 playing; 2 win; 3 lost; 4 even
    
    private int state;
    public boolean isCircle;

    public Socket s;
    private boolean disable = false;
    @FXML
    private Text msg;

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
    int cnt = 0;
    int flag = 0;

    int[][] board = new int[3][3];

    public GameController() {

    }


    private void gameOver() throws Exception {
        ClientGameThread client = new ClientGameThread(flag, false, s);
        client.start();
        System.out.println("game over");
        System.out.println("isCircle:" + isCircle);
        System.out.println(Arrays.deepToString(board));
        String s;
        switch (state){
            case 2 -> s = "win";
            case 3 -> s = "lost";
            case 4 -> s = "even";
            default -> s = "error";
        }
        msg.setText(s);
        msg.setFont(new Font("仿宋", 20));
    }

    public void send() {
        try {
            if (judge()) {
                curr *= 10;
            }
            ClientGameThread client = new ClientGameThread(curr, false, s);
            client.start();
            client.join();
            cnt++;
            get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void control(int c) throws Exception {
        switch (c) {
            case 0 -> {return;}
            case 1 -> onButtonClick1();
            case 2 -> onButtonClick2();
            case 3 -> onButtonClick3();
            case 4 -> onButtonClick4();
            case 5 -> onButtonClick5();
            case 6 -> onButtonClick6();
            case 7 -> onButtonClick7();
            case 8 -> onButtonClick8();
            case 9 -> onButtonClick9();
            default -> {
                flag = c;
                control(c/10);
                judge();
                gameOver();
            }
        }
    }

    public void get() throws Exception {
        disable = true;
        ClientGameThread client = new ClientGameThread(0, true, s);
        client.start();
        client.join();
        System.out.println(ClientGameThread.res);
        control(ClientGameThread.res);
        cnt++;
        disable = false;
    }

    @FXML
    protected void onButtonClick1() {
        btn1.setFont(new Font("微软雅黑", 40));
        if (isCircleTurner) {
            btn1.setText(CIRCLE_TURNER);
            board[0][0] = 1;
        } else {
            btn1.setText(CROSS_TURNER);
            board[0][0] = 2;
        }
        isCircleTurner = !isCircleTurner;
        btn1.setDisable(true);
        System.out.println(1);
        curr = 1;
        if (!disable) {
            send();
        }
    }

    @FXML
    protected void onButtonClick2() {
        btn2.setFont(new Font("微软雅黑", 40));
        if (isCircleTurner) {
            btn2.setText(CIRCLE_TURNER);
            board[0][1] = 1;
        } else {
            btn2.setText(CROSS_TURNER);
            board[0][1] = 2;
        }
        isCircleTurner = !isCircleTurner;
        btn2.setDisable(true);
        System.out.println(2);
        curr = 2;
        if (!disable) {
            send();
        }
    }

    @FXML
    protected void onButtonClick3() {
        btn3.setFont(new Font("微软雅黑", 40));
        if (isCircleTurner) {
            btn3.setText(CIRCLE_TURNER);
            board[0][2] = 1;
        } else {
            btn3.setText(CROSS_TURNER);
            board[0][2] = 2;
        }
        isCircleTurner = !isCircleTurner;
        btn3.setDisable(true);
        System.out.println(3);
        curr = 3;
        if (!disable) {
            send();
        }
    }

    @FXML
    protected void onButtonClick4() {
        btn4.setFont(new Font("微软雅黑", 40));
        if (isCircleTurner) {
            btn4.setText(CIRCLE_TURNER);
            board[1][0] = 1;
        } else {
            btn4.setText(CROSS_TURNER);
            board[1][0] = 2;
        }
        isCircleTurner = !isCircleTurner;
        btn4.setDisable(true);
        System.out.println(4);
        curr = 4;
        if (!disable) {
            send();
        }
    }

    @FXML
    protected void onButtonClick5() {
        btn5.setFont(new Font("微软雅黑", 40));
        if (isCircleTurner) {
            btn5.setText(CIRCLE_TURNER);
            board[1][1] = 1;
        } else {
            btn5.setText(CROSS_TURNER);
            board[1][1] = 2;
        }
        isCircleTurner = !isCircleTurner;
        btn5.setDisable(true);
        System.out.println(5);
        curr = 5;
        if (!disable) {
            send();
        }
    }

    @FXML
    protected void onButtonClick6() {
        btn6.setFont(new Font("微软雅黑", 40));
        if (isCircleTurner) {
            btn6.setText(CIRCLE_TURNER);
            board[1][2] = 1;
        } else {
            btn6.setText(CROSS_TURNER);
            board[1][2] = 2;
        }
        isCircleTurner = !isCircleTurner;
        btn6.setDisable(true);
        System.out.println(6);
        curr = 6;
        if (!disable) {
            send();
        }
    }

    @FXML
    protected void onButtonClick7() {
        btn7.setFont(new Font("微软雅黑", 40));
        if (isCircleTurner) {
            btn7.setText(CIRCLE_TURNER);
            board[2][0] = 1;
        } else {
            btn7.setText(CROSS_TURNER);
            board[2][0] = 2;
        }
        isCircleTurner = !isCircleTurner;
        btn7.setDisable(true);
        System.out.println(7);
        curr = 7;
        if (!disable) {
            send();
        }
    }

    @FXML
    protected void onButtonClick8() {
        btn8.setFont(new Font("微软雅黑", 40));
        if (isCircleTurner) {
            btn8.setText(CIRCLE_TURNER);
            board[2][1] = 1;
        } else {
            btn8.setText(CROSS_TURNER);
            board[2][1] = 2;
        }
        isCircleTurner = !isCircleTurner;
        btn8.setDisable(true);
        System.out.println(8);
        curr = 8;
        if (!disable) {
            send();
        }
    }

    @FXML
    protected void onButtonClick9() {
        btn9.setFont(new Font("微软雅黑", 40));
        if (isCircleTurner) {
            btn9.setText(CIRCLE_TURNER);
            board[2][2] = 1;
        } else {
            btn9.setText(CROSS_TURNER);
            board[2][2] = 2;
        }
        isCircleTurner = !isCircleTurner;
        btn9.setDisable(true);
        System.out.println(9);
        curr = 9;
        if (!disable) {
            send();
        }
    }

    private boolean judge() {
        if (board[0][0] > 0 && board[0][0] == board[0][1] && board[0][1] == board[0][2]) {
            btn1.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn2.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn3.setTextFill(Color.rgb(84, 255, 159, 0.7));
            state = (isCircle && board[0][0] == 1) || (!isCircle && board[0][0] == 2) ? 2 : 3;
            return true;
        } else if (board[1][0] > 0 && board[1][0] == board[1][1] && board[1][1] == board[1][2]) {
            btn4.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn5.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn6.setTextFill(Color.rgb(84, 255, 159, 0.7));
            state = (isCircle && board[1][0] == 1) || (!isCircle && board[1][0] == 2) ? 2 : 3;
            return true;
        } else if (board[2][0] > 0 && board[2][0] == board[2][1] && board[2][1] == board[2][2]) {
            btn7.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn8.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn9.setTextFill(Color.rgb(84, 255, 159, 0.7));
            state = (isCircle && board[2][0] == 1) || (!isCircle && board[2][0] == 2) ? 2 : 3;
            return true;
        } else if (board[0][0] > 0 && board[0][0] == board[1][0] && board[1][0] == board[2][0]) {
            btn1.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn4.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn7.setTextFill(Color.rgb(84, 255, 159, 0.7));
            state = (isCircle && board[0][0] == 1) || (!isCircle && board[0][0] == 2) ? 2 : 3;
            return true;
        } else if (board[0][1] > 0 && board[0][1] == board[1][1] && board[1][1] == board[2][1]) {
            btn2.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn5.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn8.setTextFill(Color.rgb(84, 255, 159, 0.7));
            state = (isCircle && board[0][1] == 1) || (!isCircle && board[0][1] == 2) ? 2 : 3;
            return true;
        } else if (board[0][2] > 0 && board[0][2] == board[1][2] && board[1][2] == board[2][2]) {
            btn3.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn6.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn9.setTextFill(Color.rgb(84, 255, 159, 0.7));
            state = (isCircle && board[0][2] == 1) || (!isCircle && board[0][2] == 2) ? 2 : 3;
            return true;
        } else if (board[0][0] > 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            btn1.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn5.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn9.setTextFill(Color.rgb(84, 255, 159, 0.7));
            state = (isCircle && board[0][0] == 1) || (!isCircle && board[0][0] == 2) ? 2 : 3;
            return true;
        } else if (board[0][2] > 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            btn3.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn5.setTextFill(Color.rgb(84, 255, 159, 0.7));
            btn7.setTextFill(Color.rgb(84, 255, 159, 0.7));
            state = (isCircle && board[0][2] == 1) || (!isCircle && board[0][2] == 2) ? 2 : 3;
            return true;
        } else if (cnt == 9) {
            state = 4;
            return true;
        } else {
            return false;
        }
    }
}
