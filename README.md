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


# @TODO
Ce qui est commencé mais incomplet (@TODO)

| Fonctionnalité | État |
|---|---|
| `isEnd()` dans Echiquier | retourne false en dur |
| `pawnPromotion()` dans Jeu | retire le pion mais ne crée pas la nouvelle pièce |
| `capture()` dans AbstractPiece | FAIT — met x=-1, y=-1 |
| `isCaptureMove()` dans Pion | FAIT — prise diagonale |
| Obstacles sur le chemin | FAIT — dans `Echiquier.isMoveOk()` check 4 |
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