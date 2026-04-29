package model;

import java.util.LinkedList;
import java.util.List;

public class Echiquier implements BoardGames{
    //Echiquier passe par les Jeu, il gère les 2 jeux, le tour de jeu, la validation/éxécution des déplacements
    //Echiquier ne gère PAS directement les pièces

    private Jeu jeuBlanc;
    private Jeu jeuNoir;
    private Jeu jeuCourant;         //on ne travaille pas avec des attributs, car la compléxité serait trop grande
    private Jeu jeuAdverse;         //là on ne stocke que des poineturs (adresses mémoires des jeux)

    private String message;

        //Constructeur
    public Echiquier() {
        jeuBlanc = new Jeu(Couleur.BLANC);
        jeuNoir = new Jeu(Couleur.NOIR);

        jeuCourant = jeuBlanc;  //par défaut au début, le jeu blanc commence
        jeuAdverse = jeuNoir;

        message = "Les jeux sont créés";
    }

    @Override
    public Couleur getColorCurrentPlayer() {
        //@TODO
        return jeuCourant.getCouleur();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
    private void setMessage(String newMessage) {
        this.message = newMessage;
    }

    @Override
    public Couleur getPieceColor(int x, int y) {
        Couleur couleur_piece =  jeuCourant.getPieceColor(x,y);

        if (couleur_piece != null){return couleur_piece;}   //si le jeu courant la trouve
        else {return jeuAdverse.getPieceColor(x,y);}        //sinon, on cherche dans le jeu adverse
    }

    //retourne une liste de pièces IHM fabriquées à partir de celle des 2 Jeux
    public List<PieceIHM> getPiecesIHM() {
        List<PieceIHM> list = new LinkedList<>();
        list.addAll(jeuBlanc.getPiecesIHM());
        list.addAll(jeuNoir.getPiecesIHM());

        return list;
    }

    @Override
    public boolean isEnd() {
        //@TODO
        return false;
    }


    public boolean isMoveOk(int xSrc, int ySrc, int xDest, int yDest) {

        // 1. pièce présente ?
        if (jeuCourant.findPiece(xSrc, ySrc) == null) {
            setMessage("KO : pas de pièce à cette position");
            return false;
        }

        // 2. destination différente ?
        if (xSrc == xDest && ySrc == yDest) {
            setMessage("KO : même position");
            return false;
        }

        // 3. mouvement valide pour la pièce (et dans le plateau) ?
        if (!jeuCourant.isMoveOk(xSrc, ySrc, xDest, yDest)) {
            setMessage("KO : mouvement interdit pour cette pièce");
            return false;
        }

        //️ PAS ENCORE :
        // - obstacles
        // - captures

        setMessage("OK : déplacement simple");
        return true;
    }


    @Override
    public boolean move(int xSrc, int ySrc, int xDest, int yDest) {
        if (!isMoveOk(xSrc, ySrc, xDest, yDest)) {
            return false;
        }

        boolean moved = jeuCourant.move(xSrc, ySrc, xDest, yDest);
        if (moved) {switchJoueur();}
        return moved;
    }

    public void switchJoueur() {
        if (jeuCourant == jeuBlanc) {
            jeuCourant = jeuNoir;
            jeuAdverse = jeuBlanc;
        } else {
            jeuCourant = jeuBlanc;
            jeuAdverse = jeuNoir;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Jeu Blanc:\n").append(jeuBlanc.toString());
        sb.append("Jeu Noir:\n").append(jeuNoir.toString());

        return sb.toString();
    }

}
