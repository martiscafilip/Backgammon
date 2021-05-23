package game;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<Piece> pieces;
    List<Spike> spikes;
    Out outPlace;
    Storage storage;
    String type;
    Dice dice;
    Label playerLabel;

    private String labelStyle;
    public boolean hasMoves = true;

    public Player(List<Piece> pieces, List<Spike> spikes, Out outPlace, Storage storage, String type, Dice dice) {
        this.pieces = pieces;
        this.spikes = spikes;
        this.outPlace = outPlace;
        this.storage = storage;
        this.type = type;
        this.dice = dice;

//        disablePlayerMove(true);
        storage.representation.setMouseTransparent(true);
    }

    public Player() {

    }

    public void myTurn(boolean status){
        if(status == true){
            playerLabel.getStyleClass().clear();
            playerLabel.getStyleClass().add(labelStyle);
            playerLabel.setOpacity(1);
        } else {
            playerLabel.getStyleClass().clear();
            playerLabel.getStyleClass().add("playerDeselect");
            playerLabel.setOpacity(0.5);
        }
    }

    public void setup(){
        int i = -1;
        for(int j = 0; j < spikes.size(); j++){
            switch (j){
                case 5:
                case 12: {
                    for (int k=1; k<=5; k++) {
                        i++;
                        spikes.get(j).addPiece(pieces.get(i));
                    }
                }break;
                case 7:{
                    for (int k=1; k<=3; k++) {
                        i++;
                        spikes.get(j).addPiece(pieces.get(i));
                    }
                }break;
                case 23:{
                    for (int k=1; k<=2; k++) {
                        i++;
                        spikes.get(j).addPiece(pieces.get(i));
                    }
                }break;
            }
        }
    }

    public void disablePlayerMove(boolean disable) {
        for (Spike s : spikes) {
            if (s.getType().compareTo(type) == 0) {
                s.getRepresentation().setMouseTransparent(disable);
            }
        }

//        outPlace.getRepresentation().setMouseTransparent(disable);
        storage.getRepresentation().setMouseTransparent(disable);
    }

    public void highPossibleMoves() {
        int count  = 0;
        int size = spikes.size();
        int index;

        removeSpikeHigh();
        if(this.outPlace.pieces.size() == 0) {
            for (int i = 0; i < size; i++) {
                if (canBeMoved(i, this.type) > 0 && spikes.get(i).type == type) {
                    count++;
                    spikes.get(i).showPiece(true);
                    spikes.get(i).getRepresentation().setMouseTransparent(false);
                }
            }
        } else {
            highOutPlace();
            count = outPlace.getHasMoves();
        }


        if(count == 0)
            hasMoves = false;
        else
            hasMoves = true;
    }

    public int canBeMoved(int from, String spikeType){
        int dice1 = dice.getValueDice1();
        int dice2 = dice.getValueDice2();
        int size = spikes.size();
        int count = 0;

        int pos1;
        int pos2;

            pos1 = from - dice1;
            pos2 = from - dice2;

        if (dice.canUse()) {
            if (pos1 < size && pos1 >= 0 && dice1 > 0) {
                if (spikes.get(pos1).getType() == type || spikes.get(pos1).getType() == "none")
                    count++;
                if(spikes.get(pos1).getPieces().size() == 1)
                    count++;
            }

            if (pos2 < size && pos2 >= 0 && dice2 > 0) {
                if (spikes.get(pos2).getType() == type || spikes.get(pos2).getType() == "none")
                    count++;
                if(spikes.get(pos2).getPieces().size() == 1)
                    count++;
            }

            if(isHome()){
                boolean isEmptyD1 = false;
                boolean isEmptyD2 = false;

                if(dice1 != 0) {
                    if (spikes.get(dice1 - 1).getPieces().size() == 0) {
                        isEmptyD1 = true;
                        for (int i = 6; i > dice1 && isEmptyD1; i--) {
                            if (spikes.get(i).getPieces().size() != 0)
                                isEmptyD1 = false;
                        }
                    }
                }

                if(dice2 != 0) {
                    if (spikes.get(dice2 - 1).getPieces().size() == 0) {
                        isEmptyD2 = true;
                        for (int i = 6; i > dice2 && isEmptyD2; i--) {
                            if (spikes.get(i).getPieces().size() != 0)
                                isEmptyD2 = false;
                        }
                    }
                }

                if (dice1 == (from + 1) || dice2 == (from + 1) || isEmptyD1 || isEmptyD2) {
                    count++;
                }
            }
        }
        return count;
    }

    public int highPossiblePlaces(int from) {
        int dice1 = dice.getValueDice1();
        int dice2 = dice.getValueDice2();
        int size = spikes.size();
        int count = 0;

        int pos1 = from + dice1 + 1;
        int pos2 = from + dice2 + 1;

        if (dice.canUse()) {
            removePieceHigh();
            if (pos1 <= size && dice1 > 0) {
                if (spikes.get(size - pos1).getType() == type || spikes.get(size - pos1).getType() == "none") {
                    spikes.get(size - pos1).show(true);
                    spikes.get(size - pos1).getRepresentation().setMouseTransparent(false);
                    count++;
                }
                if (spikes.get(size - pos1).getPieces().size() == 1) {
                    spikes.get(size - pos1).show(true);
                    spikes.get(size - pos1).getRepresentation().setMouseTransparent(false);
                    count++;
                }
            }

            if (pos2 <= size && dice2 > 0) {
                if (spikes.get(size - pos2).getType() == type || spikes.get(size - pos2).getType() == "none") {
                    spikes.get(size - pos2).show(true);
                    spikes.get(size - pos2).getRepresentation().setMouseTransparent(false);
                    count++;
                }
                if (spikes.get(size - pos2).getPieces().size() == 1) {
                    spikes.get(size - pos2).show(true);
                    spikes.get(size - pos2).getRepresentation().setMouseTransparent(false);
                    count++;
                }
            }

            if (isHome()) {
                boolean isEmptyD1 = false;
                boolean isEmptyD2 = false;

                if(dice1 != 0) {
                    if (spikes.get(dice1 - 1).getPieces().size() == 0) {
                        isEmptyD1 = true;
                        for (int i = 6; i > dice1 && isEmptyD1; i--) {
                            if (spikes.get(i).getPieces().size() != 0)
                                isEmptyD1 = false;
                        }
                    }
                }

                if(dice2 != 0) {
                    if (spikes.get(dice2 - 1).getPieces().size() == 0) {
                        isEmptyD2 = true;
                        for (int i = 6; i > dice2 && isEmptyD2; i--) {
                            if (spikes.get(i).getPieces().size() != 0)
                                isEmptyD2 = false;
                        }
                    }
                }

                if (dice1 == (size - from) || dice2 == (size - from) || isEmptyD1 || isEmptyD2) {
                    storage.show(true);
                    count++;
                    storage.getRepresentation().setMouseTransparent(false);
                }

            }
        }
        return count;
    }

    public void highOutPlace(){
        outPlace.show(true);
        outPlace.getRepresentation().setMouseTransparent(false);
    }

    public void removeSpikeHigh(){
        for(Spike s : spikes){
            s.show(false);
            s.getRepresentation().setMouseTransparent(true);
        }
        storage.show(false);
        storage.getRepresentation().setMouseTransparent(true);
        outPlace.show(false);
        outPlace.getRepresentation().setMouseTransparent(true);
    }

    public void removePieceHigh(){
        for(Spike s : spikes){
            s.showPiece(false);
            s.getRepresentation().setMouseTransparent(true);
        }
    }

    public boolean isHome(){
        int sum = 0;

        if(spikes.get(0).getType() == type)
            sum += spikes.get(0).pieces.size();
        if(spikes.get(1).getType() == type)
            sum += spikes.get(1).pieces.size();
        if(spikes.get(2).getType() == type)
            sum += spikes.get(2).pieces.size();
        if(spikes.get(3).getType() == type)
            sum += spikes.get(3).pieces.size();
        if(spikes.get(4).getType() == type)
            sum += spikes.get(4).pieces.size();
        if(spikes.get(5).getType() == type)
            sum += spikes.get(5).pieces.size();

        sum += storage.pieces.size();

//        System.out.println("Sum: " + sum);
        if(sum == 15)
            return true;

        return false;
    }

    public List<Piece> getPieces() {
        return pieces;
    }
    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public List<Spike> getSpikes() {
        return spikes;
    }
    public void setSpikes(List<Spike> spikes) {
        this.spikes = spikes;
    }

    public Out getOutPlace() {
        return outPlace;
    }
    public void setOutPlace(Out outPlace) {
        this.outPlace = outPlace;
    }

    public Storage getStorage() {
        return storage;
    }
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Dice getDice() {
        return dice;
    }
    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public Label getPlayerLabel() {
        return playerLabel;
    }

    public void setPlayerLabel(Label playerLabel) {
        this.playerLabel = playerLabel;
        for(String s : playerLabel.getStyleClass())
            labelStyle = s;
    }

    public boolean hasMoves(){
        return hasMoves;
    }
}
