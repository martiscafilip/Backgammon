package extra;

import javafx.scene.image.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Player2 {
    private final ArrayList<ImageView> jetons = new ArrayList<>();

    public void addJetons(ImageView... jeton) {
        Collections.addAll(jetons, jeton);
    }

    public Boolean validatePick(ImageView jeton)
    {
        return jetons.contains(jeton);
    }

}
