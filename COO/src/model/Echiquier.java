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

    // --- Helpers échec / mat / pat ---

    private boolean isPathBlocked(int xSrc, int ySrc, int xDest, int yDest) {
        int dx = Integer.signum(xDest - xSrc);  // signum(n) : =-1 si n<0, =0 si n=0, =1 si n>0
        int dy = Integer.signum(yDest - ySrc);
        int cx = xSrc + dx;
        int cy = ySrc + dy;
        while (cx != xDest || cy != yDest) {
            if (jeuBlanc.findPiece(cx, cy) != null || jeuNoir.findPiece(cx, cy) != null) return true;
            cx += dx;
            cy += dy;
        }
        return false;
    }

    private boolean isSquareAttacked(int x, int y, Jeu attacker) {  // est ce que le roi est en échec
        for (Pieces p : attacker.getPieces()) {             // on récup les pièces adverses
            if ((p.getX() == -1) && (p.getY() == -1)) continue;
            if (p instanceof Pion) {
                if (((Pion) p).isCaptureMove(x, y)) return true;    // on regarde si c'est un pion qui prend le roi
            } else if (p instanceof Cavalier) {                 // on regarde si c'est le cavalier (pas de chek de si le chemin est bloqué)
                if (p.isMoveOk(x, y)) return true;
            } else {                // pour toutes autres pièces on regarde si le mouvement sur la case du roi est possible et si le chemin n'est pas bloqué
                if (p.isMoveOk(x, y) && !isPathBlocked(p.getX(), p.getY(), x, y)) return true;
            }
        }
        return false;
    }

    public boolean isInCheck(Jeu jeu, Jeu adversaire) {         //la fonction qu'on va appeler pour vérif si il est en échec ou non
        Coord king = jeu.getKingCoord();
        if (king == null) return false;
        return isSquareAttacked(king.getX(), king.getY(), adversaire);
    }

    // appelé dans isMoveOk (étape 7)
    private boolean doesNotLeaveKingInCheck(int xSrc, int ySrc, int xDest, int yDest) { // Quand le roi et en echec, est ce que le mouvement le laisse en echec ou le sauve ?
        Pieces moving = jeuCourant.findPiece(xSrc, ySrc);   //le jeu courant : où le roi est actuellement  en echec !
        Pieces captured = jeuAdverse.findPiece(xDest, yDest);
        int capX = (captured != null) ? captured.getX() : 0;    //on récup les coords de la pièce à capturer
        int capY = (captured != null) ? captured.getY() : 0;

        ((AbstractPiece) moving).x = xDest;         // on SIMULE et on déplace vraiment la pièce du jeu courant
        ((AbstractPiece) moving).y = yDest;
        if (captured != null) {                     // si il y a bien une pièce à capturer, on SIMULE en la mettant avec les autres pièces capturées (hors du jeu)
            ((AbstractPiece) captured).x = -1;
            ((AbstractPiece) captured).y = -1;
        }

        boolean safe = !isInCheck(jeuCourant, jeuAdverse);  // on CHEK si le roi est en echec après simulation (donc le reste)

        // On oublie pas de remettre les pièces à leur place d'avant la SIMULATION (ce n'est pas cette méthode qui gère les vrais déplacements !!!)
        ((AbstractPiece) moving).x = xSrc;
        ((AbstractPiece) moving).y = ySrc;
        if (captured != null) {
            ((AbstractPiece) captured).x = capX;
            ((AbstractPiece) captured).y = capY;
        }
        return safe;
    }

    // appelée dans isEnd()
    // vérifie si il existe un déplacement légal ou si il y a une victoire de l'adversaire
    private boolean hasLegalMove() {
        String saved = this.message;
        for (Pieces p : jeuCourant.getPieces()) {
            if (p.getX() == -1) continue;
            for (int xDest = 0; xDest < 8; xDest++) {
                for (int yDest = 0; yDest < 8; yDest++) {
                    if (isMoveOk(p.getX(), p.getY(), xDest, yDest)) {   //on test tous les mouvements possibles et imaginables
                        this.message = saved;
                        return true;
                    }
                }
            }
        }
        this.message = saved;
        return false;
    }

    @Override
    public boolean isEnd() {
        if (!hasLegalMove()) {  //si il n' y a plus de move possible/légal
            if (isInCheck(jeuCourant, jeuAdverse)) {    //si il y a échec
                setMessage("ÉCHEC ET MAT ! " + jeuAdverse.getCouleur() + " gagne !");
            } else {    //sinon match null (aucun coup légal des 2 cotés mais pas d'echec et MAT)
                setMessage("PAT ! Match nul.");
            }
            return true;
        }
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
        Pieces movedPiece = jeuCourant.findPiece(xSrc, ySrc);
        boolean isNormalMove  = jeuCourant.isMoveOk(xSrc, ySrc, xDest, yDest);
        boolean isPawnCapture = (movedPiece instanceof Pion)
                && ((Pion) movedPiece).isCaptureMove(xDest, yDest)
                && jeuAdverse.findPiece(xDest, yDest) != null;

        if (!isNormalMove && !isPawnCapture) {
            setMessage("KO : mouvement interdit pour cette pièce");
            return false;
        }
        // Le pion ne peut pas capturer en avançant tout droit
        if (isNormalMove && movedPiece instanceof Pion && jeuAdverse.findPiece(xDest, yDest) != null) {
            setMessage("KO : le pion ne peut pas capturer en avançant");
            return false;
        }

        // 4. aucune pièce (alliée ou adverse) ne bloque le trajet intermédiaire
        if (!(movedPiece instanceof Cavalier) && isPathBlocked(xSrc, ySrc, xDest, yDest)) {
            setMessage("KO : pièce sur le chemin");
            return false;
        }

        // 5. destination pas occupée par une pièce alliée ?
        if (jeuCourant.findPiece(xDest, yDest) != null) {
            setMessage("KO : case occupée par une pièce alliée");
            return false;
        }

        // 6. capture si pièce adverse à destination
        if (jeuAdverse.findPiece(xDest, yDest) != null) {
            setMessage("OK : déplacement + capture");
        } else {
            setMessage("OK : déplacement simple");
        }

        // 7. le mouvement ne doit pas laisser le roi en échec
        if (!doesNotLeaveKingInCheck(xSrc, ySrc, xDest, yDest)) {
            setMessage("KO : mouvement illégal, votre roi serait en échec");
            return false;
        }
        return true;
    }


    @Override
    public boolean move(int xSrc, int ySrc, int xDest, int yDest) {
        if (!isMoveOk(xSrc, ySrc, xDest, yDest)) {
            return false;
        }

        // Capturer la pièce adverse si la destination est occupée
        if (jeuAdverse.findPiece(xDest, yDest) != null) {
            jeuAdverse.capture(xDest, yDest);
        }

        boolean moved = jeuCourant.move(xSrc, ySrc, xDest, yDest);
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
        if (isInCheck(jeuCourant, jeuAdverse)) {
            setMessage("ÉCHEC au roi " + jeuCourant.getCouleur() + " !");
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
