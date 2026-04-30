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


# A LIRE !!!

## Gestion de l'échec, du mat et du pat — FAIT (2026-04-30)

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

### Modification de `Jeu.java`

Ajout de `getPieces()` qui retourne une vue non modifiable de la liste des pièces — nécessaire pour que `Echiquier` puisse itérer dessus dans `isSquareAttacked` et `hasLegalMove`.

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