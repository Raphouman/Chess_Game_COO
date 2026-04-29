package model;

import java.util.LinkedList;
import java.util.List;
import tools.ChessPiecesFactory;

public class Jeu {    //pas abstract car instance de Jeu 
// il y en a 2, il est défini par sa couleur et les pièces qui le composent

    private List<Pieces> pieces;
    private Couleur couleur;

    public Jeu(Couleur couleur){
        this.couleur = couleur;
        this.pieces = ChessPiecesFactory.newPieces(couleur);
    }



    //----------------------- FONCTIONS DE BASE -----------------------------------

    public Pieces findPiece(int x, int y){          // <==> isPieceHere(x,y)
        for (Pieces p : pieces){
            int x_Piece = p.getX();
            int y_Piece = p.getY();
            if ((x_Piece == x) && (y_Piece == y)){
                return p;
            }
        }
        return null;    //obligatoire car on a spécifié que la fonction renvoyait un objet de type Pieces
    }



    public Couleur getCouleur() {   //retourne la couleur du jeu, c'est à dire la couleur des pièces qui le composent
        return this.couleur;
    }   //couleur du jeu

    public Coord getKingCoord(){
        for (Pieces p : pieces){
            if (p instanceof Roi){
                return new Coord(p.getX(), p.getY()); // on renvoie un objet de type Coord, ici une COPIE pour éviter de pouvoir modif les coords du roi
            }
        }
        return null;    //obligatoire car on a spécifié que la fonction renvoyait un objet de type Coord
    }


    //----------------------------------------------------

    public Couleur getPieceColor(int x, int y){
        for (Pieces p : pieces){
            int x_Piece = p.getX();
            int y_Piece = p.getY();
            if ((x_Piece == x) && (y_Piece == y)){
                return p.getCouleur();
            }
        }
        return null;    //obligatoire car on a spécifié que la fonction renvoyait un objet de type Couleur
    }
    public String getPieceType(int x, int y){
        for (Pieces p : pieces){
            int x_Piece = p.getX();
            int y_Piece = p.getY();
            if ((x_Piece == x) && (y_Piece == y)){
                return p.getClass().getSimpleName();  //getClass() retourne la classe de l'objet, getSimpleName() retourne le nom de la classe sans le package
            }
        }
        return null;    //obligatoire car on a spécifié que la fonction renvoyait un objet de type String
    }

    // ------------------------------------------------------------------

    public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal){
        Pieces p_to_move = findPiece(xInit, yInit);
        if (p_to_move == null){return false;}
        return p_to_move.isMoveOk(xFinal, yFinal);   //appel isMoveOk de la classe de la pièce à déplacer (passe par Abstractpiece), qui vérifie si le déplacement est valide généralement PUIS pour ce type de pièce spécifique (la sous classe)
    }
    public boolean move(int xInit, int yInit, int xFinal, int yFinal){
        if (isMoveOk(xInit, yInit, xFinal, yFinal)){
            Pieces p_to_move = findPiece(xInit, yInit);
            p_to_move.move(xFinal, yFinal);   //appel move de la classe AbstractPiece qui met à jour les coordonnées de la pièce
            return true;
        }
        return false;
    }

    //-----------------------------------------------------------------------


    public boolean isPawnPromotion(int xFinal, int yFinal) {
        Pieces p = findPiece(xFinal, yFinal);

        if (p instanceof Pion) {
            if (p.getCouleur() == Couleur.BLANC && yFinal == 0) return true;
            if (p.getCouleur() == Couleur.NOIR && yFinal == 7) return true;
        }
        return false;
    }

    public boolean pawnPromotion(int xFinal, int yFinal, String newPieceType){
        if (isPawnPromotion(xFinal, yFinal)){
            for (Pieces p : pieces){
                if (p instanceof Pion){
                    if ((p.getX() == xFinal) && (p.getY() == yFinal)){

                        pieces.remove(p);   //on retire le pion promu de la liste des pièces du jeu pour le remplacer par la nouvelle pièce choisie par le joueur
                        // A FAIRE  : créer une nouvelle pièce du type choisi par le joueur (ex : Dame) avec les mêmes coordonnées que le pion promu et l'ajouter à la liste des pièces du jeu
                        // Pieces newPiece = ChessSinglePieceFactory.createPiece(newPieceType, p.getCouleur(), new Coord(x, y));
                    }
                }
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------

    public boolean setCastling(){   // maj un boolean dans le jeu qui indique si le roque est encore possible ou pas (si le roi ou la tour ont bougé, ou si le roi est en échec, ou si les cases entre le roi et la tour sont occupées ou attaquées par une pièce adverse)
        // @TODO plus tard
        return false;
    }





    //----------------------- FONCTIONS POUR IHM -----------------------------------
    /**
    * @return une vue de la liste des pièces en cours
    * ne donnant que des accès en lecture sur des PieceIHM
    * (type piece + couleur + liste de coordonnées)
    */
    public List<PieceIHM> getPiecesIHM(){
        PieceIHM newPieceIHM = null;
        List<PieceIHM> list = new LinkedList<PieceIHM>();
        for (Pieces piece : pieces){
            boolean existe = false;
            // si le type de piece existe déjà dans la liste de PieceIHM
            // ajout des coordonnées de la pièce dans la liste de Coord de ce type
            // si elle est toujours en jeu (x et y != -1)
            for ( PieceIHM pieceIHM : list){
                if ((pieceIHM.getTypePiece()).equals(piece.getClass().getSimpleName())){  
                    existe = true;
                    if (piece.getX() != -1){   
                        pieceIHM.add(new Coord(piece.getX(), piece.getY()));
                    }               
                }
            }
            // sinon, création d'une nouvelle PieceIHM si la pièce est toujours en jeu
            if (! existe) {
                if (piece.getX() != -1){
                    newPieceIHM = new PieceIHM(piece.getClass().getSimpleName(),
                                                            piece.getCouleur());
                    newPieceIHM.add(new Coord(piece.getX(), piece.getY()));
                    list.add(newPieceIHM);
                }
            }
        }
        return list;
    }


    //@TODO plus tard
    // public boolean capture()
    // public void setPossibleCapture()  // Si une capture d'une pièce de l'autre jeu est possible met à jour 1 booléen
    //public void undoMove()  // annule le dernier déplacement effectué (pour la fonction "annuler" de l'IHM)
    //public void undoCapture()  // annule la dernière capture effectuée (pour la fonction "annuler" de l'IHM)





    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();  // StringBuilder est plus efficace que String pour construire une chaîne de caractères à partir de plusieurs éléments, car il évite de créer plusieurs objets String intermédiaires lors de la concaténation
        sb.append("\nJeu ").append(couleur).append(" :\n \n");

        for (Pieces p : pieces) {
            sb.append(p.toString()).append("\n");       //appel la méthode toString de la classe AbstractPiece (de chaque pièce qui en hérite en réalité)
        }
        return sb.toString();   // toString de StringBuilder qui retourne la chaîne de caractères construite (ex : "a" + "b" == "ab")
    }

    
    //----------------LUNCHER TEST ----------------
    public static void main(String[] args) {
        System.out.println("------------------------------------------------------------------------------");
        Jeu jeuBlanc = new Jeu(Couleur.BLANC);
        System.out.println(jeuBlanc); // Affiche la liste des pièces blanches avec leurs positions initiales



    
    }

}
