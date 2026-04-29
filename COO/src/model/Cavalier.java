package model;

public class Cavalier extends AbstractPiece{
    
    public Cavalier(Couleur couleur, Coord coord) {
        super(couleur, coord);
    }

    @Override
    public boolean isAlgoMoveOk(int xDest, int yDest) {
        // Le cavalier peut se déplacer en L
        // donc : (x,y) ==> (x+1 && y+2) || (x+1 && y-2) || (x-1 && y+2) || (x-1 && y-2)
        // ou   : (x,y) ==> (x+2 && y+1) || (x+2 && y-1) || (x-2 && y+1) || (x-2 && y-1)

        int dx = Math.abs(xDest - getX());
        int dy = Math.abs(yDest - getY());

        return ( (dx == 1 && dy == 2) || (dx == 2 && dy == 1 ) );
    }
}
