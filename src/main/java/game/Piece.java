package game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Piece {
    private ImageView piece;
    private Container place;
    private String type;
    private Player player;

    public Piece(ImageView piece){
        this.piece = piece;
    }

    public Piece(ImageView piece, Container place, String type){
        this.piece = piece;
        this.place = place;
        this.type = type;
    }

    public void move(Container container){
        this.place.removePiece(this);
        this.place = container;
//        if(piece.getParent() != place.getRepresentation())
//        if(!container.getRepresentation().getChildren().contains(piece))
            this.place.getRepresentation().getChildren().add(piece);
//        this.place.addPiece(this);
    }

    public void moveAnimation(double value) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(piece.layoutYProperty(), piece.getLayoutY())),
                new KeyFrame(Duration.millis(50),
                        new KeyValue(piece.layoutYProperty(), piece.getLayoutY() + value))
        );
        timeline.play();
    }

    public void show(boolean show){
        if(show) {
            this.piece.getStyleClass().clear();
            this.piece.getStyleClass().add("pieceHigh");
        }else
            this.piece.getStyleClass().clear();
    }

    public ImageView getPiece() {
        return piece;
    }
    public void setPiece(ImageView piece) {
        this.piece = piece;
    }

    public Container getPlace() {
        return place;
    }
    public void setPlace(Container place) {
        this.place = place;
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
}
