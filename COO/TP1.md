On code sur l'IDE IntelliJ IDEA
# COO - 1er cours-TP
## PILIER
- **Polymorphisme** (=plusieurs formes) : Une même méthode peut se comporter différemment selon l’objet (ex : ecrire() n’est pas pareil pour un stylo et un clavier).
- **Héritage** (Une classe peut hériter d’une autre (ex : Bic hérite de Stylo).) On doit pouvoir dire « L’héritier est un hériteur ». Par ex : « Le canard est un animal »
- **Abstraction**= INTERFACE (du + au – abstrait : interface classe abstraite  classe)
- **Encapsulation** = (On ne montre que l’essentiel (ex : tu appuies sur un bouton sans savoir ce qu’il fait à l’intérieur).)  SERVICE + METHODE (comportements = fonctions)

## SOLID (= PRINCIPES)
**Les principes sont des applications des pilliers**

- **Single Responsability Principle** (utiliser la Delegation) chacun ses missions, ne doit avoir qu'une seule raison de changer. Une classe est responsable de ses attributs ! ==> Pas de classe DIEU.
- **Open/Closed Principle** (ouvert à l’extension et fermé à la modif ==>  (interface) 
- **Liskov Subsitution Principle** (2 types différents) 
`Animal a = new Chat()`
une méthode utilisant une référence vers une classe de base doit pouvoir référencer des objets de ses classes dérivées sans les connaitre (polymorphisme).
- **Interface Segregation Principle** (lié à SRP, méthode1 dans une interfaceA ssi toutes les classes implémentant cette interfaceA ont besoin de cette méthode 1 = aucun client ne devrait dépendre de méthodes qu'il n'utilise pas)
- **Dependency Inversion Principle** (O/C + Liskov = Les modules de haut niveau ne doivent pas dépendre des modules de bas niveau. Les deux doivent dépendre des abstractions. 2) Les abstractions ne doivent pas dépendre des détails. Les détails doivent dépendre des abstractions)
    - Ex : Classe animal ne doit pas implémenter des méthodes spécifiques à une abeille
    - Ex : Si la classe monde dépend de 3000 "Animal" ==> on va devoir faire 3000 tests de type (if instance of...)


---
---

# COO - 2ème cours-TP
## SOLID (détails additionnels)
- **SRP** : Role d'une classe : constructeur qui donne des objets opérationnels (init des attributs...) + GERER l'encapsulation de ses attributs  
- **O/C** :
    - **Ouvert à l'extension** : Ajouter de nouvelles fonctionnalités FACILEMENT = sans modifier le code existant (ex : ajouter une nouvelle classe qui implémente une interface déjà utilisée)
    - **Fermé à la modification** : Ne pas modifier le code existant (ex : ne pas ajouter de nouveaux if dans une classe déjà utilisée)
- **Liskov** : Je dois pouvoir substituer n'importe quel sous type, d'un surtype. Polymorphisme (ecire()) et Héritage (Bic hérite de Stylo) sont les moyens d'implémenter le Liskov Substitution Principle.
- **Interface Segregation** : On doit éviter les interfaces "Dieu" (ex : interface "Animal" avec 100 méthodes, alors que la classe "Chat" n'en utilise que 10).
- **Dependency Inversion** : Si on raisonne à haut niveau d'abstraction, on ne se soucie pas des détails concrets d'implémentation. 
Ex : Template Method (ex : classe "Monde" qui utilise une interface "Animal", sans se soucier de la classe concrète "Chat" ou "Abeille" qui implémente cette interface).



- **LoD** : Principe de la Moindre Connaissance (ou loi de Demeter) 
    Only speak to your closest friends (Echéquier parle au Jeu qui parle au Pièces)

---
---

# COO - 4ème cours-TP
## DESIGN PATTERNS (= Mise en oeuvre des principes SOLID)
- **Design Pattern** : Solution réutilisable à un problème de conception récurrent (ex : Template Method, Strategy, Observer, etc.)


### TEMPLATE METHOD (un design patterns) ==> SOLID
- **Template Method** : Permet de factoriser du code commun dans une méthode (finale) et de déléguer les parties spécifiques à des méthodes abstraites qui seront implémentées dans les sous classes.

    - Dependency Inversion Principle : La méthode isMoveOk() dépend d'une abstraction (isAlgoMoveOk()) et pas d'une implémentation concrète (ex : isAlgoMoveOk() est implémenter dans les sous classes).
      ==> A haut niveau d'abstraction, on ne se soucie pas des détails concrets d'implémentation.
  
    Sinon, on respecte aussi le SRP (chacun sa mission) et le Open/Closed Principle (on peut ajouter de nouvelles pièces sans modifier le code existant).

### TEMPLATE METHOD, exemple dans le projet

public isMoveOk() ==> une partie COMMUNE + une partie SPECIFIQUE
- On code la partie COMMUNE (verif taille echéquier) dans isMoveOk()
- Puis on on définit une méthode **abstraite protected** isAlgoMoveOk() 
qui sera implémenter de manière **spécifique** dans chaque sous classe

Et donc isMoveOk : CODE COMMUN + isAlgoMoveOk()

ATTENTION : On met **FINAL** la méthode isMoveOk() pour éviter que les sous classes puissent la modifier (et ainsi respecter le O/C).

---

## Comment les principes SOLID sont mis en oeuvre dans le projet :
### SRP
- Echiquier ==> Jeu ==> Pièce
- Chaque pièce est responsable de son mode de déplacement spécifique (et ne connait pas l'échiquier)

### O/C
- Pas respecté : Enum ChessPiecePos et taille plateau dans Coord.java (coordonnees_valides()) ==> mettre des constantes dans un fichier config.java...
  Respecté : 
- On peut ajouter de nouvelles pièces (ex : Cavalier) sans modifier le code existant grâce à l'**INTROSPECTION**

### Liskov
- Chaque pièce (ex : Roi, Reine, etc.) est une sous classe de la classe abstraite Piece, et peut être substituée à une référence de type Piece sans problème (ex : Piece p = new Roi();)
    ==>Chaque pièce implémente la méthode isAlgoMoveOk() de manière spécifique, mais on peut toujours appeler isMoveOk() sur une référence de type Piece sans se soucier de la classe concrète (ex : Piece p = new Roi(); p.isMoveOk(...);)
- Liste de pièces : List<Piece> pieces = new ArrayList<>() ou LinkedList<>() 

### Interface Segregation
- Pas d'interface "Dieu" avec 100 méthodes, chaque classe implémente uniquement les méthodes dont elle a besoin

### Dependency Inversion
- La classe monde, l'arbitre (Echiquier) dépend d'une interface (BoardGame) et pas d'implémentation concrète (Pièce...)

---

---

# COO - 5ème cours-TP

## IHM (Interface Homme-Machine) = GRAPHIQUE !!!
- **IHM** : Interface qui permet à l'utilisateur d'interagir avec le programme (ex : interface graphique avec des boutons, des cases à cocher, etc.)
- C'est un **container** (cf pattern Composite vu en 5A) ==> JComponent (JPanel = tableau ?), JContainer, JFrame, etc.
- **Layout**: Utilitaire d'un **container** auquel on va déléguer la gestion de l'affichage des **components** (ex : BorderLayout, GridLayout, etc.)

## Événementiel
- **Événementiel** : Paradigme de programmation où le flux d'exécution est déterminé par des **événements** (ex : clic de souris, appui sur une touche, etc.)
- 
==> Le **component** (ex : JButton) génère un **événement (classe concrète)** (ex : ActionEvent, MouseEvent) lorsqu'une action se produit sur lui, et un **listener** (peut être lui même) attend et réagit à cet événement. 
getSource() : permet de récupérer la source de l'événement (ex : le bouton qui a été cliqué) pour pouvoir réagir en conséquence.
- **Listener** : Une **Interface** qui attend et reçoit et réagit à un événement (ex : ActionListener pour les boutons ou MouseListener pour les clics de souris)

- **Observer** : Un **design pattern** qui permet à un **objet (le listener)** de s'abonner à un autre objet/sujet (le **component**) pour recevoir des notifications lorsque le **component** change d'état (ex : Observer pour les changements de position des pièces sur l'échiquier)
Ex de méthode pour **observer** : `update(Params)` qui est appelée par le sujet pour notifier les observers des changements d'état.

### Dans le projet
JFrame (ChessGameGUIProf)                    <-- container racine (fenêtre principale)
└── JLayeredPane (layeredPane)               <-- container à couches = layers (`setContentPane(layeredPane)`)
├── DEFAULT_LAYER:                           <-- par défaut pour les components normaux
│   └── JPanel (chessBoardGuiContainer)      <-- container plateau d'echec de 64 cases
│       └── GridLayout(8,8)
│           ├── JPanel square[0][0]          <-- component case = 1 case du damier (case de la pièce JLabel)
│           │   └── JLabel (pièce, optionnel)           <-- Image de la pièce sur la case (ex : TourBlancS.png) ou la Pièce en elle même (ex : TourBlanche)
│           └── ... 64 cases ...
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

---

---

## MVC (Model-View-Controller)
### CREATION ==> Probleme de COUPLAGE !!!
- On veut un **couplage faible** entre les composants (ex : la Vue ne doit pas connaitre les details du Model, et vice versa) pour faciliter la maintenance et l'évolution du code.
- On utilise le **controler** pour faire le lien entre la Vue et le Model, et ainsi éviter que la Vue ne depende directement du Model (et vice versa).
  - On **privilégie 2 sens de communication** :
    -   **sens de communication PAR REFERENCE** : la Vue connait le Controler qui connait le Model, et le Model ne connait rien.
    -   **sens de communication PAR OBSERVATION** : la Vue observe le Model pour se faire notifier des changements d'état, et la vue ne connait pas le Model : c'est le **Model qui notifie la Vue**

- Or ici, on aura besoin de l'un pour créer l'autre et vice versa donc on va faire en sorte que la vue est un comportement d'**Observer** pour se faire notifier des changements d'état du **modèle** et rafraichir l'affichage en conséquence.
Mais elle n'a pas besoin de savoir quel **observé** elle observe.
- De même, le modèle a un comportement de **Subject / Observé** pour notifier les changements d'état aux **observers** (la vue), mais il n'a pas besoin de savoir qui sont les **observers**.

### Roles
- **Model (M) ==> ChessGame.java** : classes metier dans `model/` (`Echiquier`, `Pieces`, `Coord`, etc.). 
Va implémenter un comportement de **Subject / Observé** pour notifier les changements d'état aux **observers** (la vue).
==> `Col <observer>;` et `addObserver(observer);` et `notifyObservers();`

- **View (V) ==> ChessGameGUI.java** : classes de `vue/` (`ChessGameGUI`) qui affichent le plateau et captent les evenements utilisateur.
Va implémenter un comportement d'**Observer** pour se faire notifier des changements d'état du **modèle** et rafraichir l'affichage en conséquence.
==> `implements Observer` et `update(Params)` pour rafraichir l'affichage.

- **Controller (C) ==> ChessGameControler** : classes de `controler/` (`ChessGameControlers`) qui recoivent les actions de la vue et pilotent le modele.

### Flux (dans le projet)

```text
Utilisateur (drag/drop souris)
   -> View (`ChessGameGUI`) capte `MouseEvent`
   -> Controller (`move(initCoord, finalCoord)`)
   -> Model (`Echiquier`) valide + met a jour l'etat
   -> Notification (`Observer.update(...)`)
   -> View rafraichit les components (`JPanel`, `JLabel` ...)
```

### Regle a retenir
- La **View** n'implemente pas les regles d'echecs.
- Le **Model** ne depend pas des details graphiques Swing.
- Le **Controler** fait le lien entre les deux.

---

---

## En quoi le MVC (+ Observer/Subject) respecte les principes SOLID ?
- **SRP** : Chaque composant a une responsabilité unique (Model = logique métier, View = affichage, Controller = liaison entre les deux).

- **O/C** : On peut ajouter de nouvelles vues ou de nouveaux modèles sans modifier les autres composants (ex : ajouter une vue 3D sans toucher au modèle).
Ouvert à l'extension : on peut ajouter de nouvelles fonctionnalités (ex : nouvelle pièce, nouvelle règle) en créant de nouvelles classes qui implémentent les interfaces existantes.
Fermé au modification : on ne peut pas changer d'architecture (Observable <-> Observé), d'organisation, de constructeur ...

- **Liskov** : On peut substituer n'importe quelle implémentation de la vue ou du modèle sans affecter les autres composants (ex : remplacer `ChessGameGUI` par une autre classe qui implémente `Observer`).

- **Interface Segregation** : Les interfaces sont spécifiques à chaque composant (ex : `Observer` pour la vue, `Subject` pour le modèle) et ne contiennent que les méthodes nécessaires.
DownCast dans LauncherGUI : `chessGame.addObserver((Observer) frame);` ==> on cast la vue en Observer pour l'ajouter comme observer du modèle, mais la vue n'a pas besoin de connaitre les details du modèle pour implémenter l'interface Observer.

- **Dependency Inversion** : La vue et le modèle dépendent d'abstractions (interfaces `Observer` et `Subject`) plutôt que de classes concrètes (de bas niveau), ce qui permet une flexibilité maximale dans les implémentations.
Update(Params = ListPiece**IHM**) : la vue reçoit une liste de pièces **à afficher**, sans se soucier de la logique métier ou de la structure interne du modèle. Attention à ne pas lui passer en params une **Liste de pièce** !!!
De même, le modèle peut notifier les changements d'état sans se soucier de qui sont les observers ou comment ils vont réagir.

Donc on en retient 2 pour le **découplage** : SRP (chacun sa mission) et O/C (ajouter de nouvelles fonctionnalités sans modifier le code existant).