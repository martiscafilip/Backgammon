package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.*;

public class Start implements Initializable {

    public Button singlePlayer;
    public Button multiPlayer;
    public BorderPane stage;
    public VBox vbox;
    public HBox hbox1;
    public HBox hbox2;


    public Button getSinglePlayer() {
        return singlePlayer;
    }

    public Button getMultiPlayer() {
        return multiPlayer;
    }

    public void singlePlayerclick(ActionEvent event) throws IOException, InterruptedException {
        Parent tableparent = FXMLLoader.load(getClass().getResource("gamesingle.fxml"));
        Scene table = new Scene(tableparent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(table);
        window.show();
    }


    public void multiPlayerclick(ActionEvent event) throws IOException {
        Parent tableparent = FXMLLoader.load(getClass().getResource("game.fxml"));
        Scene table = new Scene(tableparent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(table);
        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singlePlayer.prefWidthProperty().bind(Bindings.divide(stage.widthProperty(), 3));
        hbox1.prefHeightProperty().bind(Bindings.divide(stage.heightProperty(), 5));
        singlePlayer.prefHeightProperty().bind(Bindings.divide(stage.heightProperty(), 4));

        multiPlayer.prefWidthProperty().bind(Bindings.divide(stage.widthProperty(), 3));
        hbox2.prefHeightProperty().bind(Bindings.divide(stage.heightProperty(), 5));
        multiPlayer.prefHeightProperty().bind(Bindings.divide(stage.heightProperty(), 4));
    }
}
