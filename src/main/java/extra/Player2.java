package extra;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Player2 {
    private final ArrayList<ImageView> jetons = new ArrayList<>();

    public void addJetons(ImageView... jeton) {
        Collections.addAll(jetons, jeton);
    }

    public Boolean validatePick(ImageView jeton) {
        return jetons.contains(jeton);
    }

    public void setTransparent(boolean transparent) {
        for (ImageView iv : jetons) {
            iv.setMouseTransparent(transparent);
        }
    }

    public Boolean validateStack(Pane parent) {
        ObservableList<Node> list = parent.getChildren();
        if (list.isEmpty()) return true;
        if (list.size() == 1 && !jetons.contains(list.get(0))) return false;
        for (Node node : list) {
            return jetons.contains(node);
        }
        return null;
    }

    public Boolean getOut(Pane parent) {
        ObservableList<Node> list = parent.getChildren();
        if (list.size() == 1 && !jetons.contains(list.get(0)))
            return true;
        return false;
    }

    public Integer dice1ValueChange(Pane parent, Pane parent2, Integer dice1Value) {
        if (Integer.parseInt(parent.getId().substring(1)) - Integer.parseInt(parent2.getId().substring(1)) == dice1Value) {
            return 0;
        } else return dice1Value;
    }

    public Integer dice2ValueChange(Pane parent, Pane parent2, Integer dice2Value) {
        if (Integer.parseInt(parent.getId().substring(1)) - Integer.parseInt(parent2.getId().substring(1)) == dice2Value) {
            return 0;
        } else return dice2Value;
    }

    public Boolean bothDicesValueChange(Pane parent, Pane parent2, Integer dice1Value, Integer dice2Value, Integer dice3Value, Integer dice4Value) {
//        if (Integer.parseInt(parent.getId().substring(1)) - Integer.parseInt(parent2.getId().substring(1)) != dice1Value
//                && Integer.parseInt(parent.getId().substring(1)) - Integer.parseInt(parent2.getId().substring(1)) != dice2Value)
//        {
//            return true;
//        }
//        return  false;


        return dice1Value.equals(dice2Value);

    }
}
