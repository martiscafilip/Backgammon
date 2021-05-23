package game;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Random;

public class Dice {
    private ImageView dice1;
    private ImageView dice2;
    private Button rollButton;

    private int valueDice1 = 0;
    private int valueDice2 = 0;
    private int valueDice3 = 0;
    private int valueDice4 = 0;

    private Image face1;
    private Image face2;
    private Image face3;
    private Image face4;
    private Image face5;
    private Image face6;

    private RollAnimation rollAnimation = new RollAnimation();
    public boolean canRoll = true;

    public Dice(ImageView dice1, ImageView dice2, Button rollButton){
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.rollButton = rollButton;

        face1 = getFace(1);
        face2 = getFace(2);
        face3 = getFace(3);
        face4 = getFace(4);
        face5 = getFace(5);
        face6 = getFace(6);

        dice1.setImage(face1);
        dice2.setImage(face2);

        this.rollButton.setOnAction(e -> rollAction());
    }

    private class RollAnimation extends AnimationTimer {

        private long FRAMES_PER_SEC = 20l;
        private long INTERVAL = 1000000000l / FRAMES_PER_SEC;
        private int MAX_ROLLS = 20;

        private Random rand = new Random();
        private long last = 0;
        private int count = 0;
        private int d1;
        private int d2;

        @Override
        public void handle(long now) {
            if (now - last > INTERVAL) {
//                int face = (int) (Math.random() * 6) + 1;
                d1 = rand.nextInt(6) + 1;
                d2 = rand.nextInt(6) + 1;
                changeFace(d1, dice1);
                changeFace(d2, dice2);
                last = now;
                count++;
//                rollButton.setDisable(true);
                if (count > MAX_ROLLS) {
                    rollAnimation.stop();
                    count = 0;
                    canRoll = true;
//                    rollButton.setDisable(false);
                    valueDice1 = d1;
                    valueDice2 = d2;
                    if(d1 == d2){
                        valueDice3 = d1;
                        valueDice4 = d2;
                    }
                }
            }
        }
    }

    public void changeFace(int face, ImageView dice){
        switch (face){
            case 1: dice.setImage(face1);
                break;
            case 2: dice.setImage(face2);
                break;
            case 3: dice.setImage(face3);
                break;
            case 4: dice.setImage(face4);
                break;
            case 5: dice.setImage(face5);
                break;
            case 6: dice.setImage(face6);
                break;
        }
    }

    public void rollAction() {
        if(canRoll) {
            dice1.setOpacity(1);
            dice2.setOpacity(1);
            rollAnimation.start();
            rollButton.setDisable(true);
        }
    }

    public void disableDice(){
        dice1.setOpacity(0.5);
        dice2.setOpacity(0.5);
    }

    public Image getFace(int face){
        File file = new File("src/main/resources/images/Dice" + face + ".png");
        String url = null;
        try {
            url = file.toURI().toURL().toExternalForm();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new Image(url);
    }

    public void canRoll(boolean status) {
        this.rollButton.setDisable(!status);
        this.canRoll = status;
    }

    public void consume(int value){
        if(value == valueDice4) {
            valueDice4 = 0;
            return;
        }

        if(value == valueDice3) {
            valueDice3 = 0;
            return;
        }

        if(value == valueDice2) {
            valueDice2 = 0;
            dice2.setOpacity(0.5);
            return;
        }

        if(value == valueDice1) {
            valueDice1 = 0;
            dice1.setOpacity(0.5);
            return;
        }
    }

    public void consumeAll(){
        this.valueDice1 = 0;
        this.valueDice2 = 0;
        this.valueDice3 = 0;
        this.valueDice4 = 0;
    }

    public boolean canUse(){
        if(valueDice1 == 0 && valueDice2 == 0 && valueDice3 == 0 && valueDice4 == 0){
            return false;
        }
        return true;
    }

    public int getValueDice1() {
        if(valueDice3 != 0)
            return valueDice3;
        return valueDice1;
    }
    public void setValueDice1(int valueDice1) {
        this.valueDice1 = valueDice1;
    }

    public int getValueDice2() {
        if(valueDice4 != 0)
            return valueDice4;
        return valueDice2;
    }
    public void setValueDice2(int valueDice2) {
        this.valueDice2 = valueDice2;
    }

    public Button getRollButton() {
        return rollButton;
    }
    public void setRollButton(Button rollButton) {
        this.rollButton = rollButton;
    }
}
