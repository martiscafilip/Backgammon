package org.example;

import extra.Network;
import extra.Player1;
import extra.Player2;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private ImageView j1;
    @FXML
    private ImageView j2;
    @FXML
    private ImageView j3;
    @FXML
    private ImageView j4;
    @FXML
    private ImageView j5;
    @FXML
    private ImageView j6;
    @FXML
    private ImageView j7;
    @FXML
    private ImageView j8;
    @FXML
    private ImageView j9;
    @FXML
    private ImageView j10;
    @FXML
    private ImageView j11;
    @FXML
    private ImageView j12;
    @FXML
    private ImageView j13;
    @FXML
    private ImageView j14;
    @FXML
    private ImageView j15;
    @FXML
    private ImageView j16;
    @FXML
    private ImageView j17;
    @FXML
    private ImageView j18;
    @FXML
    private ImageView j19;
    @FXML
    private ImageView j20;
    @FXML
    private ImageView j21;
    @FXML
    private ImageView j22;
    @FXML
    private ImageView j23;
    @FXML
    private ImageView j24;
    @FXML
    private ImageView j25;
    @FXML
    private ImageView j26;
    @FXML
    private ImageView j27;
    @FXML
    private ImageView j28;
    @FXML
    private ImageView j29;
    @FXML
    private ImageView j30;

    @FXML
    private Pane table;
    @FXML
    private AnchorPane board;
    @FXML
    private AnchorPane bigboard;

    @FXML
    private Pane p1;
    @FXML
    private Pane p2;
    @FXML
    private Pane p3;
    @FXML
    private Pane p4;
    @FXML
    private Pane p5;
    @FXML
    private Pane p6;
    @FXML
    private Pane p7;
    @FXML
    private Pane p8;
    @FXML
    private Pane p9;
    @FXML
    private Pane p10;
    @FXML
    private Pane p11;
    @FXML
    private Pane p12;
    @FXML
    private Pane p13;
    @FXML
    private Pane p14;
    @FXML
    private Pane p15;
    @FXML
    private Pane p16;
    @FXML
    private Pane p17;
    @FXML
    private Pane p18;
    @FXML
    private Pane p19;
    @FXML
    private Pane p20;
    @FXML
    private Pane p21;
    @FXML
    private Pane p22;
    @FXML
    private Pane p23;
    @FXML
    private Pane p24;

    @FXML
    private ImageView dice1;
    @FXML
    private ImageView dice2;
    @FXML
    private Button rollButton;


    private ImageView selected = null;
    private double offset;

    private double positionX;
    private double positionY;
    private double positionXX;
    private double positionYY;
    private double x, y;
    private Pane parent;
    private Pane parent2;
    private Roll roll;
    private Integer dice1Value = 1;
    private Integer dice2Value = 2;
    private Integer diceSum;
    private int rollValidator = 0;

    private Integer playerNr = 0;
    private PrintWriter out;
    private BufferedReader in;

    private final ArrayList<ImageView> jetons = new ArrayList<>();
    private final ArrayList<Pane> panes = new ArrayList<>();

    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Text p1star;
    @FXML
    private Text p2star;
    private Player1 first;
    private Player2 second;

    private final String redstyle = "-fx-background-color: red ;-fx-border-color: black";
    private final String greenstyle = "-fx-background-color: green ;-fx-border-color: black";

    private Pane high1;
    private Pane high2;
    private Pane high3;


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
                    diceSum = dice1Value + dice2Value;
                }
            }
        }
    }

    public void back() {
        System.out.println("back");

        if(playerNr==1) {
            if (Integer.parseInt(parent.getId().substring(1)) - Integer.parseInt(parent2.getId().substring(1)) != dice1Value
                    && Integer.parseInt(parent.getId().substring(1)) - Integer.parseInt(parent2.getId().substring(1)) != dice2Value
                    && Integer.parseInt(parent.getId().substring(1)) - Integer.parseInt(parent2.getId().substring(1)) != (dice2Value + dice1Value)) {
                if (selected.isMouseTransparent()) {
                    selected.setMouseTransparent(false);
                    first.setTransparent(false);
                }
                selected.setLayoutX(positionXX);
                selected.setLayoutY(positionYY);

                if(parent2.getChildren().size() > 4)
                    overlap(parent2, false);
                parent2.getChildren().add(selected);

                selected = null;
                parent = null;
                return;
            }
        }
        else
        {
            if (Integer.parseInt(parent2.getId().substring(1)) - Integer.parseInt(parent.getId().substring(1)) != dice1Value
                    && Integer.parseInt(parent2.getId().substring(1)) - Integer.parseInt(parent.getId().substring(1)) != dice2Value
                    && Integer.parseInt(parent2.getId().substring(1)) - Integer.parseInt(parent.getId().substring(1)) != (dice2Value + dice1Value)) {
                if (selected.isMouseTransparent()) {
                    selected.setMouseTransparent(false);
                    second.setTransparent(false);
                }
                selected.setLayoutX(positionXX);
                selected.setLayoutY(positionYY);

                if(parent2.getChildren().size() > 4)
                    overlap(parent2, false);
                parent2.getChildren().add(selected);

                selected = null;
                parent = null;
                return;
            }
        }


        if (selected.isMouseTransparent()){
            selected.setMouseTransparent(false);
                first.setTransparent(false);
                second.setTransparent(false);
        }

        selected.setLayoutX(positionX);
        selected.setLayoutY(positionY);

        if(parent.getChildren().size() > 4)
            overlap(parent, false);

        try {
            parent.getChildren().add(selected);
        }catch (Exception e){
            System.out.println("Selected exception");
        }
        System.out.println(positionX);
        System.out.println(positionY);
        System.out.println(parent.getChildren().size());

        high1.setStyle("-fx-border: 0");
        high2.setStyle("-fx-border: 0");
        high3.setStyle("-fx-border: 0");

        if(playerNr==1) {
            if (Integer.parseInt(parent.getId().substring(1)) - Integer.parseInt(parent2.getId().substring(1)) == dice1Value) {
                dice1Value = 0;
            } else if (Integer.parseInt(parent.getId().substring(1)) - Integer.parseInt(parent2.getId().substring(1)) == dice2Value) {
                dice2Value = 0;
            } else {
                dice1Value = 0;
                dice2Value = 0;
            }
        }
        else
        {
            if (Integer.parseInt(parent2.getId().substring(1)) - Integer.parseInt(parent.getId().substring(1)) == dice1Value) {
                dice1Value = 0;
            } else if (Integer.parseInt(parent2.getId().substring(1)) - Integer.parseInt(parent.getId().substring(1)) == dice2Value) {
                dice2Value = 0;
            } else {
                dice1Value = 0;
                dice2Value = 0;
            }
        }

        if (parent2.getId().equals(parent.getId())) {
            selected = null;
            parent = null;
            return;
        }

        if (dice1Value + dice2Value > 0) {
            selected = null;
            parent = null;
            return;
        }


        String position = "";
        position = position.concat(parent.getId()).concat("^").concat(selected.getId()).concat("^").concat(String.valueOf(positionX)).concat("^").concat(String.valueOf(positionY));
        out.println(position);

        System.out.println("DICES: " + dice1Value + " " + dice2Value);

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

    @FXML
    private void follow(MouseEvent event) {

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

            String highlight1 = "p";
            String highlight2 = "p";
            String highlight3 = "p";

            if(playerNr==1) {
                highlight1 = highlight1.concat(((Integer) (dice1Value + Integer.parseInt(parent2.getId().substring(1)))).toString());
                highlight2 = highlight2.concat(((Integer) (dice2Value + Integer.parseInt(parent2.getId().substring(1)))).toString());
                highlight3 = highlight3.concat(((Integer) (diceSum + Integer.parseInt(parent2.getId().substring(1)))).toString());
                first.setTransparent(true);
            }
            else
            {
                highlight1 = highlight1.concat(((Integer) ( Integer.parseInt(parent2.getId().substring(1))-dice1Value)).toString());
                highlight2 = highlight2.concat(((Integer) ( Integer.parseInt(parent2.getId().substring(1))-dice2Value)).toString());
                highlight3 = highlight3.concat(((Integer) ( Integer.parseInt(parent2.getId().substring(1))-diceSum)).toString());
                second.setTransparent(true);
            }
            high1 = getpane(highlight1);
            high2 = getpane(highlight2);
            high3 = getpane(highlight3);
            if (dice1Value > 0) high1.setStyle("-fx-border-color: red");
            if (dice2Value > 0) high2.setStyle("-fx-border-color: red");
            if (dice1Value > 0 && dice2Value > 0) high3.setStyle("-fx-border-color: red");

            if (v.getParent() != board) {
                board.getChildren().add(v);
                v.setLayoutX(x - offset);
                v.setLayoutY(y - offset);
            }
//            v.setMouseTransparent(true);
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
    }

    private List<ImageView> getAllChildren(Pane root){
        List<ImageView> pieces = new ArrayList<>();
        for(Node n : root.getChildrenUnmodifiable()){
            if(n instanceof ImageView)
                pieces.add((ImageView) n);
        }
        return pieces;
    }

    private double getOverlap(Pane root){
        List<ImageView> children = getAllChildren(root);
        int size = children.size();
//        System.out.println("Children: "+size);
        if(size > 1) {
//            System.out.println("Copil2: "+children.get(1).getLayoutY());
            double overlap = 150 - children.get(1).getLayoutY();
            System.out.println("Overlap: "+overlap);
            return overlap*(-1);
        }

        return 0;
    }

    public void moveAnimation(ImageView view, double value){
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(view.layoutYProperty(), view.getLayoutY())),
                new KeyFrame(Duration.millis(50),
                        new KeyValue(view.layoutYProperty(), view.getLayoutY() + value))
        );
        timeline.play();
    }

    public void overlap(Pane pane, boolean reverse){
        List<ImageView> list = getAllChildren(pane);
        double size = list.size();
        double newY;
        double value;
        double overlap = getOverlap(pane);
        double pieceHeight = 50;
        ImageView view;

        if(size > 4){
//            System.out.println(view1.getFitHeight());
            if(!reverse)
                newY = ((pieceHeight-overlap)/(size));
            else
                newY = ((pieceHeight)/size+1);
            value = newY;
//            value = (pieceHeight * (size - 4))/(size);

            for(int i = 1; i < size; i++){
//                view = list.get(i);
                view = list.get(i);
//                view.setLayoutY(view.getLayoutY() + value);

                if(!reverse)
                    moveAnimation(view, value);
                else
                    moveAnimation(view, -value);

                value = value + newY;
            }
//            System.out.println("Translate: "+view2.getLayoutY());
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
                            getjeton(parts[1]).setLayoutX(Double.parseDouble(parts[2]));
                            getjeton(parts[1]).setLayoutY(Double.parseDouble(parts[3]));
                            (getpane(parts[0])).getChildren().add(getjeton(parts[1]));
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
                    if(parent.getChildren().size() < 4) {
                        positionX = 0;
                        positionY = 200 - parent.getChildren().size() * 50;
                    }else {
//                        overlap(parent, false);
                        positionX = 0;
                        positionY = 0;
                    }
                }
                if (selected != null) {
                    back();
                }
            }
        });
    }
}


