package org.example;

import extra.Network;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Game implements Initializable {

    @FXML private ImageView j1;
    @FXML private ImageView j2;
    @FXML private ImageView j3;
    @FXML private Pane table;
    @FXML private AnchorPane board;
    @FXML private AnchorPane bigboard;
    @FXML private Pane p1;
    @FXML private Pane p2;
    @FXML private Pane p3;
    @FXML private Pane p4;
    @FXML private Pane p5;
    @FXML private Pane p6;

    @FXML private ImageView dice1;
    @FXML private ImageView dice2;
    @FXML private Button rollButton;


    private ImageView selected = null;
    private double offset;

    private double positionX;
    private double positionY;
    private double x, y;
    private Pane parent;
    private Roll roll;
    private int dice1Value = 1;
    private int dice2Value = 2;

    private Integer playerNr = 0;
    private PrintWriter out;
    private BufferedReader in;

    private final ArrayList<ImageView> jetons = new ArrayList<>();
    private final ArrayList<Pane> panes = new ArrayList<>();

    @FXML private Label player1;
    @FXML private Label player2;
    @FXML private Text p1star;
    @FXML private Text p2star;

    private final String redstyle= "-fx-background-color: red ;-fx-border-color: black";;
    private final String greenstyle= "-fx-background-color: green ;-fx-border-color: black";

    private class Roll extends AnimationTimer {

        private long FRAMES_PER_SEC = 20l;
        private long INTERVAL = 1000000000l/FRAMES_PER_SEC;
        private int MAX_ROLLS = 20;

        private Random rand = new Random();
        private long last = 0;
        private int count = 0;

        @Override
        public void handle(long now) {
            if(now - last > INTERVAL){
//                int face = (int) (Math.random() * 6) + 1;
                dice1Value = rand.nextInt((6 - 1) + 1) + 1;
                dice2Value = rand.nextInt((6 - 1) + 1) + 1;
                changeFace(dice1Value, dice1);
                changeFace(dice2Value, dice2);
                last = now;
                count++;
                rollButton.setDisable(true);
                if(count > MAX_ROLLS) {
                    roll.stop();
                    count = 0;
                    rollButton.setDisable(false);
                }
            }
        }
    }

    public void back() {
        System.out.println("back");
        if (selected.isMouseTransparent())
            selected.setMouseTransparent(false);
        selected.setLayoutX(positionX);
        selected.setLayoutY(positionY);
        parent.getChildren().add(selected);
        System.out.println(positionX);
        System.out.println(positionY);
        System.out.println(parent.getChildren().size());


        String position = "";
        position = position.concat(parent.getId()).concat("^").concat(selected.getId()).concat("^").concat(String.valueOf(positionX)).concat("^").concat(String.valueOf(positionY));
        out.println(position);

        if (playerNr == 1) {
            player1.setStyle(redstyle);
            player2.setStyle(greenstyle);
        } else {
            player2.setStyle(redstyle);
            player1.setStyle(greenstyle);
        }


        board.setDisable(true);
        selected = null;
        parent = null;
    }

    @FXML private void follow(MouseEvent event) {
        System.out.println("follow");
        ImageView v = (ImageView) event.getTarget();
        offset = v.getFitWidth() / 2;
        parent = (Pane) v.getParent();
        positionX = v.getLayoutX();
        positionY = v.getLayoutY();

        selected = v;
        if (v.getParent() != board) {
            board.getChildren().add(v);
            v.setLayoutX(x - offset);
            v.setLayoutY(y - offset);
        }
        v.setMouseTransparent(true);
    }

    @FXML private void enter(MouseEvent event) {
        System.out.println("enter");
        if (selected != null) {
            positionX = 0;
            positionY = 200;
            parent = (Pane) event.getTarget();
        }
    }

    public ImageView getjeton(String id) {
        for (ImageView imageView : jetons) {
            if (id.equals(imageView.getId()))
                return imageView;
        }
        return null;
    }

    public Pane getpane(String id) {
        for (Pane pane : panes) {
            if (id.equals(pane.getId())) {
                return pane;
            }
        }
        return null;
    }

    @FXML private void changeFace(int face, ImageView dice){
//        int face = (int) (Math.random() * 6) + 1;

        File file = new File("src/main/resources/images/Dice"+face+".png");
        String url = null;
        try {
            url = file.toURI().toURL().toExternalForm();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Image image = new Image(url);
        dice.setImage(image);
    }

    public void rolling() {
//        Random rand = new Random();
//        int randomNum1 = rand.nextInt((6 - 1) + 1) + 1;
//        int randomNum2 = rand.nextInt((6 - 1) + 1) + 1;
//
//
//        System.out.println("random numbers: " + randomNum1 + " " + randomNum2);
        roll.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeFace((int) Math.random()*6+1, dice1);
        changeFace((int) Math.random()*6+1, dice2);
        roll = new Roll();

        jetons.add(j1);
        jetons.add(j2);
        jetons.add(j3);

        panes.add(p1);
        panes.add(p2);
        panes.add(p3);
        panes.add(p4);
        panes.add(p5);
        panes.add(p6);

        try {
            Network network = new Network();
            out = network.getOut();
            in = network.getIn();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response;
        if (playerNr == 0) {
            try {
                response = in.readLine();
                System.out.println(response);
                player1.setStyle(greenstyle);
                player2.setStyle(redstyle);
                if (response.equals("1")) {

                    bigboard.getChildren().remove(p2star);
                    playerNr = 1;

                } else {
                    playerNr = 2;
                    bigboard.getChildren().remove(p1star);
                    board.setDisable(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        new Thread(() -> {
            String response2;
            while (true) {
                try {
                    response2 = in.readLine();
                    String[] parts = response2.split("\\^");

                    if (playerNr == 1) {
                        player1.setStyle(greenstyle);
                        player2.setStyle(redstyle);
                    } else {
                        player1.setStyle(redstyle);
                        player2.setStyle(greenstyle);

                    }
                    board.setDisable(false);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            getjeton(parts[1]).setLayoutX(Double.parseDouble(parts[2]));
                            getjeton(parts[1]).setLayoutY(Double.parseDouble(parts[3]));
                            (getpane(parts[0])).getChildren().add(getjeton(parts[1]));
                        }
                    });


                    System.out.println("Raspuns: " + response2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        positionX = 0;
        positionY = 100;

        board.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                System.out.println(event.getX());
//                System.out.println(event.getY());
                if (selected != null) {
                    selected.setLayoutX(event.getX() - offset);
                    selected.setLayoutY(event.getY() - offset);
                    if ((event.getX() + offset) > board.getWidth() || (event.getY() + offset) > board.getHeight() || (event.getX() + offset) < 0) {
                        back();
                    }
                }
            }
        });

        board.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                System.out.println("handleclicked");
                System.out.println(selected);
                System.out.println(parent);
                x = event.getX();
                y = event.getY();
                if ((event.getTarget() instanceof Pane) && (event.getTarget() != table) && (event.getTarget() != board)) {
                    parent = (Pane) event.getTarget();
                    positionX = 0;
                    positionY = 200 - parent.getChildren().size() * 50;
                }
                if (selected != null) {
                    back();
                }
            }
        });

    }
}


