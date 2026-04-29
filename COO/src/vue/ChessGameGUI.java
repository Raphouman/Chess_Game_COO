package vue;

import java.awt.BorderLayout;
import java.awt.Color;
// import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
// import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
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

                // On construit l'image de chaque pièce en fonction de sa position initiale
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

    // Methode graphique permettant de construire l'image/icone d'un objet ==> sa version graphique (avec comme param = url de l'image)
    private JLabel newImage(int i, int j) {
        JLabel pieceGuiLabel = null;
        if (ChessImageProvider.isCoordOK(i, j)) {       //chek si `JLabel pieceGuiLabel = this.newImage(i, j);` a bien marché
            String type    = ChessImageProvider.getType(i, j);
            Couleur couleur = ChessImageProvider.getCouleur(i, j);
            pieceGuiLabel  = new JLabel(
                    new ImageIcon(ChessImageProvider.getImageFile(type, couleur)));
        }
        return pieceGuiLabel;
    }

    // ── Observer ──────────────────────────────────────────────────────────────

    @Override
    public void update(Observable arg0, Object arg1) {
        // TODO : rafraîchir l'affichage des pièces après un déplacement
    }

    // ── MouseListener / MouseMotionListener ───────────────────────────────────

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO : saisir la pièce sous la souris
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO : déplacer la pièce avec la souris
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO : déposer la pièce et appeler le contrôleur
    }

    @Override public void mouseClicked(MouseEvent e)  {}
    @Override public void mouseEntered(MouseEvent e)  {}
    @Override public void mouseExited(MouseEvent e)   {}
    @Override public void mouseMoved(MouseEvent e)    {}


    // Luncher dans LauncherGUI.java
}