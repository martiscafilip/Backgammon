package game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Container {
    protected String type;
    protected Pane representation;
    protected List<Piece> pieces = new ArrayList<>();
    protected Player player;
    protected Dice dice;

    public void moveAux(Container where){
        if(this.pieces.size() > 0) {
            if (where.getType() != type && where.getPieces().size() == 1) {
                where.getPlayer().getOutPlace().addPiece(where.getPieces().get(0));
            }
            where.overlap(false);
            where.addPiece(this.pieces.get(this.pieces.size() - 1));
            overlap(true);
//            this.removePiece(this.pieces.get(this.pieces.size() - 1));
        }
    }

    public void move(Container where) {
        Timeline timeline = new Timeline();

        Piece p = this.pieces.get(this.pieces.size() - 1);
        double pX = p.getPiece().getLayoutX();
        double pY = p.getPiece().getLayoutY();
        double wX;
        double wY;
        double fX;
        double fY;

        if(where.getRepresentation().getRotate() == 0) {
            wX = where.getRepresentation().getLayoutX();
            wY = where.getRepresentation().getLayoutY();
        } else {
            wX = where.getRepresentation().getLayoutX();
            wY = where.getRepresentation().getLayoutY() + 200;
        }

        if(this.representation.getRotate() == 0) {
            fX = this.getRepresentation().getLayoutX();
            fY = this.getRepresentation().getLayoutY();
        } else {
            fX = this.getRepresentation().getLayoutX();
            fY = this.getRepresentation().getLayoutY() + 200;
        }

        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        e -> addToBoard(this, p, true)),
                new KeyFrame(Duration.ZERO,
                        new KeyValue(p.getPiece().layoutYProperty(), fY)),
                new KeyFrame(Duration.ZERO,
                        new KeyValue(p.getPiece().layoutXProperty(), fX)),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(p.getPiece().layoutYProperty(), wY)),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(p.getPiece().layoutXProperty(), wX)),
                new KeyFrame(Duration.millis(505),
                        e -> addToBoard(this, p, false)),
                new KeyFrame(Duration.millis(510),
                        e -> moveAux(where))
        );

        timeline.play();
    }

    public void addToBoard(Container parent, Piece p, boolean status){
        if(status == true){
            Selected.board.getBoard().getChildren().add(p.getPiece());
            p.getPiece().setLayoutX(parent.getRepresentation().getLayoutX());
            p.getPiece().setLayoutY(parent.getRepresentation().getLayoutY());
        }else {
            parent.getRepresentation().getChildren().add(p.getPiece());
        }
    }

    public void addPiece(Piece piece) {
        if (pieces.size() <= 4) {
            piece.getPiece().setLayoutX(0);
            piece.getPiece().setLayoutY(4 * Sizes.GRID_SIZE - pieces.size() * Sizes.GRID_SIZE);
        } else {
            piece.getPiece().setLayoutX(0);
            piece.getPiece().setLayoutY(0);
        }

        piece.move(this);
        this.pieces.add(piece);
        type = decideType();
    }

    public String decideType(){
        if(pieces.size() > 0) {
            player = pieces.get(0).getPlayer();
            return pieces.get(0).getType();
        }
        player = null;
        return "none";
    }

    public double getOverlap(){
        int size = pieces.size();
        if(size > 1) {
            double ovl = ((Sizes.SPIKE_GRID - 1) / (double)(size - 1)) * Sizes.GRID_SIZE;
//            System.out.println("Overlap: "+overlap);
            return Sizes.GRID_SIZE - ovl;
        }
        return 0;
    }

    public void overlap(boolean reverse) {
        double size = pieces.size();
        double newY;
        double value;
        Piece piece;

        if (size > 4) {
            newY = (Sizes.GRID_SIZE - getOverlap()) / size;
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

    public void removePiece(Piece piece){
        this.pieces.remove(piece);
        type = decideType();
    }

    public boolean existPiece(Piece piece){
        for(Piece p : pieces)
            if(p.getPlace() == piece.getPlace())
                return true;
        return false;
    }

    public Piece getPiece(){
        if(this.pieces.size() > 0)
            return this.pieces.get(pieces.size()-1);
        return null;
    }

    public Pane getRepresentation() {
        return representation;
    }
    public void setRepresentation(Pane representation) {
        this.representation = representation;
    }

    public List<Piece> getPieces() {
        return pieces;
    }
    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }
}
