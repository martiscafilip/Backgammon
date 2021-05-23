package game;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Spike extends Container{
    private Board board;
    private String styleClass;
    private int orderNumber;

    public Spike(Pane spike){
        this.representation = spike;
        for(String s : representation.getStyleClass())
            this.styleClass = s;

        this.representation.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                select(event);
            }
        });
    }

    public void select(MouseEvent event){
        if(Selected.getSelected() == null && pieces.size() > 0 && player.outPlace.pieces.size() == 0) {
            System.out.println("Is home: " + player.isHome());
            Piece piece = this.pieces.get(pieces.size() - 1);
            Selected.select(piece);

            piece.getPiece().setLayoutX(event.getX() - (double) Sizes.GRID_SIZE/2);
            piece.getPiece().setLayoutY(event.getY() - (double) Sizes.GRID_SIZE/2);

//            player.removeSpikeHigh();
            if(type == "white")
                player.highPossiblePlaces(orderNumber);
            else
                player.highPossiblePlaces(23 - orderNumber);

            representation.setMouseTransparent(false);
            this.removePiece(piece);
//            this.representation.getChildren().remove(piece);
            overlap(true);
        }else if(Selected.getSelected() != null) {
            if (Selected.getSelected().getType().compareTo(this.getType()) == 0 || this.type.compareTo("none") == 0) {
                if (Selected.getFrom() instanceof Spike) {
                    Spike s = (Spike) Selected.getFrom();
                    System.out.println("Moved: " + (orderNumber - s.getOrderNumber()));
                    if (Selected.getSelected().getType() == "white") {
                        this.dice.consume(orderNumber - s.getOrderNumber());
                    } else {
                        this.dice.consume((orderNumber - s.getOrderNumber()) * (-1));
                    }
                }

                if(Selected.getFrom() instanceof Out){
                    Out o = (Out) Selected.getFrom();
                    System.out.println("Order NUmber: " + orderNumber);
                    if(Selected.getSelected().getType() == "white"){
                        this.dice.consume(orderNumber + 1);
                    } else {
                        this.dice.consume(24 - orderNumber);
                    }
                }

                overlap(false);
                this.addPiece(Selected.getSelected());
                Selected.deselect();
                player.removeSpikeHigh();
                player.highPossibleMoves();
            } else {
                if (pieces.size() == 1) {

                    if (Selected.getFrom() instanceof Spike) {
                        Spike s = (Spike) Selected.getFrom();
                        System.out.println("Moved: " + (orderNumber - s.getOrderNumber()));
                        if (Selected.getSelected().getType() == "white") {
                            this.dice.consume(orderNumber - s.getOrderNumber());
                        } else {
                            this.dice.consume((orderNumber - s.getOrderNumber()) * (-1));
                        }
                    }

                    if(Selected.getFrom() instanceof Out){
                        Out o = (Out) Selected.getFrom();
                        if(Selected.getSelected().getType() == "white"){
                            this.dice.consume(orderNumber + 1);
                        } else {
                            this.dice.consume(24 - orderNumber);
                        }
                    }

                    this.player.getOutPlace().overlap(false);
                    Piece p = pieces.get(0);
                    this.player.getOutPlace().addPiece(p);
                    pieces.remove(p);

                    this.addPiece(Selected.getSelected());
                    Selected.deselect();


                    player.removeSpikeHigh();
                    player.highPossibleMoves();
                }
            }
        }
    }

    public void show(boolean show){
        this.representation.getStyleClass().clear();
        if(show)
            this.representation.getStyleClass().add(styleClass+"High");
        else
            this.representation.getStyleClass().add(styleClass);
    }

    public void showPiece(boolean show){
        if(this.pieces.size() > 0)
            this.pieces.get(this.pieces.size() - 1).show(show);
    }

    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    public int getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "Spike{" +
                "spike=" + representation +
                ", pieces=" + pieces +
                '}';
    }
}
