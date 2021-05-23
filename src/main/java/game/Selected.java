package game;


public class Selected {
    private static Piece selected = null;
    private static Container from = null;
    public static Board board;

    public static int offsetX = Sizes.GRID_SIZE/2;
    public static int offsetY = Sizes.GRID_SIZE/2;

    public static void select(Piece piece){
        selected = piece;
        from = piece.getPlace();
//        piece.getPlace().removePiece(piece);
    }

    public static void deselect(){
        selected = null;
        from = null;
    }

    public static Piece getSelected() {
        return selected;
    }
    public static void setSelected(Piece selected) {
        Selected.selected = selected;
    }

    public static Container getFrom() {
        return from;
    }
    public static void setFrom(Container from) {
        Selected.from = from;
    }
}
