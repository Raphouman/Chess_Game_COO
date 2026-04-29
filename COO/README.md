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

┌──────────────────────────────┬───────────────────────────────────────────────────┐
│        Fonctionnalité        │                       État                        │
├──────────────────────────────┼───────────────────────────────────────────────────┤
│ isEnd() dans Echiquier       │ retourne false en dur                             │
├──────────────────────────────┼───────────────────────────────────────────────────┤
│ pawnPromotion() dans Jeu     │ retire le pion mais ne crée pas la nouvelle pièce │
├──────────────────────────────┼───────────────────────────────────────────────────┤
│ capture() dans AbstractPiece │ retourne false en dur                             │
├──────────────────────────────┼───────────────────────────────────────────────────┤
│ isCaptureMove() dans Pion    │ retourne false en dur                             │
├──────────────────────────────┼───────────────────────────────────────────────────┤
│ Obstacles sur le chemin      │ pas encore implémenté                             │
├──────────────────────────────┼───────────────────────────────────────────────────┤                                                                                 
│ Roque (setCastling())        │ squelette vide                                    │
├──────────────────────────────┼───────────────────────────────────────────────────┤                                                                                 
│ undoMove() / undoCapture()   │ commentés, pas commencés                          │                                                                               
└──────────────────────────────┴───────────────────────────────────────────────────┘
                                                                                                                                                                     
---                                                                                                                                                                  
Dernières modifications (selon timestamps)

1. Jeu.java — isPawnPromotion() + squelette pawnPromotion() + setCastling()
2. Echiquier.java — dernière modifiée : logique de validation isMoveOk() avec messages d'erreur
3. Echiquier.java getPiecesIHM() — dernière modifiée : ajout de la logique pour retourner une liste des pièces des 2 jeux
  
---
---

# 2eme ITERATION : IHM en mode graphique
Inspirez-vous de l’exemple pour identifier vos premiers attributs et coder votre constructeur. Ce dernier
construit le plateau de l'échiquier sous forme de damier 8*8, et le rend écoutable par les évènements
MouseListener et MouseMotionListener.
 Créez dans un 1er temps un damier vide (sans les images des pièces) et testez.

@TODO :
 Ajoutez les pièces à leur position initiale en vous servant des méthodes de la classe
ChessImageProvider et testez. ==> FAIT

A FAIRE :
 Programmez les déplacements et testez :
Votre vue (classe ChessGameGUI) observe votre modèle (classe ChessGame) et doit être munie
d’une méthode update() qui a la responsabilité de rafraichir l’affichage après un déplacement,
une promotion du pion, etc. ...