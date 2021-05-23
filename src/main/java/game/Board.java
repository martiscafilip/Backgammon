package game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Board {
    private AnchorPane board;
    private Player player1;
    private Player player2;
    private Dice dice;

    public Board(AnchorPane board, Player player1, Player player2, Dice dice) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.dice = dice;

        board.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(Selected.getSelected() != null)
                    move(event);
            }
        });

//        board.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if(Selected.getSelected() != null) {
//                    if(event.getTarget() != board) {
//                        System.out.println("Event: " + event.getTarget());
//                        if(!Selected.getFrom().getPieces().contains(Selected.getSelected().getPiece()))
//                            Selected.getFrom().addPiece(Selected.getSelected());
//                        Selected.deselect();
//                    }
//                }
//            }
//        });
    }

    private void move(MouseEvent event){
        Piece p = Selected.getSelected();
        double eventX = event.getX();
        double eventY = event.getY();
        if(p.getPiece().getParent() != board)
            board.getChildren().add(p.getPiece());
        if(eventX > Selected.offsetX && eventX < (board.getWidth() - Selected.offsetX) &&
                eventY > Selected.offsetY && eventY < (board.getHeight() - Selected.offsetY)) {

            p.getPiece().setLayoutX(eventX - Selected.offsetX);
            p.getPiece().setLayoutY(eventY - Selected.offsetY);
        }
    }

    public void bordSetup(){
        player1.setup();
        player2.setup();
    }

    public AnchorPane getBoard() {
        return board;
    }

    public void setBoard(AnchorPane board) {
        this.board = board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }
}
