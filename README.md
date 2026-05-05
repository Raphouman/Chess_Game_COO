## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
---
---


### Dans le projet
JFrame (ChessGameGUIProf)                    <-- container racine (fenêtre principale)
└── JLayeredPane (layeredPane)               <-- container à couches = layers (`setContentPane(layeredPane)`)
├── DEFAULT_LAYER:                           <-- par défaut pour les components normaux
│   └── JPanel (chessBoardGuiContainer)      <-- container plateau d'echec de 64 cases
│       └── GridLayout(8,8)
│           ├── JPanel square[0][0]          <-- component case = 1 case du damier (case de la pièce JLabel)
│           │   └── JLabel (pièce, optionnel)           <-- Image de la pièce sur la case (ex : TourBlancS.png) ou la Pièce en elle même (ex : TourBlanche)
│           └── ... 63 autres cases ...
└── DRAG_LAYER:                              <-- pour la pièce en cours de déplacement (JLabel de la pièce ajoutée tempo dans layeredPane en DRAG_LAYER pendant le drag)
└── JLabel pieceToMove (temporaire pendant drag)

### Lecture rapide (structure + comportement)

- **`JFrame`** : fenêtre principale (*container racine*).
- **`JLayeredPane`** : superpose les components (plateau + pièce déplacée au-dessus).
- **`JPanel chessBoardGuiContainer`** : contient les 64 cases du damier.
- **`JPanel square`** : représente une case (couleur + éventuel `JLabel`).
- **`JLabel`** : affiche l'image d'une pièce.
- **``DRAG_LAYER``** : couche temporaire pour la pièce en cours de déplacement. Permet de faire "flotter" la pièce au-dessus du plateau pendant le drag.

### Layouts

- **`GridLayout(8,8)`** sur `chessBoardGuiContainer` : organise les 64 cases en grille.
- **`BorderLayout`** sur chaque `JPanel square` : place proprement le `JLabel` (image) dans la case.

### Events / Listeners
- **`MouseEvent`** : généré lors du clic et du déplacement de la souris. ==> `getX()`, `getY()`, `getSource()`, etc. pour récupérer les coordonnées et la source de l'événement.
- **`MouseListener`**
    - `mousePressed` : sélectionne la pièce (`JLabel`) et la passe en `DRAG_LAYER`.
    - `mouseReleased` : calcule la case de départ/arrivée puis appelle `move(...)`.
- **`MouseMotionListener`**
    - `mouseDragged` : déplace visuellement le `JLabel` avec la souris (`MouseEvent`).
- **`Observer.update(...)`**
    - rafraîchit l'affichage après validation du déplacement par le modèle.

<u>A retenir :</u> la vue gère l'affichage et les événements, le modèle valide les règles, puis notifie la vue.


# A LIRE !!!
---

# @TODO
Ce qui est commencé mais incomplet (@TODO)

| Fonctionnalité | État |
|---|---|
| `pawnPromotion()` dans Jeu | retire le pion mais ne crée pas la nouvelle pièce |
| Roque (`setCastling()`) | squelette vide |
| `undoMove()` / `undoCapture()` | commentés, pas commencés |

---

# 2eme ITERATION : IHM en mode graphique — FAIT (2026-04-30)

## Ce qui a été implémenté

**ChessGameGUI (vue)**
- `update()` : vide les 64 cases et redessine depuis la liste `PieceIHM` envoyée par le modèle (patron Observer)
- `mousePressed()` : saisit la pièce sous la souris, la place sur la `DRAG_LAYER`
- `mouseDragged()` : déplace la pièce avec la souris
- `mouseReleased()` : vérifie le tour du joueur, appelle `chessGameControler.move()`, affiche le message console

**ChessGameControler**
- `isPlayerOK()` : compare la couleur de la pièce cliquée avec le joueur courant

**Echiquier / Jeu — corrections et ajouts**
- Suppression du double `switchJoueur()` (bug : le tour ne changeait jamais)
- `Echiquier.isMoveOk()` : 6 checks (pièce présente, destination différente, règle pièce, obstacles alliés+adverses sur le chemin, pas d'allié à destination, capture)
- `AbstractPiece.capture()` : met x=-1, y=-1 pour marquer une pièce comme capturée
- `Jeu.capture(x,y)` : délègue à `piece.capture()`
- `Jeu.move()` : déplace directement sans re-valider (Echiquier a déjà validé)
- `Pion.isAlgoMoveOk()` : avance de 2 cases autorisée depuis la ligne de départ
- `Pion.isCaptureMove()` : prise diagonale (x±1, y+direction)
- Bloc pion dans `Echiquier.isMoveOk()` : autorise la diagonale si ennemi présent, bloque la capture vers l'avant

## Gestion de l'échec, du mat et du pat — FAIT

### Nouvelles méthodes dans `Echiquier.java`

| Méthode | Rôle |
|---|---|
| `isPathBlocked(xSrc,ySrc,xDest,yDest)` | Vérifie si une pièce quelconque bloque le trajet entre deux cases (extrait de `isMoveOk` pour éviter la duplication) |
| `isSquareAttacked(x, y, attacker)` | Retourne `true` si une pièce de `attacker` peut atteindre la case (x,y) — gère le Pion (prise diagonale via `isCaptureMove`), le Cavalier (saut, pas de blocage), et toutes les autres pièces (mouvement + vérification du chemin) |
| `isInCheck(jeu, adversaire)` | Retourne `true` si le roi de `jeu` est actuellement en échec |
| `doesNotLeaveKingInCheck(xSrc,ySrc,xDest,yDest)` | Simule le coup (déplace la pièce, retire l'éventuelle pièce capturée), vérifie que le roi n'est pas en échec, puis **annule** la simulation — utilisé dans `isMoveOk` |
| `hasLegalMove()` | Parcourt toutes les pièces du joueur courant et toutes les cases : retourne `true` dès qu'un coup légal existe |
| `isEnd()` | Retourne `true` si le joueur courant n'a aucun coup légal — distingue **mat** (roi en échec → le joueur adverse gagne) et **pat** (roi non en échec → match nul) |

### Modification de `Echiquier.isMoveOk()`

Un **7ème check** a été ajouté à la fin : après toutes les validations existantes, le coup est refusé s'il laisserait le propre roi en échec (`doesNotLeaveKingInCheck`). Cela couvre notamment le cas où une pièce est clouée.

### Modification de `Echiquier.switchJoueur()`

Après le changement de tour, si le nouveau joueur courant est en échec, le message est mis à jour : `"ÉCHEC au roi <COULEUR> !"`.
Et si la partie est finie, le message est mis à jour : `"MAT ! Le joueur <ADVERSAIRE> gagne."` ou `"PAT ! Match nul."`

### Modification de `Jeu.java`

Ajout de `getPieces()` qui retourne une vue non modifiable de la liste des pièces — nécessaire pour que `Echiquier` puisse itérer dessus dans `isSquareAttacked` et `hasLegalMove`.

---
## PROMOTION (Quand un pion blanc atteint la rangée 0 (ou noir la rangée 7), la GUI affiche un dialog avec quatre choix : Dame, Tour, Fou, Cavalier. )
┌────────────┬────────────────────────────────────────────────────────────────┐                                                                                      
│          Fichier           │                           Changement                           │
├────────────────────────────┼────────────────────────────────────────────────────────────────┤                                                                      
│ BoardGames                      │ move(…, String promotionType)                                  │                                                               
├─────────────────────────────────┼────────────────────────────────────────────────────────────────┤                                                               
│ Echiquier                       │ isPawnPromotionMove() + move(…, String) utilise le type reçu   │                                                                 
├─────────────────────────────────┼────────────────────────────────────────────────────────────────┤                                                                 
│ ChessGame                       │ move(…, String) + isPawnPromotionMove() délégués à l'échiquier │                                                                 
├─────────────────────────────────┼────────────────────────────────────────────────────────────────┤                                                                 
│ AbstractChessGameControler      │ moveModel(…, String) + isPawnPromotionMove()                   │                                                               
├─────────────────────────────────┼────────────────────────────────────────────────────────────────┤                                                                 
│ ChessGameControlers (interface) │ move(…, String) + isPawnPromotionMove()                        │                                                               
├─────────────────────────────────┼────────────────────────────────────────────────────────────────┤                                                                 
│ ChessGameGUI                    │ dialog JOptionPane avant le move, passe le type choisi         │                                                               
├─────────────────────────────────┼────────────────────────────────────────────────────────────────┤                                                                 
│ ChessGameCmdLine                │ appels move(…, null) → promotion par défaut en Dame            │                                                               
└─────────────────────────────────┴────────────────────────────────────────────────────────────────┘     