// ChessImageProvider.java ==> cherche l'image d'une pièce dans images/... (paramètres : type de pièce + couleur)
package tools;	//ca import tout seul tools.ChessPieceImage

import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import model.Coord;
import model.Couleur;

/**
 * @author francoise.perrin
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD
 * 
 * Cette classe s'appuie sur ChessPieceImage
 * pour fournir les noms des images des pièces
 * qui sont utilisées dans l'IHM 
 *  
 */
public class ChessImageProvider {
	
	private static Map<String, String> mapImage;

	static {		//static car on ne veut pas pouvoir modifier les images à l'exécution, elles sont définies
		mapImage = new HashMap<String, String>();
		for (int i = 0; i < ChessPieceImage.values().length; i++) {
			mapImage.put(ChessPieceImage.values()[i].nom, ChessPieceImage.values()[i].imageFile);
		}	
	}

	/**
	 * private pour ne pas instancier d'objets
	 */
	private ChessImageProvider() {

	}	
	
	/**
	 * @param pieceType
	 * @param pieceCouleur
	 * @return ressource contenant l'image de la pièce
	 */
	public static URL getImageFile(String pieceType, Couleur pieceCouleur){
		String key, value;
		key = pieceType + pieceCouleur.name();
		value = mapImage.get(key);
		if (value == null) {
			return null;
		}
		return ChessImageProvider.class.getResource("/images/" + value);	// src/images dans le classpath
	}

	// ------------------------------------------------------------------
	// On rajoute les méthodes appelées dans ChessGameGUI.java dans newImage(i,j) !!!!!!!!
	/**
	 * @param i ligne (y) sur le plateau
	 * @param j colonne (x) sur le plateau
	 * @return true s'il y a une pièce à cette position initiale
	 */
	public static boolean isCoordOK(int i, int j) {
		for (ChessPiecePos pos : ChessPiecePos.values()) {
			for (Coord c : pos.coords) {
				if (c.getX() == j && c.getY() == i) return true;
			}
		}
		return false;
	}

	/**
	 * @param i ligne (y), j colonne (x)
	 * @return le type de la pièce en (i,j) ("Tour", "Pion", etc.)
	 */
	public static String getType(int i, int j) {
		for (ChessPiecePos pos : ChessPiecePos.values()) {
			for (Coord c : pos.coords) {	//boucle pour les pièces ayant plusieurs occurences (tour, fou, cheval, pion ...)
				if (c.getX() == j && c.getY() == i) return pos.nom;
			}
		}
		return null;
	}

	/**
	 * @param i ligne (y), j colonne (x)
	 * @return la couleur de la pièce en (i,j)
	 */
	public static Couleur getCouleur(int i, int j) {
		for (ChessPiecePos pos : ChessPiecePos.values()) {
			for (Coord c : pos.coords) {	//boucle pour les pièces ayant plusieurs occurences (tour, fou, cheval, pion ...)
				if (c.getX() == j && c.getY() == i) return pos.couleur;
			}
		}
		return null;
	}

	/**
	 * Test unitaires
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(ChessImageProvider.getImageFile("Cavalier", Couleur.BLANC));
	}

}
