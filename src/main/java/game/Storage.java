package game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Storage extends Container{
    public double placeOverlap;

    public Storage(Pane storage){
        this.representation = storage;
        placeOverlap = (double) (Sizes.STORAGE_GRID - 1)/(Sizes.NUMBER_OF_PIECES - 1) * Sizes.GRID_SIZE;
//        representation.setMouseTransparent(true);

        this.representation.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                System.out.println("Player: " + player);
//                if(player.isHome())
                    select();
            }
        });
    }

    public void select(){
        if(Selected.getSelected() != null){
            if(Selected.getSelected().getType().compareTo(this.getType()) == 0 || this.type.compareTo("none") == 0) {
//                overlap(false);
                storageOverlap(false);

                if (Selected.getFrom() instanceof Spike) {
                    Spike s = (Spike) Selected.getFrom();
                    System.out.println("Moved storage: " + (s.getOrderNumber()));
//                    if (Selected.getSelected().getType() == "white") {
//                        this.dice.consume(orderNumber - s.getOrderNumber());
//                    } else {
//                        this.dice.consume((orderNumber - s.getOrderNumber()) * (-1));
//                    }
                    if(dice.getValueDice1() != (24 - s.getOrderNumber()) && dice.getValueDice2() != (24 - s.getOrderNumber())){
                        if(dice.getValueDice1() > dice.getValueDice2())
                            dice.consume(dice.getValueDice1());
                        else
                            dice.consume(dice.getValueDice2());
                    }else {
                        this.dice.consume(24-s.getOrderNumber());
                    }
                }

                this.addInStorage(Selected.getSelected());
                Selected.deselect();
                player.removeSpikeHigh();
                player.highPossibleMoves();
            }else if(pieces.size() == 1){

                if (Selected.getFrom() instanceof Spike) {
                    Spike s = (Spike) Selected.getFrom();
                    System.out.println("Moved storage: " + (s.getOrderNumber()));
//                    if (Selected.getSelected().getType() == "white") {
//                        this.dice.consume(orderNumber - s.getOrderNumber());
//                    } else {
//                        this.dice.consume((orderNumber - s.getOrderNumber()) * (-1));
//                    }
                    this.dice.consume(s.getOrderNumber());
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

    public void addInStorage(Piece piece) {
        if(pieces.size() <= 5) {
            piece.getPiece().setLayoutX(0);
            piece.getPiece().setLayoutY(5 * Sizes.GRID_SIZE - pieces.size() * Sizes.GRID_SIZE);
//            piece.getPiece().setLayoutY(((Sizes.STORAGE_GRID - 1) * Sizes.GRID_SIZE - (pieces.size()) * placeOverlap));
        } else {
            piece.getPiece().setLayoutX(0);
            piece.getPiece().setLayoutY(0);
        }
//        double placeOverlap = (double) (Sizes.STORAGE_GRID - 1)/(Sizes.NUMBER_OF_PIECES - 1) * Sizes.GRID_SIZE;
//        public static final int GRID_SIZE = 50;
//        public static final int NUMBER_OF_PIECES = 15;
//        public static final int NUMBER_OF_SPIKES = 24;
//
//        public static final double SPIKE_GRID = 5;
//        public static final double OUT_GRID = 5;
//        public static final double STORAGE_GRID = 6;

        piece.move(this);
        this.pieces.add(piece);
        type = decideType();
    }

    public double getStorageOverlap(){
        int size = pieces.size();
        if(size > 1) {
            double ovl = ((Sizes.STORAGE_GRID - 1) / (double)(size - 1)) * Sizes.GRID_SIZE;
//            System.out.println("Overlap: "+overlap);
            return Sizes.GRID_SIZE - ovl;
        }
        return 0;
    }

    public void storageOverlap(boolean reverse) {
        double size = pieces.size();
        double newY;
        double value;
        Piece piece;

        if (size > 5) {
            newY = (Sizes.GRID_SIZE - getStorageOverlap()) / size;
            value = newY;

            for (int i = 1; i < size; i++) {
                piece = pieces.get(i);

                if (!reverse)
                    piece.moveAnimation(value);
                else
                    piece.moveAnimation(-value);

                value = value + newY;
            }
        }
    }

    public void show(boolean show){
        if(show) {
            this.representation.getStyleClass().clear();
            this.representation.getStyleClass().add("storageHigh");
        }
        else {
            this.representation.getStyleClass().clear();
            this.representation.getStyleClass().add("storage");
        }
    }
}
