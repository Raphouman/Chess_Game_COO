package model;

public class Pion extends AbstractPiece implements Pions {

    public Pion(Couleur couleur, Coord coord) {
        super(couleur, coord);
    }

    @Override
    public boolean isAlgoMoveOk(int xDest, int yDest) {

        // On suppose l'origine du plateau (0,0) en haut à gauche, donc les pions blancs avancent vers le haut (y-1) et les pions noirs vers le bas (y+1)
        // si il est BLANC il doit avancer vers le haut (y-1) sinon vers le bas (y+1)
        int direction = (couleur == Couleur.BLANC) ? -1 : 1;
        return (x == xDest) && (yDest - y == direction);
    }

    @Override
    public boolean isCaptureMove(int xDest, int yDest) {    //ou isMoveDiagOk
        // PAS POUR MAINTENANT
        return false;
    }
}