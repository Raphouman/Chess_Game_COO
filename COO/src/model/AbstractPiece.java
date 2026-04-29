package model;
// pas besoin d'importer Coord, Couleur, Pieces car ils sont dans le même package

public abstract class AbstractPiece implements Pieces {
    protected int x;
    protected int y;
    protected Couleur couleur;

    public AbstractPiece(Couleur couleur, Coord coord) {
        this.couleur = couleur;
        this.x = coord.getX();
        this.y = coord.getY();
    }

    @Override
    public int getX() {return x;}

    @Override
    public int getY() {return y;}

    @Override
    public Couleur getCouleur() {return couleur;}

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + couleur + " en (" + x + ", " + y + ")";
    }

    // Méthode abstraite, car chaque pièce a des règles de déplacement différentes
    protected abstract boolean isAlgoMoveOk(int xDest, int yDest);

    @Override
    public final boolean isMoveOk(int xDest, int yDest){
        if (Coord.coordonnees_valides(xDest, yDest)){
            return isAlgoMoveOk(xDest,yDest);
        }
        return false;
    }


    // C'est à la classe Jeu de vérifier si le déplacement est valide pour la pièce spécifiqueà déplacer, et si oui d'appeler la méthode move générale pour mettre à jour ses coordonnées
    //pas aux Pieces elles-mêmes. on généralise la méthode move pour toutes les pièces
    @Override
    public boolean move(int xDest, int yDest) {
        if (isMoveOk(xDest, yDest)){
            this.x = xDest;
            this.y = yDest;
            return true;
        }
        return false;
    }

    @Override
    public boolean capture() {
        return false;
    }








    //----------------LUNCHER TEST ----------------
    public static void main(String[] args) {
    System.out.println("------------------------------------------------------------------------------");
    System.out.println("------------------------------------------------------------------------------");

    // Test de la classe AbstractPiece et de la classe Tour
    Tour tourBlanche = new Tour(Couleur.NOIR, new Coord(0, 0));
    System.out.println(tourBlanche); // Affiche : Tour BLANC en (0, 0)
    System.out.println(tourBlanche.isMoveOk(0, 5)); // Affiche : true (déplacement vertical)
    System.out.println(tourBlanche.isMoveOk(5, 0)); // Affiche : true (déplacement horizontal)
    System.out.println(tourBlanche.isMoveOk(5, 5)); // Affiche : false (déplacement diagonal)

    System.out.println("------------------------------------------------------------------------------");

    // Test de la classe AbstractPiece et de la classe Fou
    Fou fouNoir = new Fou(Couleur.NOIR, new Coord(2, 0));
    System.out.println(fouNoir); // Affiche : Fou NOIR en (2, 0)
    System.out.println(fouNoir.isMoveOk(5, 3)); // Affiche : true (déplacement diagonal)
    System.out.println(fouNoir.isMoveOk(5, 0)); // Affiche : false (déplacement horizontal)
    
    System.out.println("------------------------------------------------------------------------------");

    // Test de la classe AbstractPiece et de la classe Reine
    Reine reineBlanche = new Reine(Couleur.BLANC, new Coord(4, 0));
    System.out.println(reineBlanche); // Affiche : Reine BLANC en (4, 0)
    System.out.println(reineBlanche.isMoveOk(4, 5)); // Affiche : true (déplacement vertical)
    System.out.println(reineBlanche.isMoveOk(7, 3)); // Affiche : true (déplacement diagonal)
    System.out.println(reineBlanche.isMoveOk(7, 0)); // Affiche : true (déplacement horizontal)
    System.out.println(reineBlanche.isMoveOk(3, 2)); // Affiche : false (déplacement non linéaire)");

    System.out.println("------------------------------------------------------------------------------");

    // Test de la classe AbstractPiece et de la classe Roi
    Roi roiNoir = new Roi(Couleur.NOIR, new Coord(4, 7));
    System.out.println(roiNoir); // Affiche : Roi NOIR en (4, 7)
    System.out.println(roiNoir.isMoveOk(4, 6)); // Affiche : true (déplacement vertical)
    System.out.println(roiNoir.isMoveOk(5, 6)); // Affiche : true (déplacement diagonal)
    System.out.println(roiNoir.isMoveOk(0, 7)); // Affiche : false (déplacement de +d'une case)

    System.out.println("------------------------------------------------------------------------------");
    

    // Test de la classe AbstractPiece et de la classe Pion
    Pion pionBlanc = new Pion(Couleur.BLANC, new Coord(1, 6));
    System.out.println(pionBlanc); // Affiche : Pion BLANC en (1, 6)
    System.out.println(pionBlanc.isMoveOk(1, 5)); // Affiche : true (déplacement vertical)
    System.out.println (pionBlanc.isMoveOk(1, 4)); // Affiche : false (déplacement vertical de 2 cases)
    System.out.println(pionBlanc.isMoveOk(2, 5)); // Affiche : false (déplacement diagonal sans capture)
    }

}


