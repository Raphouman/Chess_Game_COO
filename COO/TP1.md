On code sur l'IDE IntelliJ IDEA
# COO - 1er cours-TP
Un objet a des **attributs** (caractéristiques) et des **comportements** (fonctions/méthodes/services).
## PILIER
- **Polymorphisme** (=plusieurs formes) : Une même méthode peut se comporter différemment selon l’objet (ex : ecrire() n’est pas pareil pour un stylo et un clavier).
    Polymorphisme d'**héritage** = **@Override** (classe Object ou Interface) + **surcharge d'argument** (constructeur) <==> **introspection** (ex : List<Piece> pieces = new ArrayList<>(); ou LinkedList<>();) 
  <==> **List<T>** = polymorphisme **paramétrique** → on ne se soucie pas du type concret au moment de coder la liste + **ad-hoc** (ex : ecrire() dans Stylo et ecrire() dans Clavier, qui n'ont rien à voir)
  ### Polymorphisme paramétrique
     - **Classe paramétrée** : classe qui peut être utilisée avec différents types de données (ex : List<T> en Java).
     - **Généricité** : capacité d'une classe ou d'une méthode à fonctionner avec différents types de données sans modification du code (ex : List<T> peut être utilisée pour List<String>, List<Integer>, etc.).


- **Héritage** (Une classe peut hériter d’une autre (ex : Bic hérite de Stylo).) On doit pouvoir dire « L’héritier est un hériteur ». Par ex : « Le canard est un animal »
Attention : On évite de faire TROP d'héritage, on peut passer par un médiateur (ex : interface) pour faire le lien entre les classes.
==> cf **Design Patterns** : Mediator, Template Method ...

- **Abstraction**= INTERFACE (du + au – abstrait : interface ==> classe abstraite ==> classe concrète). Pas de détail d'implémentation, pas de langage de programmation spécifique.

- **Encapsulation** = (On ne montre que l’essentiel (ex : tu appuies sur un bouton sans savoir ce qu’il fait à l’intérieur).) ==> SERVICE + METHODE (comportements = fonctions). 
On cache l’intérieur (les données/ **attributs**) et on passe par des “portes” (les méthodes). Il y a aussi un aspect de protection (ex : les attributs sont **privés** et on utilise des getters/setters pour y accéder) pour éviter que les données soient modifiées de manière non contrôlée.

!!! **délégation = SRP** = On utilise les méthodes d'un de ses attributs et rien d'autre pour implémenter un de ses comportements (ex : la classe Echiquier utilise les méthodes de la classe Piece pour implémenter le comportement de déplacement des pièces, sans connaitre les détails de chaque pièce).


## SOLID (= PRINCIPES)
**Les principes sont des applications des pilliers**

- **Single Responsability Principle** (utiliser la Delegation) chacun ses missions, ne doit avoir qu'une seule raison de changer. Une classe est responsable de ses attributs (leur **ENCAPSULATION**) ! ==> Pas de classe DIEU.

- **Open/Closed Principle** ==> (interface Observer/Observable) : `((Observable) chessGame).addObserver((Observer) frame)`
    - **Ouvert à l'extension** : Ajouter de nouvelles fonctionnalités FACILEMENT = sans modifier le code existant (ex : ajouter une nouvelle classe qui implémente une interface déjà utilisée)
    - **Fermé à la modification** : Ne pas modifier le code existant (ex : ne pas ajouter de nouveaux if dans une classe déjà utilisée)
  
- **Liskov Subsitution Principle** : Un type/ Une abstraction A doit pouvoir être substituée par n'importe laquelle de ses sous-type /sous-abstractions sans que cela n'affecte le programme 
`Animal a = new Chat()` : type **Abstrait / Déclaré** (Animal) + type **concret / instancié** (Chat)
une méthode utilisant une référence vers une classe de base doit pouvoir référencer des objets de ses classes dérivées sans les connaitre (polymorphisme).

- **Interface Segregation Principle** : "Forte cohésion, **faible couplage**"
(lié à SRP, méthode1 dans une interfaceA ssi toutes les classes implémentant cette interfaceA ont besoin de cette méthode 1 = **aucun client ne devrait dépendre de méthodes qu'il n'utilise pas**)
  On doit éviter les interfaces "Dieu" (ex : interface "Piece" avec 100 méthodes, alors que la classe "Fou" n'en utilise que 10).

- **Dependency Inversion Principle** (O/C + Liskov = Les modules de haut niveau ne doivent pas dépendre des modules de bas niveau. Les deux doivent dépendre des abstractions. 2) Les abstractions ne doivent pas dépendre des détails. (Les détails doivent dépendre des abstractions))
  **Ne nous appelez pas, c'est nous qui vous rappellerons** : (Le modèle [haut niveau d'abstraction] ne doit pas dépendre de la vue [bas niveau d'implémentation], il la notifie via l'interface **Observer**. 
    - Ex : La classe monde, l'arbitre (Echiquier) dépend d'une interface (BoardGame) et pas d'implémentation concrète (Pièce...)
    - Ex : Si la classe Echiquier dépend de 3000 "Pièces" différentes ==> on va devoir faire 3000 tests de type (if instance of...)

(BONUS)
- **LoD** : Principe de la Moindre Connaissance (ou loi de Demeter)
  Only speak to your closest friends (Echéquier parle au Jeu qui parle au Pièces)
Ex :  `a.getB().getC().doSomething()` (connaissance excessive).

---
---
# COO - 3ème cours-TP (rappel 4A POO)
## RAPPEL COLLECTION
- **Collection** : structure de données qui permet de stocker et de manipuler des groupes d'objets (ex : List, Set, Map, etc.)
- **List** : collection **ordonnée** qui peut contenir des éléments en double (ex :ArrayList, LinkedList, etc.)
- **Set** : collection qui ne peut pas contenir d'éléments en double (ex : HashSet, TreeSet, etc.), **non ordonnée**
- **Map** : collection qui associe des **clés à des valeurs** (ex : HashMap, TreeMap, etc.)
## Comparateur vs Comparable
- **Comparable** : interface qui permet de définir un ordre naturel pour les objets d'une classe (ex : `compareTo()` pour trier une liste d'objets de cette classe).
- **Comparator** : interface qui permet de définir un ordre personnalisé pour les objets d'une classe (ex : `compare()` pour trier une liste d'objets de cette classe selon un critère spécifique).
## EQUALS vs HASHCODE (equals ==> même hashcode, hashcode ==> pas forcément même equals)
- **equals()** : méthode qui permet de comparer deux objets pour vérifier s'ils sont égaux (ex : `equals()` pour comparer le contenu de deux objets).
- **hashCode()** : méthode qui permet de générer un code de hachage pour un objet (ex : `hashCode()` pour stocker des objets dans une collection de type HashSet ou HashMap).

---
---
# COO - 4ème cours-TP
## DESIGN PATTERNS (= Mise en oeuvre des principes SOLID) !!!
- **Design Pattern** : Solution réutilisable à un problème de conception récurrent (ex : Template Method, médiateur, Fabrique, Strategy, Observer, etc.)


### TEMPLATE METHOD (un design patterns) ==> SOLID
- **Template Method** : Permet de factoriser du code commun dans une méthode (finale) et de déléguer les parties spécifiques à des méthodes abstraites qui seront implémentées dans les sous classes.
    
    - **Dependency Inversion Principle** : La méthode isMoveOk() (AbstractPiece.java) dépend d'une abstraction (isAlgoMoveOk()) et pas d'une implémentation concrète (ex : isAlgoMoveOk() est implémenter dans les sous classes).
      ==> A haut niveau d'abstraction, on ne se soucie pas des détails concrets d'implémentation.
    - Sinon, on respecte aussi le **SRP** (chacun sa mission) et le **Open/Closed** Principle (on peut ajouter de nouvelles pièces sans modifier le code existant).

### TEMPLATE METHOD, exemple dans le projet

`public isMoveOk()` (AbstractPiece.java) ==> une partie COMMUNE + une partie SPECIFIQUE
- On code la partie COMMUNE (verif taille echéquier) dans `isMoveOk()`
- Puis on on définit une méthode **abstraite protected** `isAlgoMoveOk() ` qui sera implémenter de manière **spécifique** dans chaque sous classe

Et donc **isMoveOk : CODE COMMUN + isAlgoMoveOk()**
ATTENTION : On met **FINAL** la méthode isMoveOk() pour éviter que les sous classes puissent la modifier (et ainsi respecter le O/C).

#### Inconvénient LSP du Template Method
Si une sous-classe override un step avec un corps vide (**Hooks** : méthode avec corps vide) pour "désactiver" un comportement par défaut, elle viole LSP. 
Ton prof cite exactement ce problème dans le DS 2022 avec deleteObservers() vide.

#### Factory Method
Factory Method est une spécialisation de Template Method. Une Factory Method peut aussi servir de step dans un grand Template Method

---

## Comment les principes SOLID sont mis en oeuvre dans le projet :
### SRP
- **Respecté** : Echiquier ==> Jeu ==> Pièce
Chaque pièce est responsable de son mode de déplacement spécifique (que l'échéquier ne connait pas, et les pièces ne connaissent pas l'échéquier) 
- **Mauvais exemple** (spéculation) : la classe Echiquier qui gère à la fois l'UI, la logique de déplacement des pièces et la logique de vérification des règles du jeu (ex : échec, mat, etc.) ==> on peut déléguer la logique de déplacement à Jeu, l'UI à la Vue...

### O/C
- **Mauvais exemple** : Enum ChessPiecePos et taille plateau dans Coord.java (coordonnees_valides()) ==> mettre des constantes dans un fichier config.java... + if (p instance of Cavalier) dans Echiquier.isSquareAttacked() ==> on peut déléguer la responsabilité de vérifier si une pièce attaque une case à la pièce elle même (ex : p.isAttackingSquare(...)) pour éviter de devoir faire des if pour chaque type de pièce.
- **Respecté** : On peut ajouter de nouvelles pièces (ex : Cavalier) sans modifier le code existant grâce à l'**INTROSPECTION** (cf polymorphisme d'**introspection** et **paramétrique**) : on peut faire `List<Piece> pieces = new ArrayList<>()` et ajouter n'importe quelle pièce qui implémente l'interface Piece, sans se soucier de la classe concrète (ex : `pieces.add(new Cavalier())`).

### Liskov
- Chaque pièce (ex : Roi, Reine, etc.) est une sous classe de la classe abstraite Piece, et peut être substituée à une référence de type Piece sans problème 
**Respecté** : (ex : `Piece p = new Roi();`)
    ==>Chaque pièce implémente la méthode isAlgoMoveOk() de manière spécifique, mais on peut toujours appeler isMoveOk() sur une référence de type Piece sans se soucier de la classe concrète (ex : Piece p = new Roi(); p.isMoveOk(...);)
- Liste de pièces : List<Piece> pieces = new ArrayList<>() ou LinkedList<>()

- **Mauvais exemple** (spéculation) : toString() qui retourne "ok" au lieu d'une description de la pièce, ou qui retourne une description différente selon la classe (ex : "Roi" pour la classe Roi, "Reine" pour la classe Reine, etc.) ==> on peut faire mieux en utilisant une méthode getDescription() dans la classe Piece qui sera implémentée de manière spécifique dans chaque sous classe.
                                    Ou sous classe qui ovveride et modifie le contract, retourne des valeurs illogiques ou lève des exeptions non prévues.

### Interface Segregation
- Pas d'interface "Dieu" avec 100 méthodes, chaque classe implémente uniquement les méthodes dont elle a besoin.
- **Respecté** : Echiquier implémente une interface BoardGame avec des méthodes spécifiques au jeu d'échecs (ex : movePiece(), isCheck(), etc.) et les pièces implémentent une interface Piece avec des méthodes spécifiques à leur comportement (ex : isAlgoMoveOk(), getDescription(), etc.)
- **Respecté** : Tour ne dépend pas de la méthode d'un Pion : isCaptureMove().
- **Mauvais exemple** (spéculation) : une interface "Piece" avec 100 méthodes, alors que la classe "Fou" n'en utilise que 10.

### Dependency Inversion
-**Respecté** : La classe monde, l'arbitre (Echiquier) dépend d'une interface (BoardGame) et pas d'implémentation concrète (Pièce...)
- **Mauvais exemple** : La classe Echiquier, dans isSquareAttacked() doit chek les instances (cavalier, pion, etc.) pour vérifier si une pièce attaque une case, alors qu'elle pourrait déléguer cette responsabilité à la pièce elle-même (ex : p.isAttackingSquare(...)).










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

---

---

## MVC (Model-View-Controller)
### CREATION ==> Probleme de COUPLAGE !!!
- On veut un **couplage faible** entre les composants (ex : la Vue ne doit pas connaitre les details du Model, et vice versa) pour faciliter la maintenance et l'évolution du code.
- On utilise le **controler** pour faire le lien entre la Vue et le Model, et ainsi éviter que la Vue ne depende directement du Model (et vice versa).
  - On **privilégie 2 sens de communication** :
    -   **sens de communication PAR REFERENCE** : la Vue connait le Controler qui connait le Model, et le Model ne connait rien.
    -   **sens de communication PAR OBSERVATION** : la Vue observe le Model pour se faire notifier des changements d'état, et la vue ne connait pas le Model : c'est le **Model qui notifie la Vue**

![img.png](Observable_Observer.png)

- Or ici, on aura besoin de l'un pour créer l'autre et vice versa donc on va faire en sorte que la vue est un comportement d'**Observer** pour se faire notifier des changements d'état du **modèle** et rafraichir l'affichage en conséquence.
Mais elle n'a pas besoin de savoir quel **observé** elle observe.
- De même, le modèle a un comportement de **Subject / Observé** pour notifier les changements d'état aux **observers** (la vue), mais il n'a pas besoin de savoir qui sont les **observers**.

### Roles
- **Model (M) ==> ChessGame.java** : classes metier dans `model/` (`Echiquier`, `Pieces`, `Coord`, etc.). 
Va implémenter un comportement de **Subject / Observé** pour notifier les changements d'état aux **observers** (la vue).
==> `Col <observer>;` et `addObserver(observer);` et `notifyObservers()`; et `setChanged()` pour **notifier** les observers des changements d'état.

- **View (V) ==> ChessGameGUI.java** : classes de `vue/` (`ChessGameGUI`) qui affichent le plateau et captent les evenements utilisateur.
Va implémenter un comportement d'**Observer** pour se faire notifier des changements d'état du **modèle** et rafraichir l'affichage en conséquence.
==> `implements Observer` et `update(Params)` pour rafraichir l'affichage.

- **Controller (C) ==> ChessGameControler** : classes de `controler/controler.local/` (`ChessGameControlers`) qui recoivent les actions de la vue et pilotent le modele.

### Flux (dans le projet)

```text
Utilisateur (drag/drop souris)
   -> View (`ChessGameGUI`) capte `MouseEvent`
   -> Controller (`move(initCoord, finalCoord)`)
   -> Model (`Echiquier`) valide + met a jour l'etat
   -> Notification (`Observer.update(...)`) ==> UPDATE Chez la VUE
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

- **O/C** : Grace aux interfaces `Observer` et `Observable` on peut ajouter de nouvelles vues ou de nouveaux modèles sans modifier les autres composants (ex : ajouter une `vue` 3D sans toucher au modèle `ChessGame`).
Ouvert à l'extension : on peut ajouter de nouvelles fonctionnalités (ex : nouvelle pièce, nouvelle règle) en créant de nouvelles classes qui implémentent les interfaces existantes.
Fermé au modification : on ne peut pas changer d'architecture (Observable <-> Observé), d'organisation, de constructeur ...

- **Liskov** : On peut substituer n'importe quelle implémentation de la vue ou du modèle sans affecter les autres composants (ex : remplacer `ChessGameGUI` par une autre classe qui implémente `Observer`).

- **Interface Segregation** : Les interfaces sont spécifiques à chaque composant (ex : `Observer` pour la vue, `Subject` pour le modèle) et ne contiennent que les méthodes nécessaires.
DownCast dans LauncherGUI : `chessGame.addObserver((Observer) frame);` ==> on cast la vue en Observer pour l'ajouter comme observer du modèle, mais la vue n'a pas besoin de connaitre les details du modèle pour implémenter l'interface Observer.

- **Dependency Inversion** : La vue et le modèle dépendent d'abstractions (interfaces `Observer` et `Subject`) plutôt que de classes concrètes (de bas niveau), ce qui permet une flexibilité maximale dans les implémentations.
Update(Params = ListPiece**IHM**) : la vue reçoit une liste de pièces **à afficher**, sans se soucier de la logique métier ou de la structure interne du modèle. Attention à ne pas lui passer en params une **Liste de pièce** !!!
De même, le modèle peut notifier les changements d'état sans se soucier de qui sont les observers ou comment ils vont réagir.

Donc on en retient 2 pour le **découplage** : SRP (chacun sa mission) et O/C (ajouter de nouvelles fonctionnalités sans modifier le code existant).


---
---

# PARTIEL 2024/2025 - 1S
## QUESTIONS DE COURS (sur le projet)
- Expliquer le rôle de chaque composant dans l'architecture MVC du projet (Model, View, Controller) et comment ils interagissent entre eux.
- Comment le design pattern Observer est utilisé dans le projet pour permettre la communication entre le modèle et la vue ? Donner un exemple concret de notification d'un changement d'état du modèle à la vue.
- Comment les principes SOLID sont respectés dans l'architecture du projet ? Donner des exemples spécifiques pour chaque principe (SRP, O/C, Liskov, Interface Segregation, Dependency Inversion).
## EXERCICES
- ...

## CONCEPTION
- Architceture MVC + Observer/Observable. (Revoir le sens des fleches, pleines [héritage] vs pointillées [dépendance], et les rôles de chaque composant)
- 3 Vues donc une Interface Vue et des AbstractVue...
- Plusieurs modèles donc IModel et des AbstractModel...

- Est ce une bonne idée de faire hériter la classe IModel de IObservable (`notify()`, `addobserver()`, `removeobserver()`) et la classe IView de IObserver ? 
    - Pas tant car ca voudrait dire que chaque model est observable et que chaque view est un observer, ce qui n'est pas forcément le cas, mais ca peut l'être.
      - Ex dans le projet : L'interface IModel n'implémente pas IObservable directement, mais le modèle **ChessGame** implémente IObservable, et la vue ChessGameGUI implémente IObserver, mais on pourrait imaginer d'autres modèles ou vues qui n'ont pas besoin de ces comportements.
==> Attention à ce que ça respecte la **Interface Segregation Principle** : on ne veut pas forcer toutes les classes à implémenter des méthodes dont elles n'ont pas besoin.
    - Possibilité : Pas d'héritage entre IModel et IObservable, mais plutôt une composition : la classe ChessGame implémente IModel et contient une instance de IObservable pour gérer les observers, et la classe ChessGameGUI implémente IObserver et s'abonne à l'instance de IObservable du modèle.

### NOTIFICATIONS (template méthode)
- IJoueur (dans Model) a une méthode `prévenir()` et `notifyObservers()` pour notifier les vues des changements d'état du modèle (ex : changement de tour, échec, mat, etc.)
- INotifJoeur à une méthode `prévenir()` pour envoyer des notifications spécifiques aux joueurs (ex : "C'est au tour du joueur X", "Échec au roi Y", etc.)
    - AbstractNotif implémente INotifJoueur et contient une référence à IModel pour pouvoir accéder aux informations du modèle et envoyer des notifications pertinentes aux joueurs. A une méthode finale `prévenir()` qui appelle une méthode abstraite `prévenirSpecifique()` que les sous classes doivent implémenter pour envoyer des notifications spécifiques (ex : par SMS, par email, etc.)
    - SMSNotif et EmailNotif sont des classes concrètes qui extends AbstractNotif et implémentent la méthode `prévenirSpecifique()` pour envoyer des notifications par SMS ou par email, en utilisant les informations du modèle (ex : nom du joueur, état de la partie, etc.) pour personnaliser les messages.