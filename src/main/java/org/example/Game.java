package org.example;

import extra.Network;
import extra.Player1;
import extra.Player2;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class Game implements Initializable {

    @FXML
    private ImageView j1, j2, j3, j4, j5, j6, j7, j8, j9, j10;
    @FXML
    private ImageView j11, j12, j13, j14, j15, j16, j17, j18, j19, j20;
    @FXML
    private ImageView j21, j22, j23, j24, j25, j26, j27, j28, j29, j30;
    @FXML
    private Pane table;
    @FXML
    private AnchorPane board, bigboard;
    @FXML
    private Pane p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12;
    @FXML
    private Pane p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24;
    @FXML
    private ImageView dice1, dice2;
    @FXML
    private Button rollButton;
    @FXML
    private Label player1, player2;
    @FXML
    private Text p1star, p2star;
    @FXML
    private Pane middleBlack, middleWhite;


    private ImageView selected = null;
    private double offset;

    private double positionX, positionY;
    private double positionXX, positionYY;
    private double x, y;
    private Pane parent, parent2;
    private Roll roll;
    private Integer dice1Value = 1, dice2Value = 2;
    private Integer dice3Value = 0, dice4Value = 0;
    private int rollValidator = 0;

    private Integer playerNr = 0;
    private PrintWriter out;
    private BufferedReader in;

    private final ArrayList<ImageView> jetons = new ArrayList<>();
    private final ArrayList<Pane> panes = new ArrayList<>();

    private Player1 first;
    private Player2 second;

    private final String redstyle = "-fx-background-color: red ;-fx-border-color: black";
    private final String greenstyle = "-fx-background-color: green ;-fx-border-color: black";

    private Pane high1;
    private Pane high2;
    private String position = "";
    private Boolean moveFromMiddle;
    private Integer middleWhiteCoordonate = 150;
    private Integer middleBlackCoordonate = 150;


    private class Roll extends AnimationTimer {

        private long FRAMES_PER_SEC = 20l;
        private long INTERVAL = 1000000000l / FRAMES_PER_SEC;
        private int MAX_ROLLS = 20;

        private Random rand = new Random();
        private long last = 0;
        private int count = 0;

        @Override
        public void handle(long now) {
            if (now - last > INTERVAL) {
//                int face = (int) (Math.random() * 6) + 1;
                dice1Value = rand.nextInt((6 - 1) + 1) + 1;
                dice2Value = rand.nextInt((6 - 1) + 1) + 1;
                changeFace(dice1Value, dice1);
                changeFace(dice2Value, dice2);
                last = now;
                count++;
                rollButton.setDisable(true);
                if (count > MAX_ROLLS) {
                    roll.stop();
                    count = 0;
                    rollButton.setDisable(false);
                    if (dice1Value == dice2Value) {
                        dice3Value = dice1Value;
                        dice4Value = dice1Value;
                    }
                }
            }
        }
    }

    public void back() {
        System.out.println("back");

        if (moveFromMiddle) {
            if (playerNr == 2) {
                if ((Integer.parseInt(parent.getId().substring(1)) != dice1Value
                        && Integer.parseInt(parent.getId().substring(1)) != dice2Value)
                        || !second.validateStack(parent)) {
                    if (selected.isMouseTransparent()) {
                        selected.setMouseTransparent(false);
                        second.setTransparent(false);
                    }
                    selected.setLayoutX(positionXX);
                    selected.setLayoutY(positionYY);
                    parent2.getChildren().add(selected);
                    clearBorder();

                    if (getOutFromMiddle()) {
                        selected = null;
                        parent = null;
                        return;
                    }
                }
            } else {
                if ((Integer.parseInt(parent.getId().substring(1)) != (25 - dice1Value)
                        && Integer.parseInt(parent.getId().substring(1)) != (25 - dice2Value))
                        || (!first.validateStack(parent))) {
                    if (selected.isMouseTransparent()) {
                        selected.setMouseTransparent(false);
                        second.setTransparent(false);
                    }
                    selected.setLayoutX(positionXX);
                    selected.setLayoutY(positionYY);
                    parent2.getChildren().add(selected);
                    clearBorder();

                    if (getOutFromMiddle()) {
                        selected = null;
                        parent = null;
                        return;
                    }
                }
            }
        } else if (playerNr == 2) {
            if ((Integer.parseInt(parent.getId().substring(1)) - Integer.parseInt(parent2.getId().substring(1)) != dice1Value
                    && Integer.parseInt(parent.getId().substring(1)) - Integer.parseInt(parent2.getId().substring(1)) != dice2Value)
                    || !second.validateStack(parent)) {

                if (getOutFromMiddle()) {
                    selected = null;
                    parent = null;
                    return;
                }

                if (selected.isMouseTransparent()) {
                    selected.setMouseTransparent(false);
                    second.setTransparent(false);
                }
                selected.setLayoutX(positionXX);
                selected.setLayoutY(positionYY);

                if (parent2.getChildren().size() > 4)
                    overlap(parent2, false);
                parent2.getChildren().add(selected);
                clearBorder();

//                overlap(parent, false);
                selected = null;
                parent = null;
                return;
            }

        } else {
            if ((Integer.parseInt(parent2.getId().substring(1)) - Integer.parseInt(parent.getId().substring(1)) != dice1Value
                    && Integer.parseInt(parent2.getId().substring(1)) - Integer.parseInt(parent.getId().substring(1)) != dice2Value)
                    || !first.validateStack(parent)) {
                if (getOutFromMiddle()) {
                    selected = null;
                    parent = null;
                    return;
                }
                if (selected.isMouseTransparent()) {
                    selected.setMouseTransparent(false);
                    first.setTransparent(false);
                }
                selected.setLayoutX(positionXX);
                selected.setLayoutY(positionYY);

                if (parent2.getChildren().size() > 4)
                    overlap(parent2, false);
                parent2.getChildren().add(selected);

                clearBorder();

//                overlap(parent, false);
                selected = null;
                parent = null;
                return;
            }

        }

        if (selected.isMouseTransparent()) {
            selected.setMouseTransparent(false);
            first.setTransparent(false);
            second.setTransparent(false);
        }

        selected.setLayoutX(positionX);
        selected.setLayoutY(positionY);

        overlap(parent, false);

        try {
            parent.getChildren().add(selected);
        } catch (Exception e) {
            System.out.println("Selected exception");
        }

        clearBorder();                                        //clear the border of the spikes that were drown

        diceValueChange();                                    //based on the move of the player sets to 0 the dice that has been used

        finalSend();                                          //sends the message to the other player if both dice were used
        //checks if if he hasn't moved to the same place
        //changes the color between red and green for the players' turn
    }

    @FXML
    private void follow(MouseEvent event) {
        moveFromMiddle = false;

        if ((playerNr == 1 && middleBlack.getChildren().size() > 0) || (playerNr == 2 && middleWhite.getChildren().size() > 0)) {
            if (middleCheck(event)) {
                System.out.println("merge");
                if (playerNr == 1) middleWhiteCoordonate -= 50;
                if (playerNr == 2) middleBlackCoordonate -= 50;
                moveFromMiddle = true;
            } else {
                return;
            }
        }

        if (rollValidator == 1) {
            System.out.println("follow");
            ImageView v = (ImageView) event.getTarget();
            if (playerNr == 1 && !first.validatePick(v)) return;
            if (playerNr == 2 && !second.validatePick(v)) return;

            offset = v.getFitWidth() / 2;
            parent = (Pane) v.getParent();
            parent2 = parent;
            positionX = v.getLayoutX();
            positionY = v.getLayoutY();
            positionXX = positionX;
            positionYY = positionY;
            selected = v;
//            overlap(parent, true);

            String highlight1 = "p";
            String highlight2 = "p";

            if (moveFromMiddle == false)
            {
                if (playerNr == 2) {
                    highlight1 = highlight1.concat(((Integer) (dice1Value + Integer.parseInt(parent2.getId().substring(1)))).toString());
                    highlight2 = highlight2.concat(((Integer) (dice2Value + Integer.parseInt(parent2.getId().substring(1)))).toString());
                    second.setTransparent(true);
                } else {
                    highlight1 = highlight1.concat(((Integer) (Integer.parseInt(parent2.getId().substring(1)) - dice1Value)).toString());
                    highlight2 = highlight2.concat(((Integer) (Integer.parseInt(parent2.getId().substring(1)) - dice2Value)).toString());
                    first.setTransparent(true);
                }

                if (Integer.parseInt(highlight1.substring(1)) >= 1 && Integer.parseInt(highlight1.substring(1)) <= 24) {
                    high1 = getpane(highlight1);
                    if (dice1Value > 0) high1.setStyle("-fx-border-color: red");
                }
                if (Integer.parseInt(highlight2.substring(1)) >= 1 && Integer.parseInt(highlight2.substring(1)) <= 24) {
                    high2 = getpane(highlight2);
                    if (dice2Value > 0) high2.setStyle("-fx-border-color: red");
                }
            }
            else {
                if (playerNr == 2) {
                    highlight1 = highlight1.concat(((Integer) dice1Value).toString());
                    highlight2 = highlight2.concat(((Integer) dice2Value).toString());
                } else {
                    highlight1 = highlight1.concat(((Integer) (25 - dice1Value)).toString());
                    highlight2 = highlight2.concat(((Integer) (25 - dice2Value)).toString());
                }
                first.setTransparent(true);
                second.setTransparent(true);
                high1 = getpane(highlight1);
                high2 = getpane(highlight2);
                if (dice1Value>0) high1.setStyle("-fx-border-color: red");
                if(dice2Value>0) high2.setStyle("-fx-border-color: red");


            }


            if (v.getParent() != board) {
                Pane prnt = (Pane) v.getParent();
                board.getChildren().add(v);
                overlap(prnt, true);
                v.setLayoutX(x - offset);
                v.setLayoutY(y - offset);
            }
//            v.setMouseTransparent(true);
        }


    }

    private Boolean middleCheck(MouseEvent event) {

        ImageView v = (ImageView) event.getTarget();
        Pane test = (Pane) v.getParent();

        if (test.equals(middleBlack) && playerNr == 1 && test.getChildren().contains(v)) {
            return true;
        } else return test.equals(middleWhite) && playerNr == 2 && test.getChildren().contains(v);
    }

    private Boolean getOutFromMiddle() {
        if (playerNr == 2) {
            if (second.getOut(parent)) {
                ObservableList<Node> list = parent.getChildren();
                position = position.concat(middleBlack.getId()).concat("^").concat(list.get(0).getId()).concat("^").concat(String.valueOf(0)).concat("^").concat(String.valueOf(middleBlackCoordonate)).concat("^");
                position = position.concat(parent.getId()).concat("^").concat(selected.getId()).concat("^").concat(String.valueOf(positionX)).concat("^").concat(String.valueOf(positionY + 50)).concat("^");
                list.get(0).setLayoutX(0);
                list.get(0).setLayoutY(middleBlackCoordonate);
                middleBlackCoordonate += 50;
                middleBlack.getChildren().add(list.get(0));
                if (selected.isMouseTransparent()) {
                    selected.setMouseTransparent(false);
                    first.setTransparent(false);
                    second.setTransparent(false);
                }
                selected.setLayoutX(positionX);
                selected.setLayoutY(positionY + 50);
                try {
                    parent.getChildren().add(selected);
                } catch (Exception e) {
                    System.out.println("Selected exception");
                }

                clearBorder();
                diceValueChange();    //based on the move of the player sets to 0 the dice that has been used
                finalSend2();
                return true;
            }
            return false;
        } else {
            if (first.getOut(parent)) {
                ObservableList<Node> list = parent.getChildren();

                position = position.concat(parent.getId()).concat("^").concat(selected.getId()).concat("^").concat(String.valueOf(positionX)).concat("^").concat(String.valueOf(positionY + 50)).concat("^");
                position = position.concat(middleWhite.getId()).concat("^").concat(list.get(0).getId()).concat("^").concat(String.valueOf(0)).concat("^").concat(String.valueOf(middleWhiteCoordonate)).concat("^");
                list.get(0).setLayoutX(0);
                list.get(0).setLayoutY(middleWhiteCoordonate);
                middleWhiteCoordonate += 50;
                middleWhite.getChildren().add(list.get(0));

                if (selected.isMouseTransparent()) {
                    selected.setMouseTransparent(false);
                    first.setTransparent(false);
                    second.setTransparent(false);
                }
                selected.setLayoutX(positionX);
                selected.setLayoutY(positionY + 50);
                try {
                    parent.getChildren().add(selected);
                } catch (Exception e) {
                    System.out.println("Selected exception");
                }

                clearBorder();

                diceValueChange();    //based on the move of the player sets to 0 the dice that has been used
                finalSend2();
                return true;
            }
            return false;
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

    public void diceValueChange() {

        if (moveFromMiddle) {
            if (playerNr == 2) {
                if (parent.getId().substring(1).equals(((Integer) dice1Value).toString())) {
                    dice1Value = 0;
                } else dice2Value = 0;
                return;
            } else {
                if (parent.getId().substring(1).equals(((Integer) (25 - dice1Value)).toString())) {
                    dice1Value = 0;
                } else dice2Value = 0;
                return;
            }
        }

        if (dice1Value.equals(dice2Value) && dice1Value > 0) {
            System.out.println("intra si aici cand e egal");
            if (dice3Value != 0) dice3Value = 0;
            else if (dice4Value != 0) dice4Value = 0;
            else if (dice1Value != 0) dice1Value = 0;
            else if (dice2Value != 0) dice2Value = 0;

            return;
        }

        if (playerNr == 2) {                                                           //checks which dice has been used and then sets it to 0
            dice1Value = second.dice1ValueChange(parent, parent2, dice1Value);
            dice2Value = second.dice2ValueChange(parent, parent2, dice2Value);
//            if (second.bothDicesValueChange(parent, parent2, dice1Value, dice2Value)) {
//                dice1Value = 0;
//                dice2Value = 0;
//            }
        } else {                                                                         //checks which dice has been used and then sets it to 0
            dice1Value = first.dice1ValueChange(parent, parent2, dice1Value);
            dice2Value = first.dice2ValueChange(parent, parent2, dice2Value);
//            if (first.bothDicesValueChange(parent, parent2, dice1Value, dice2Value)) {
//                dice1Value = 0;
//                dice2Value = 0;
//            }
        }
    }

    public void clearBorder() {
        if (high1 != null)
            high1.setStyle("-fx-border: 0");
        if (high2 != null)
            high2.setStyle("-fx-border: 0");
    }

    public void changeColorOfPlayerTurn() {
        if (playerNr == 1) {
            player1.setStyle(redstyle);
            player2.setStyle(greenstyle);
        } else {
            player2.setStyle(redstyle);
            player1.setStyle(greenstyle);
        }
    }

    public void finalSend() {

        if (parent2.getId().equals(parent.getId())) {
//            overlap(parent, false);
            selected = null;
            parent = null;
            return;
        }

        if (dice1Value + dice2Value > 0) {
            position = position.concat(parent.getId()).concat("^").concat(selected.getId()).concat("^").concat(String.valueOf(positionX)).concat("^").concat(String.valueOf(positionY)).concat("^");
//          overlap(parent,false);
            selected = null;
            parent = null;
            return;
        }
        position = position.concat(parent.getId()).concat("^").concat(selected.getId()).concat("^").concat(String.valueOf(positionX)).concat("^").concat(String.valueOf(positionY));
        out.println(position);
        position = "";
        changeColorOfPlayerTurn();
        board.setDisable(true);
        selected = null;
        parent = null;
    }

    public void finalSend2() {

        if (parent2.getId().equals(parent.getId())) {
//            overlap(parent, false);
            selected = null;
            parent = null;
            return;
        }

        if (dice1Value + dice2Value > 0) {
//            position = position.concat(parent.getId()).concat("^").concat(selected.getId()).concat("^").concat(String.valueOf(positionX)).concat("^").concat(String.valueOf(positionY)).concat("^");
//          overlap(parent,false);
            selected = null;
            parent = null;
            return;
        }
//        position = position.concat(parent.getId()).concat("^").concat(selected.getId()).concat("^").concat(String.valueOf(positionX)).concat("^").concat(String.valueOf(positionY));
        out.println(position);
        position = "";
        changeColorOfPlayerTurn();
        board.setDisable(true);
        selected = null;
        parent = null;
    }

    @FXML
    private void changeFace(int face, ImageView dice) {
//        int face = (int) (Math.random() * 6) + 1;

        File file = new File("src/main/resources/images/Dice" + face + ".png");
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
        if (rollValidator == 0) {
            roll.start();
            rollValidator = 1;
            System.out.println(dice1Value + " " + dice2Value);
        }
    }

    public void addJetons() {
        first.addJetons(j1, j2, j11, j12, j13, j14, j15, j21, j22, j23, j24, j25, j26, j27, j28);
        second.addJetons(j3, j4, j5, j6, j7, j8, j9, j10, j16, j17, j18, j19, j20, j29, j30);

        jetons.add(j1);
        jetons.add(j2);
        jetons.add(j3);
        jetons.add(j4);
        jetons.add(j5);
        jetons.add(j6);
        jetons.add(j7);
        jetons.add(j8);
        jetons.add(j9);
        jetons.add(j10);
        jetons.add(j11);
        jetons.add(j12);
        jetons.add(j13);
        jetons.add(j14);
        jetons.add(j15);
        jetons.add(j16);
        jetons.add(j17);
        jetons.add(j18);
        jetons.add(j19);
        jetons.add(j20);
        jetons.add(j21);
        jetons.add(j22);
        jetons.add(j23);
        jetons.add(j24);
        jetons.add(j25);
        jetons.add(j26);
        jetons.add(j27);
        jetons.add(j28);
        jetons.add(j29);
        jetons.add(j30);

    }

    public void addPanes() {
        panes.add(p1);
        panes.add(p2);
        panes.add(p3);
        panes.add(p4);
        panes.add(p5);
        panes.add(p6);
        panes.add(p7);
        panes.add(p8);
        panes.add(p9);
        panes.add(p10);
        panes.add(p11);
        panes.add(p12);
        panes.add(p13);
        panes.add(p14);
        panes.add(p15);
        panes.add(p16);
        panes.add(p17);
        panes.add(p18);
        panes.add(p19);
        panes.add(p20);
        panes.add(p21);
        panes.add(p22);
        panes.add(p23);
        panes.add(p24);
        panes.add(middleWhite);
        panes.add(middleBlack);
    }

    private List<ImageView> getAllChildren(Pane root) {
        List<ImageView> pieces = new ArrayList<>();
        for (Node n : root.getChildrenUnmodifiable()) {
            if (n instanceof ImageView)
                pieces.add((ImageView) n);
        }
        return pieces;
    }

    private double getOverlap(Pane root) {
        List<ImageView> children = getAllChildren(root);
        int size = children.size();
//        System.out.println("Children: "+size);
        if (size > 1) {
//            System.out.println("Copil2: "+children.get(1).getLayoutY());
            double overlap = 150 - children.get(1).getLayoutY();
            System.out.println("Overlap: " + overlap);
            if (overlap < 0.1)
                return 0;
            return overlap * (-1);
        }

        return 0;
    }

    public void moveAnimation(ImageView view, double value) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(view.layoutYProperty(), view.getLayoutY())),
                new KeyFrame(Duration.millis(50),
                        new KeyValue(view.layoutYProperty(), view.getLayoutY() + value))
        );
        timeline.play();
    }

    public void overlap(Pane pane, boolean reverse) {
        List<ImageView> list = getAllChildren(pane);
        double size = list.size();
        double newY;
        double value;
//        int  overlap = (int) getOverlap(pane);
        double pieceHeight = 50;
        ImageView view;

        if (size > 4) {
            newY = ((pieceHeight) / (size));
            value = newY;

//            System.out.println("Rotation:"+pane.getRotate());
            for (int i = 1; i < size; i++) {
                view = list.get(i);

                if (!reverse)
                    moveAnimation(view, value);
                else
                    moveAnimation(view, -value);

                value = value + newY;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeFace((int) Math.random() * 6 + 1, dice1);
        changeFace((int) Math.random() * 6 + 1, dice2);
        roll = new Roll();
        first = new Player1();
        second = new Player2();

        addJetons();
        addPanes();


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
                    board.rotateProperty().setValue(180);
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
                    System.out.println(response2);


                    if (playerNr == 1) {
                        player1.setStyle(greenstyle);
                        player2.setStyle(redstyle);
                    } else {
                        player1.setStyle(redstyle);
                        player2.setStyle(greenstyle);
                    }
                    board.setDisable(false);
                    rollValidator = 0;

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Pane pane = (Pane) getjeton(parts[1]).getParent();
                            Pane pane1 = null, pane2 = null, pane3 = null, pane4 = null;
                            Pane pane5 = null, pane6 = null, pane7 = null;

                            ArrayList<Pane> panes = new ArrayList<>();


                            if (parts.length > 5) pane1 = (Pane) getjeton(parts[5]).getParent();
                            if (parts.length > 8) pane2 = (Pane) getjeton(parts[9]).getParent();
                            if (parts.length > 12) pane3 = (Pane) getjeton(parts[13]).getParent();
                            if (parts.length > 16) pane4 = (Pane) getjeton(parts[17]).getParent();
                            if (parts.length > 20) pane5 = (Pane) getjeton(parts[21]).getParent();
                            if (parts.length > 24) pane6 = (Pane) getjeton(parts[25]).getParent();
                            if (parts.length > 28) pane7 = (Pane) getjeton(parts[29]).getParent();

                            panes.add(pane1);
                            panes.add(pane2);
                            panes.add(pane3);
                            panes.add(pane4);
                            panes.add(pane5);
                            panes.add(pane6);
                            panes.add(pane7);

                            getjeton(parts[1]).setLayoutX(Double.parseDouble(parts[2]));
                            getjeton(parts[1]).setLayoutY(Double.parseDouble(parts[3]));
                            if (!pane.equals(pane1))
                                overlap(getpane(parts[0]), false);
                            (getpane(parts[0])).getChildren().add(getjeton(parts[1]));
                            if (!pane.equals(pane1))
                                overlap(pane, true);
                            pane = getpane(parts[0]);

                            int start = 0;
                            for(int i=4; i<parts.length; i+=4)
                            {
                                getjeton(parts[i+1]).setLayoutX(Double.parseDouble(parts[i+2]));
                                getjeton(parts[i+1]).setLayoutY(Double.parseDouble(parts[i+3]));
                                if (!pane.equals(panes.get(start)))
                                    overlap(getpane(parts[i]), false);
                                (getpane(parts[i])).getChildren().add(getjeton(parts[i+1]));
                                if (!pane.equals(panes.get(start)))
                                    overlap(panes.get(start), true);
                                start++;
                            }
                        }
                    });
                    System.out.println("Raspuns: " + response2);
                } catch (IOException e) {
                    System.exit(1);
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
                System.out.println("handleclicked");
                x = event.getX();
                y = event.getY();
                if ((event.getTarget() instanceof Pane) && (event.getTarget() != table) && (event.getTarget() != board)) {
                    parent = (Pane) event.getTarget();
                    if (parent.getChildren().size() <= 4) {
                        positionX = 0;
                        positionY = 200 - parent.getChildren().size() * 50;
                    } else {
                        positionX = 0;
                        positionY = 0;
//                        overlap(parent, false);
                    }
                }
                if (selected != null) {
                    overlap(parent, false);
                    back();
                }
            }
        });
    }
}


