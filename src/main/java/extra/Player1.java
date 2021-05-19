package extra;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player1 {
    private final ArrayList<ImageView> jetons = new ArrayList<>();

    public void addJetons(ImageView... jeton) {
        Collections.addAll(jetons, jeton);
    }

    public Boolean validatePick(ImageView jeton)
    {
        return jetons.contains(jeton);
    }

    public void setTransparent(boolean transparent){
        for(ImageView iv : jetons){
            iv.setMouseTransparent(transparent);
        }
    }

    public Boolean validateStack(Pane parent, String message, Pane middleBlack)
    {
       ObservableList<Node> list =  parent.getChildren();
       if(list.isEmpty()) return true;
       if(list.size()==1 && !jetons.contains(list.get(0)))
       {

           message = message.concat(middleBlack.getId()).concat("^").concat(list.get(0).getId()).concat("^").concat(String.valueOf(0)).concat("^").concat(String.valueOf(150)).concat("^");
           middleBlack.getChildren().add(list.get(0));
           return true;
       }
        for (Node node : list) {
            return jetons.contains(node);
        }
        return null;
    }

}
