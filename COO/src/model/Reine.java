package model;

public class Reine extends AbstractPiece{
    
    public Reine(Couleur couleur, Coord coord) {
        super(couleur, coord);
    }

    @Override
    public boolean isAlgoMoveOk(int xDest, int yDest) {
        // La reine peut se déplacer en ligne+colonne (= TOUR) + en diagonale (= FOU)

        // donc soit même colonne (x) soit même ligne (y) mais pas les deux en même temps (éviter de ne pas bouger du tout)
        // donc : (x,y) ==> (x+N && y+N) || (x+N && y-N) || (x-N && y+N) || (x-N && y-N)

        int dx = Math.abs(xDest - getX());
        int dy = Math.abs(yDest - getY());

        return (dx == 0 && dy != 0) || (dx != 0 && dy == 0) // TOUR
                || (dx == dy && dx != 0);                   // FOU
    }
}

