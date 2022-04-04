[IDRISSI Nidal - LUCAS Paul]


1 - Ce qui à été implémenté :

	- Tous ce qui à été demandé dans la phase 1 à été implémenter à part le spawn des
	ratWolf à coté de la carte Grove.

	- En plus de tous cela, plusieurs monstres sont capables de spawn sur une même case
	et s'affichent proprement. Lors des combats, s'il y a plusieurs monstres,
	alors le joueur prendra 6 points de dégats multiplier par le nombre de monstres sur la case


2 - Organisation du programme :

	- Les classes sont distribuées danss différents packages en fonction de leur thème.
	par exemple le package map contient les classes Map, Cell et RoadCell.

	- Les classes GameData, TimeData, BattleData et View sont chargé de faire fonctionné le jeu
	GameData s'occupe des données du jeu.
	TimeData s'occupe du système de temps.
	BattleData s'occupe des combats.
	View s'occupe de l'affichage.

	- L'interface Entity est implémentée par la classe abstraite AbstractEntity,
	il dérive en dérive deux autres classes, Player et Monster qui ont des points communs mais 
	diffèrent sur certains points néanmoins comme le fait qu'un player a un inventaire.

	- La classe Card represente à elle seul toutes les cartes du jeu.

	- Le classe carte est représenter dans la classes map par une tableau à deux dimensions
	de Cell et une liste de coordonnées représentant les cases étant des case de la boucle


3 - Choix techniques :

	- Nous avons choisis, dans un soucis de logique, de commenter le code en anglais.

	- Pour générer la carte, la méthode s'opère en plusieurs temps. D'abord, le 
	remplissage du tableau avec des Cells vides, puis la création de la boucle.

	- Afin de tester le programme plus rapidement, nous avons décider de rajouter une vitesse x8
	qui permet de rapidement accéder aux situations que l'on souhaite tester.

4 - Problèmes rencontrés :

	- Nous avons rencontrés plusieurs problèmes de lag lors de l'affichage à l'écran du jeu
	cependant ces derniers ont été pour la majorité réglé.

	- Un autre problème et le fait que parfois, nous sommes confrontés à des choix
	d'implémentation mais nous ne savons pas lequel de ces choix utilise au mieux les 
	capacité du développement orienté objet ce qui nous rend moins confiant lorsque 
	l'on implémente une fonctionalité.

Merci d'avoir lu :)