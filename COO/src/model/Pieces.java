package model;

public interface Pieces {
    int getX();
    int getY();

    Couleur getCouleur();


    /**
     * Vérifie si le déplacement est valide (sans prendre en compte les autres pièces)
     * Retourne true si le déplacement est valide pour ce type de pièce, false sinon
     */
    boolean isMoveOk(int xDest, int yDest);
    /**
     * Effectue le déplacement en mettant à jour les coordonnées de la pièce
     * (sans vérifier si le déplacement est valide, c'est à la classe Jeu de vérifier cela avant d'appeler cette méthode)
     * Retourne true si le déplacement a été effectué, false sinon (ex: si la pièce a été capturée et n'est plus en jeu, elle ne peut pas être déplacée)
     */
    boolean move(int xDest, int yDest);


    boolean capture();      // on implémentera plus tard
}
