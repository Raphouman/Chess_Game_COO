package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import model.Coord;
import model.Couleur;
import model.PieceIHM;
import tools.ChessImageProvider;
import controler.ChessGameControlers;

public class ChessGameGUI extends JFrame implements MouseListener, MouseMotionListener, Observer {

    private static final long serialVersionUID = 1L;

    private ChessGameControlers chessGameControler;
    private Dimension boardSize;

    // Panneau stratifié permettant de superposer plusieurs couches (plateau, case ...)
    private JLayeredPane layeredPane;

    // Plateau du jeu d'échec
    private JPanel chessBoardGuiContainer;

    // Pièce sélectionnée et sa case d'origine
    private JLabel pieceToMove;
    private JPanel pieceToMoveSquare;

    // Map : JPanel (case) → coordonnées logiques sur l'échiquier
    private Map<JPanel, Coord> mapSquareCoord;

    // Tableau 2D des cases, pour rafraîchir l'affichage après chaque déplacement
    private JPanel[][] tab2DJPanel;

    // Décalage souris/pièce pendant le drag
    private int xAdjustment;
    private int yAdjustment;


    public ChessGameGUI(String name, ChessGameControlers chessGameControler, Dimension boardSize) {
        super(name);
        this.initFields(chessGameControler, boardSize);
        this.setLayout();
        this.setListener();
    }


    private void initFields(ChessGameControlers chessGameControler, Dimension boardSize) {
        this.chessGameControler = chessGameControler;
        this.boardSize = boardSize;
        this.layeredPane = new JLayeredPane();
        this.chessBoardGuiContainer = new JPanel();
        this.mapSquareCoord = new HashMap<JPanel, Coord>();
        this.tab2DJPanel = new JPanel[8][8];
    }

    private void setListener() {
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);
    }

    private void setLayout() {
        setContentPane(this.layeredPane);
        this.layeredPane.add(this.chessBoardGuiContainer, JLayeredPane.DEFAULT_LAYER);
        this.chessBoardGuiContainer.setLayout(new GridLayout(8, 8));
        this.chessBoardGuiContainer.setBounds(0, 0, boardSize.width - 10, boardSize.height - 30);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JPanel square = this.newSquare(i, j);
                this.chessBoardGuiContainer.add(square);
                this.mapSquareCoord.put(square, new Coord(j, i));
                tab2DJPanel[j][i] = square;

                JLabel pieceGuiLabel = this.newImage(i, j);
                if (pieceGuiLabel != null) {
                    square.add(pieceGuiLabel);
                }
            }
        }
    }

    private JPanel newSquare(int i, int j) {
        JPanel square = new JPanel(new BorderLayout());
        int row = i % 2;
        if (row == 0) {
            square.setBackground(j % 2 != 0 ? new Color(48, 48, 48) : new Color(242, 247, 255));
        } else {
            square.setBackground(j % 2 != 0 ? new Color(242, 247, 255) : new Color(48, 48, 48));
        }
        return square;
    }

    private JLabel newImage(int i, int j) {
        if (!ChessImageProvider.isCoordOK(i, j)) return null;
        String type = ChessImageProvider.getType(i, j);
        Couleur couleur = ChessImageProvider.getCouleur(i, j);
        return new JLabel(new ImageIcon(ChessImageProvider.getImageFile(type, couleur)));
    }

    // ── Observer ──────────────────────────────────────────────────────────────

    @Override
    @SuppressWarnings("unchecked") // Force le cast sans warning
    public void update(Observable arg0, Object arg1) {  // appelé dans ChessGame.move
        if (!(arg1 instanceof List)) return; // garde-fou
        List<PieceIHM> pieces = (List<PieceIHM>) arg1;  //cast forcé

        // Vider toutes les cases
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                tab2DJPanel[x][y].removeAll();
            }
        }

        // Redessiner chaque pièce à sa position courante dans le modèle
        for (PieceIHM piece : pieces) {
            String type = piece.getTypePiece();
            Couleur couleur = piece.getCouleur();
            for (Coord coord : piece.getList()) {   // une même PieceIHM peut avoir plusieurs positions (2 tours, ...)
                JLabel label = new JLabel(
                        new ImageIcon(ChessImageProvider.getImageFile(type, couleur)));
                tab2DJPanel[coord.x][coord.y].add(label);
            }
        }

        chessBoardGuiContainer.revalidate();    // recalcule les layouts (après removeAll/add)
        chessBoardGuiContainer.repaint();       // redessine à l'écran
    }

    // ── MouseListener / MouseMotionListener ───────────────────────────────────

    @Override
    public void mousePressed(MouseEvent e) {
        pieceToMove = null;
        pieceToMoveSquare = null;

        Component c = chessBoardGuiContainer.getComponentAt(e.getX(), e.getY()); //  Trouve quel JPanel (case) se trouve sous la souris. Les coordonnées sont relatives au layeredPane, qui démarre en (0,0) comme le chessBoardGuiContainer
        if (!(c instanceof JPanel)) return; // si souris hors des cases

        JPanel clickedSquare = (JPanel) c;
        if (clickedSquare.getComponentCount() == 0) return; // si case vide (sans pièce), rien à saisir

        Component comp = clickedSquare.getComponent(0);
        if (!(comp instanceof JLabel)) return;

        pieceToMoveSquare = clickedSquare;  // JPANEL = CASE
        pieceToMove = (JLabel) comp;        // JCOMPONENT (JLABEL) = PIECE

        // Décalage entre le coin haut-gauche de la case et la position de la souris
        xAdjustment = clickedSquare.getX() - e.getX();
        yAdjustment = clickedSquare.getY() - e.getY();

        // Retirer la pièce de sa case et la placer sur la DRAG_LAYER
        clickedSquare.remove(pieceToMove); // Détache visuellement la pièce de sa case d'origine

        // Dans un JLayeredPane il n'y a pas de layout manager : tout est en positionnement absolu, donc on doit donner explicitement taille et position.
        pieceToMove.setSize(clickedSquare.getWidth(), clickedSquare.getHeight());
        pieceToMove.setLocation(clickedSquare.getX(), clickedSquare.getY());

        //  Place la pièce sur la couche de glissement, au-dessus de DEFAULT_LAYER (le plateau) — visuellement elle "survole" l'échiquier.
        layeredPane.add(pieceToMove, JLayeredPane.DRAG_LAYER);
        layeredPane.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (pieceToMove == null) return; // Si on drag sans avoir cliqué sur une pièce (case vide), on ignore.
        pieceToMove.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment); // Repositionne la pièce à chaque pixel de mouvement. L'xAdjustment maintient l'offset calculé au mousePressed pour un drag naturel.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (pieceToMove == null) return;

        // Retirer la pièce de la couche de glissement avant tout rafraîchissement, sinon update() dessine le plateau mais la pièce fantôme reste dessus.
        layeredPane.remove(pieceToMove);

        Component c = chessBoardGuiContainer.getComponentAt(e.getX(), e.getY());
        Coord initCoord = mapSquareCoord.get(pieceToMoveSquare);

        // Déposé hors plateau : repositionner à l'origine
        if (!(c instanceof JPanel) || initCoord == null) {
            pieceToMoveSquare.add(pieceToMove);
            pieceToMoveSquare.revalidate();
            layeredPane.repaint();
            pieceToMove = null;
            pieceToMoveSquare = null;
            return;                         // on appel pas update dans ce cas
        }

        JPanel targetSquare = (JPanel) c;
        Coord finalCoord = mapSquareCoord.get(targetSquare);

        // Vérifier que c'est bien le tour de ce joueur
        // Si ce n'est pas le tour de ce joueur, le contrôleur ne va jamais appeler chessGame.move(), donc notifyObservers ne serait jamais déclenché — on gère manuellement la
        //  remise en place et le message.
        if (!chessGameControler.isPlayerOK(initCoord)) {
            System.out.println("KO : c'est au tour de l'autre joueur");
            pieceToMoveSquare.add(pieceToMove);
            pieceToMoveSquare.revalidate();
            layeredPane.repaint();
            pieceToMove = null;
            pieceToMoveSquare = null;
            return;
        }

        // Tenter le déplacement — déclenche notifyObservers → update()
        chessGameControler.move(initCoord, finalCoord); // declenche ChessGame.move() -> notifyObservers() -> ChessGameGUI.update()
        System.out.println(chessGameControler.getMessage());    // chessGameController.getMessage() -> ... -> echiquier.getMessage()

        pieceToMove = null;
        pieceToMoveSquare = null;
        layeredPane.repaint();  // redessine visuellement le layeredPane
    }

    @Override public void mouseClicked(MouseEvent e)  {}
    @Override public void mouseEntered(MouseEvent e)  {}
    @Override public void mouseExited(MouseEvent e)   {}
    @Override public void mouseMoved(MouseEvent e)    {}


    // Luncher dans LauncherGUI.java
}