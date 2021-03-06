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

    private final ArrayList<Pane> panes = new ArrayList<>();

    public void addPanes(Pane... pane) {
        Collections.addAll(panes, pane);
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
    public Boolean validateStack2(Pane parent)
    {
        if(parent.getChildren()!=null) {
            ObservableList<Node> list = parent.getChildren();

            if (list.isEmpty()) return true;
            if (list.size() == 1 && !jetons.contains(list.get(0))) return true;

            for (Node node : list) {
                return jetons.contains(node);
            }
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
    public Boolean checkFinal()
    {
        int counter = 0;
        for (Pane pane: panes) {
            ObservableList<Node> list =  pane.getChildren();
            for (ImageView jeton: jetons) {
                if(list.contains(jeton)) counter++;
            }
        }
        System.out.println(counter);
        if(counter==15) return true;
        return false;
    }
    public ArrayList<Pane> getPanes()
    {
        return panes;
    }

    public Boolean checkPane(Node jeton)
    {
        ImageView jeton2 = (ImageView)  jeton;
        return jetons.contains(jeton2);
    }
}
