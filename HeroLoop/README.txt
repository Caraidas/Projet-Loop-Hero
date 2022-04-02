- implémentations:

en se qui concerne la phase 1, tout ce qui est demandé est implémenté. 
Nous avons ajouter à la phase 1 le fait que plusieurs ennemies peuvent apparaitre sur une meme case.

- l'organisation du programme:

le programme utilise plusieurs packages :
	- Battle permettra de gérer les combats pour la phase 2 (pour l'instant il ne fais que perdre 6 HP au hero pour les combats)
	- collectable gère le loot des items
	- data contient les principales class permettant de faire fonctionner le jeu
	- entities permet de gérer toutes la construction des entités que ce soit ennemie ou le hero ainsi que les effets s'appliquant sur eux.
	- graphics gère l'affichage des monstres
	- inventory permet de gérer le deck et le future inventaire d'objet du hero
	- main permet de lancer le programme
	- map permet la construction du tableau en jeu
	- time gère le temps en jeu
Nous utilisons aussi un fichier contenant les sprites des objets ou entités.

choix techniques:

- Nous utilisons la bibliothèque graphique zen5
- La map est une matrice
- Le hero suit la route en parcourant la liste de coordonnées de la route
- Le Deck est une ArrayList de Card


Problèmes rencontrés:
- Quelques lags
