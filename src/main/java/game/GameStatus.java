package game;

import javafx.scene.control.Label;

public class GameStatus {
    private Label statusLabel;

    public GameStatus(Label statusLabel){
        this.statusLabel = statusLabel;
    }

    public void setText(String text){
        this.statusLabel.setText(text);
    }

    public String getText(){
        return this.statusLabel.getText();
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(Label statusLabel) {
        this.statusLabel = statusLabel;
    }
}
