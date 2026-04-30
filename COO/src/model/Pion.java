package model;

public class Pion extends AbstractPiece implements Pions {

    public Pion(Couleur couleur, Coord coord) {
        super(couleur, coord);
    }

    @Override
    public boolean isAlgoMoveOk(int xDest, int yDest) {

        // On suppose l'origine du plateau (0,0) en haut à gauche, donc les pions blancs avancent vers le haut (y-1) et les pions noirs vers le bas (y+1)
        // si il est BLANC il doit avancer vers le haut (y-1) sinon vers le bas (y+1)
        //mais attention, si sur ligne de déart alors on peut avancer de 2 !!!!
        int direction = (couleur == Couleur.BLANC) ? -1 : 1;
        int ligneDepart = (couleur == Couleur.BLANC) ? 6 : 1;

        if (x != xDest) return false;                                           // doit rester dans la même colonne
        if (yDest - y == direction) return true;                                // avance d'1 case
        if (y == ligneDepart && yDest - y == 2 * direction) return true;        // avance de 2 depuis la ligne de départ
        return false;
    }

    @Override
    public boolean isCaptureMove(int xDest, int yDest) {
        int direction = (couleur == Couleur.BLANC) ? -1 : 1;
        return (Math.abs(xDest - x) == 1) && (yDest - y == direction);  // Selon x ca peut etre -1 ou +1, mais selon y ca depend de la couleur
    }
}