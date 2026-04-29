package model;

public class Fou extends AbstractPiece{
    
    public Fou(Couleur couleur, Coord coord) {
        super(couleur, coord);
    }

    @Override
    public boolean isAlgoMoveOk(int xDest, int yDest) {
        // Le Fou peut se déplacer en diagonale
        // donc : (x,y) ==> (x+N && y+N) || (x+N && y-N) || (x-N && y+N) || (x-N && y-N)
        int dx = Math.abs(xDest - getX());
        int dy = Math.abs(yDest - getY());

        return (dx == dy && dx != 0);    // = N != 0 pour éviter de ne pas bouger du tout 
    }
}
