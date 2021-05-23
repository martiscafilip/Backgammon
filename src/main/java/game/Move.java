package game;

import java.util.List;
import java.util.Random;

public class Move {
    private Container piece;
    private List<Container> spikes;
    private Random random;

    public Move(Container piece, List<Container> spikes) {
        this.piece = piece;
        this.spikes = spikes;
        random = new Random();
    }

    public void makeRandomMove(){
        int where;
        where = random.nextInt(spikes.size());

        piece.move(spikes.get(where));
    }

    public Container getRandomMove(){

        int where;
        where = random.nextInt(spikes.size());

        return spikes.get(where);
    }

    public Container getPiece() {
        return piece;
    }

    public void setPiece(Container piece) {
        this.piece = piece;
    }

    public List<Container> getSpikes() {
        return spikes;
    }

    public void setSpikes(List<Container> spikes) {
        this.spikes = spikes;
    }
}
