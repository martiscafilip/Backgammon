package game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Out extends Container{
    private int hasMoves = 0;

    public Out(Pane out) {
        this.representation = out;

        this.representation.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(pieces.size() > 0) {
                    select();
                }
            }
        });
    }

    public void select(){
//        System.out.println("Ai dat click!");
//        System.out.println("Count: " + count);
        hasMoves = player.highPossiblePlaces(-1);
        if(Selected.getSelected() == null && hasMoves > 0) {
            Piece piece = this.pieces.get(pieces.size() - 1);
            Selected.select(piece);
//            player.removeSpikeHigh();
//            representation.setMouseTransparent(false);
            this.removePiece(piece);
//            this.representation.getChildren().remove(piece);
            overlap(true);
        }
//        else if(Selected.getSelected() != null){
//            if(Selected.getSelected().getType().compareTo(this.getType()) == 0 || this.type.compareTo("none") == 0) {
//                overlap(false);
//                this.addPiece(Selected.getSelected());
//                Selected.deselect();
//                player.removeSpikeHigh();
//            }else if(pieces.size() == 1){
//                this.player.getOutPlace().overlap(false);
//                Piece p = pieces.get(0);
//                this.player.getOutPlace().addPiece(p);
//                pieces.remove(p);
//
//                this.addPiece(Selected.getSelected());
//                Selected.deselect();
//                player.removeSpikeHigh();
//            }
//        }
    }

    public void show(boolean show){
        if(show) {
            this.representation.getStyleClass().clear();
            this.representation.getStyleClass().add("outHigh");
        }
        else {
            this.representation.getStyleClass().clear();
            this.representation.getStyleClass().add("out");
        }
    }

    public int getHasMoves() {
        hasMoves = player.highPossiblePlaces(-1);
        return hasMoves;
    }

    public void setHasMoves(int hasMoves) {
        this.hasMoves = hasMoves;
    }
}
