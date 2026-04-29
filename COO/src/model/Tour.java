package model;

public class Tour extends AbstractPiece{
    
    public Tour(Couleur couleur, Coord coord) {
        super(couleur, coord);
    }

    @Override
    protected boolean isAlgoMoveOk(int xDest, int yDest) {
        // La tour peut se déplacer horizontalement ou verticalement
        // donc soit même colonne (x) soit même ligne (y) mais pas les deux en même temps (éviter de ne pas bouger du tout)
        int dx = Math.abs(xDest - getX());
        int dy = Math.abs(yDest - getY());
        
        return (dx == 0 && dy != 0) || (dx != 0 && dy == 0);    // = N != 0 pour éviter de ne pas bouger du tout
    }
}
