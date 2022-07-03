package branch1;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    @FXML
    private Label gameText;

    private int playerTurn = 0;

    private int buttonCounter = 0;

    ArrayList<Button> buttons;

    @FXML
    void initialize(){
        buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4, button5, button6,
                button7, button8, button9));
        buttons.forEach(this::mouseClicked);
        buttons.forEach(this::keyPressed);
        introAnimation();
    }

    @FXML
    void fadeIn(){
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), gameText);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    @FXML
    void fadeOut(){
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), gameText);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }

    public void introAnimation(){

        KeyValue start = new KeyValue(gameText.opacityProperty(),1);
        KeyFrame startKF = new KeyFrame(Duration.millis(2000), start);

        KeyValue mid = new KeyValue(gameText.opacityProperty(), 0);
        KeyFrame midKF = new KeyFrame(Duration.millis(2300),
                mid);

        KeyFrame endKF = new KeyFrame(Duration.millis(2300),
                (e) -> {setGameText();});

        Timeline tl = new Timeline(startKF, midKF, endKF);
        tl.play();
    }

    public void moveDone(Button button){
        setSymbol(button);
        button.setDisable(true);
        buttonCounter++;
        setGameText();
        checkIfGameIsOver();
    }

    public void setSymbol(Button button){
        if(playerTurn %2 == 0) {
            button.setText("X");
            playerTurn = 1;
        } else {
            button.setText("O");
            playerTurn = 0;
        }
    }

    public void mouseClicked(Button button){
        button.setOnMouseClicked((e) -> {
            moveDone(button);}
        );
    }

    public void keyPressed(Button button){
        button.setOnKeyPressed(
                (e) -> {
                    switch(e.getCode()){
                        case DIGIT1 -> moveDone(button1);
                        case DIGIT2 -> moveDone(button2);
                        case DIGIT3 -> moveDone(button3);
                        case DIGIT4 -> moveDone(button4);
                        case DIGIT5 -> moveDone(button5);
                        case DIGIT6 -> moveDone(button6);
                        case DIGIT7 -> moveDone(button7);
                        case DIGIT8 -> moveDone(button8);
                        case DIGIT9 -> moveDone(button9);
                    }
                }
        );
    }

    @FXML
    public void setRestart(){
        buttons.forEach(button -> {
            button.setDisable(false);
            button.setText("");
        });
        gameText.setText("Tic-Tac-Toe");
        introAnimation();
        buttonCounter = 0;
    }

    void setGameText(){
        if(playerTurn %2 == 0) {
            fadeOut();
            gameText.setText("Player X turn");
            fadeIn();
        }
        else{
            fadeOut();
            gameText.setText("Player O turn");
            fadeIn();
        }
    }

    public void checkIfGameIsOver(){
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button7.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> null;
            };

            if (line.equals("XXX")) {
                gameText.setText("Player X wins!");
                buttons.forEach((button -> button.setDisable(true)));
            }
            else if (line.equals("OOO")) {
                gameText.setText("Player O wins!");
                buttons.forEach((button) -> button.setDisable(true));
            }
            else if (buttonCounter == 9){
                gameText.setText("It's a tie!");
            }
        }
    }
}
