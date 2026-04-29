package model;

public class Roi extends AbstractPiece{
    
    public Roi(Couleur couleur, Coord coord) {
        super(couleur, coord);
    }

    @Override
    public boolean isAlgoMoveOk(int xDest, int yDest) {
        // Le roi peut se déplacer d'une case dans n'importe quelle direction
        int dx = Math.abs(xDest - getX());
        int dy = Math.abs(yDest - getY());

        return (dx <= 1 && dy <= 1 && !(dx == 0 && dy == 0));    // != 0 pour éviter de ne pas bouger du tout
    }
}
