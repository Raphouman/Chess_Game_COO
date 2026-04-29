package tools;

import java.util.LinkedList;
import java.util.List;
import model.Coord;
import model.Couleur;
import model.Pieces;

/**
 * @author francoise.perrin
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD
 * 
 * Classe qui fabrique une liste de pieces de jeu d'echec
 * de la couleur pass�e en param�tre
 *
 */
public class ChessPiecesFactory {

	/**
	 * private pour ne pas instancier d'objets car cette classe ne contient que des méthodes statiques
	 */
	private ChessPiecesFactory() {

	}

	/**
	 * @param pieceCouleur
	 * @return liste de pièces de jeu d'échec de la couleur passée en paramètre
	 */
	public static List<Pieces> newPieces(Couleur pieceCouleur){

		List<Pieces> pieces = null;
		pieces = new LinkedList<Pieces>();	//linkedlist pour éviter les problèmes de redimensionnement d'arraylist
		String initCouleur = (Couleur.BLANC == pieceCouleur ? "B_" : "N_" );	//utilisé dans ChessPiecePos

		if (pieceCouleur != null){
			for (int i = 0; i < ChessPiecePos.values().length; i++) {	//lorsqu'on appel ChessPiecePos.values(), on récupère un tableau de tous les éléments de l'enum ChessPiecePos 
			// et ca aura appelé le constructeur ChessPiecePos de chaque élément de l'enum, ce qui aura initialisé les champs nom, couleur et coords de chaque élément de l'enum

				if (pieceCouleur.equals(ChessPiecePos.values()[i].couleur)) {
					for (int j = 0; j < (ChessPiecePos.values()[i].coords).length; j++) {
						String className = "model." + ChessPiecePos.values()[i].nom;	// attention au chemin "model." pour que l'introspection puisse trouver la classe 
						Coord pieceCoord = ChessPiecePos.values()[i].coords[j];

// On utilise l'introspection pour créer une instance de la classe correspondant à la pièce appelée, en passant la couleur et les coordonnées en paramètres du constructeur
						pieces.add((Pieces) Introspection.newInstance (className,
								new Object[] {pieceCouleur, pieceCoord}));
					}
				}
			}
		}
		return pieces;
	}

	/**
	 * Tests unitaires
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println(ChessPiecesFactory.newPieces(Couleur.BLANC));	//affiche le return de newPieces
	}
}
