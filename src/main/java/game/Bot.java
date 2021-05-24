package game;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot extends Player{
    private List<Container> allowMove = new ArrayList<>();
    private List<Move> moves = new ArrayList<>();
    private Random random;

    public Bot(List<Piece> pieces, List<Spike> spikes, Out outPlace, Storage storage, String type, Dice dice) {
        this.pieces = pieces;
        this.spikes = spikes;
        this.outPlace = outPlace;
        this.storage = storage;
        this.type = type;
        this.dice = dice;
        random = new Random();

        storage.representation.setMouseTransparent(true);
    }

    public List<Container> getPossibleMoves() {
        List<Container> containers = new ArrayList<>();

        int count  = 0;
        int size = spikes.size();

        removeSpikeHigh();
        if(this.outPlace.pieces.size() == 0) {
            for (int i = 0; i < size; i++) {
                if (canBeMoved(i, this.type) > 0 && spikes.get(i).type == type) {
                    count++;
                    spikes.get(i).showPiece(true);
//                    spikes.get(i).getRepresentation().setMouseTransparent(false);
                    containers.add(spikes.get(i));
                }
            }
        } else {
            highOutPlace();
            count = outPlace.getHasMoves();
            containers.add(outPlace);
        }


        if(count == 0)
            hasMoves = false;
        else
            hasMoves = true;

        return containers;
    }

    public List<Container> getPossiblePlaces(int from) {
        List<Container> movePlaces = new ArrayList<>();

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
//                    spikes.get(size - pos1).getRepresentation().setMouseTransparent(false);
                    movePlaces.add(spikes.get(size - pos1));
                    count++;
                }
                if (spikes.get(size - pos1).getPieces().size() == 1) {
                    spikes.get(size - pos1).show(true);
//                    spikes.get(size - pos1).getRepresentation().setMouseTransparent(false);
                    movePlaces.add(spikes.get(size - pos1));
                    count++;
                }
            }

            if (pos2 <= size && dice2 > 0) {
                if (spikes.get(size - pos2).getType() == type || spikes.get(size - pos2).getType() == "none") {
                    spikes.get(size - pos2).show(true);
//                    spikes.get(size - pos2).getRepresentation().setMouseTransparent(false);
                    movePlaces.add(spikes.get(size - pos2));
                    count++;
                }
                if (spikes.get(size - pos2).getPieces().size() == 1) {
                    spikes.get(size - pos2).show(true);
//                    spikes.get(size - pos2).getRepresentation().setMouseTransparent(false);
                    movePlaces.add(spikes.get(size - pos2));
                    count++;
                }
            }

            if (isHome()) {
                boolean isEmptyD1 = false;
                boolean isEmptyD2 = false;

                if(dice1 != 0) {
                    if (spikes.get(dice1 - 1).getPieces().size() == 0) {
                        isEmptyD1 = true;
                        for (int i = 6; i > from && isEmptyD1; i--) {
                            if (spikes.get(i).getPieces().size() != 0)
                                isEmptyD1 = false;
                        }
                    }
                }

                if(dice2 != 0) {
                    if (spikes.get(dice2 - 1).getPieces().size() == 0) {
                        isEmptyD2 = true;
                        for (int i = 6; i > from && isEmptyD2; i--) {
                            if (spikes.get(i).getPieces().size() != 0)
                                isEmptyD2 = false;
                        }
                    }
                }

                if (dice1 == (size - from) || dice2 == (size - from) || (dice1 > (size - from) && isEmptyD1) || (dice2 > (size - from) && isEmptyD2)) {
                    System.out.println("Zize-from: " + (size-from));
                    storage.show(true);
                    movePlaces.add(storage);
                    count++;
//                    storage.getRepresentation().setMouseTransparent(false);
                }

            }
        }
        return movePlaces;
    }

    public void makeMove(){
        allowMove = getPossibleMoves();

        if(allowMove.size() > 0){
            if(allowMove.get(0) instanceof Out){
                moves.add(new Move(allowMove.get(0), getPossiblePlaces(-1)));
            } else {
                for(Container c : allowMove){
                    if(c instanceof Spike) {
                        Spike s = (Spike) c;
                        moves.add(new Move(c, getPossiblePlaces(23 - s.getOrderNumber())));
                    }
                }
            }
        }
        if(moves.size() > 0) {
            int move = random.nextInt(moves.size());
            if(moves.get(move).getSpikes().size() > 0) {

                Container from = moves.get(move).getPiece();
                Container where = moves.get(move).getRandomMove();

                if (from instanceof Out) {
                    from.move(where);
                    moves.clear();
                    Spike w = (Spike) where;
                    dice.consume(24 - w.getOrderNumber());
                } else if (where instanceof Storage){
                    if(from.getPieces().size() > 0) {
                        Spike f = (Spike) from;
                        Storage w = (Storage) where;
                        w.storageOverlap(false);
                        w.addInStorage(f.pieces.get(f.pieces.size() - 1));
                        f.overlap(true);
                        if (dice.getValueDice1() != (24 - f.getOrderNumber()) && dice.getValueDice2() != (24 - f.getOrderNumber())) {
                            if (dice.getValueDice1() > dice.getValueDice2())
                                dice.consume(dice.getValueDice1());
                            else
                                dice.consume(dice.getValueDice2());
                        } else {
                            this.dice.consume(24 - f.getOrderNumber());
                        }
                        moves.clear();
                    }
                } else {
                    from.move(where);
                    moves.clear();
                    Spike f = (Spike) from;
                    Spike w = (Spike) where;
                    dice.consume((f.getOrderNumber() - w.getOrderNumber()));
                }
            }
        } else {
            hasMoves = false;
            dice.consumeAll();
        }
    }
}
