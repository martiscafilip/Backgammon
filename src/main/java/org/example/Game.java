package org.example;

import extra.Network;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Game implements Initializable {

    @FXML
    private ImageView j1;
    @FXML
    private ImageView j2;
    @FXML
    private ImageView j3;
    @FXML
    private Pane table;
    @FXML
    private AnchorPane board;
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



    private ImageView selected = null;
    private double offset;

    private double positionX;
    private double positionY;
    private Pane parent;

    private double x, y;

    private Integer playerNr = 0;
    private PrintWriter out;
    private BufferedReader in;
    private ArrayList<ImageView> jetons = new ArrayList<>();
    private ArrayList<Pane> panes = new ArrayList<>();


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

        board.setDisable(true);
        selected = null;
        parent = null;
//


    }

    @FXML
    private void follow(MouseEvent event) {
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

    @FXML
    private void enter(MouseEvent event) {
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
            System.out.println(pane.getId() + " getpane");
            if (id.equals(pane.getId())) {
                System.out.println("found");
                return pane;
            }
        }
        System.out.println("am trimis null:(");
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
                if (response.equals("1")) {
                    playerNr = 1;

                } else {
                    playerNr = 2;
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


//        j1.setMouseTransparent(true);
//        j2.setMouseTransparent(true);
//        j3.setMouseTransparent(true);
        positionX = 0;
        positionY = 100;
//        parent = (Pane) pTest.getParent();
        System.out.println(parent);

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


