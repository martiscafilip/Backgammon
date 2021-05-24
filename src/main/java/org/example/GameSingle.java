package org.example;

import game.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameSingle implements Initializable {

    @FXML private AnchorPane board;
    @FXML private Pane player1Storage;
    @FXML private Pane player2Storage;
    @FXML private Pane player1Out;
    @FXML private Pane player2Out;
    @FXML private Pane middleBar;
    @FXML private Pane boardBackground;

    @FXML private ImageView dice1;
    @FXML private ImageView dice2;
    @FXML private Button rollButton;

    @FXML private Label p1Label;
    @FXML private Label p2Label;
    @FXML private Text p1Star;
    @FXML private Text p2Star;
    @FXML Label timer;
    @FXML Label statusLabel;

    private List<Piece> whitePieces;
    private List<Piece> blackPieces;
    private List<Spike> spikesPlayer1;
    private List<Spike> spikesPlayer2;


    private List<Piece> getPieces(Storage storage, String type) {
        List<Piece> pieces = new ArrayList<>();
        for (Node n : storage.getRepresentation().getChildrenUnmodifiable()) {
            if (n instanceof ImageView) {
                n.setMouseTransparent(true);
                Piece p = new Piece((ImageView) n);
                p.setType(type);
                pieces.add(p);
            }
        }
        return pieces;
    }

    private List<Spike> getSpikes(AnchorPane board) {
        System.out.println("Order normal: ");
        List<Spike> spikes = new ArrayList<>();
        int order = 0;
        for(Node n : board.getChildrenUnmodifiable()){
            if(n != player1Storage && n != player2Storage &&
                    n != player1Out && n != player2Out &&
                    n != boardBackground && n != middleBar ) {

                System.out.println(n);
                Spike spike = new Spike((Pane) n);
                spike.setOrderNumber(order);
//                System.out.println("Order: " + order);
                order++;
                spikes.add(spike);
            }
        }
        return spikes;
    }

    private List<Spike> reveresSpikes(List<Spike> order){
//        System.out.println("Order Reveres: ");
        List<Spike> reveres = new ArrayList<>();
        int orderNr = order.size()-1;

        for(Spike s : order){
            s.setOrderNumber(orderNr);
//            System.out.println("orderNr: " + orderNr);
            orderNr--;
            reveres.add(0, s);
        }
        return reveres;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        System.out.println(board);
//        System.out.println(player1Storage.getChildren().size());
//        System.out.println(board +": "+board.getChildren().size());

        Storage storage1 = new Storage(player1Storage);
        Storage storage2 = new Storage(player2Storage);
        whitePieces = getPieces(storage1, "white");
        blackPieces = getPieces(storage2, "black");

//        whitePieces.get(0).show(true);

        List<Piece> whiteCopy = new ArrayList<>(whitePieces);
        storage1.setPieces(whiteCopy);
        whiteCopy.forEach(piece -> piece.setPlace(storage1));
        whitePieces.forEach(piece -> piece.setPlace(storage1));

        List<Piece> blackCopy = new ArrayList<>(blackPieces);
        storage2.setPieces(blackCopy);
        blackCopy.forEach(piece -> piece.setPlace(storage2));
        blackPieces.forEach(piece -> piece.setPlace(storage2));

        spikesPlayer1 = getSpikes(board);

//        spikesPlayer1.get(0).show(true);
//        spikesPlayer1.get(1).show(false);

        spikesPlayer1.forEach(spike -> spike.setType("none"));
        spikesPlayer2 = reveresSpikes(spikesPlayer1);

        Out outPlayer1 = new Out(player1Out);
        Out outPlayer2 = new Out(player2Out);

        Dice dice = new Dice(dice1, dice2, rollButton);
        Player player1 = new Player(whitePieces, spikesPlayer1, outPlayer1, storage1, "white", dice);
//        Player player2 = new Player(blackPieces, spikesPlayer2, outPlayer2, storage2, "black", dice);
        Bot player2 = new Bot(blackPieces, spikesPlayer2, outPlayer2, storage2, "black", dice);

        player1.setPlayerLabel(p1Label);
        p1Label.setText("You");
        player2.setPlayerLabel(p2Label);
        p2Label.setText("Bot");

        spikesPlayer1.forEach(spike -> spike.setPlayer(player1));
        spikesPlayer2.forEach(spike -> spike.setPlayer(player2));

        whitePieces.forEach(piece -> piece.setPlayer(player1));
        blackPieces.forEach(piece -> piece.setPlayer(player2));

        storage1.setType(player1.getType());
        storage2.setType(player2.getType());

        Board board1 = new Board(board, player1, player2, dice);
        spikesPlayer1.forEach(spike -> spike.setBoard(board1));
        spikesPlayer2.forEach(spike -> spike.setBoard(board1));
        spikesPlayer1.forEach(spike -> spike.setDice(dice));
        spikesPlayer2.forEach(spike -> spike.setDice(dice));

        outPlayer1.setDice(dice);
        outPlayer2.setDice(dice);
        storage1.setDice(dice);
        storage2.setDice(dice);

        board1.bordSetup();
        Selected.board = board1;

        outPlayer1.setPlayer(player1);
        outPlayer2.setPlayer(player2);
        storage1.setPlayer(player1);
        storage2.setPlayer(player2);

//        player1.disablePlayerMove(true);
//        player2.disablePlayerMove(true);

        RoundTimer roundTimer = new RoundTimer(timer);
        timer.setVisible(false);

        GameStatus gameStatus = new GameStatus(statusLabel);
        GameManager manager = new GameManager(player1, player2, board1, dice, roundTimer);
        manager.setGameStatus(gameStatus);
//        spikesPlayer1.get(5).showPiece(true);
        manager.startGame();

//        if(player1 instanceof Bot){
//            System.out.println("Este Bot");
//        }else {
//            System.out.println("Nu este Bot");
//        }
//        dice.rollAction();
//        dice.rollAction();
//        dice.canRoll(false);

//        player1.disablePlayerMove(true);

//        spikesPlayer2.forEach(spike -> System.out.println(spike));
    }
}


