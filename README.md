# Jeu_Pistolero
Jeu de démons et de pistolero réalisé en JavaFX (fichier 'Projet' à compiler avec Netbeans)


Le but du projet est de créer un jeu (genre shoot them up) où vous incarnez un pistolero chargée de touer les démons. 
Le jeu se joue sur une arène où évoluent les personnages. Vous dirigez le pistolero à l’aide du clavier. 
Prévoir les mouvements verticales et horizontales pour le pistolero et pour les démons, et peut-être, pourquoi pas, sur les diagonales, 
on peut imaginer que les 4 touches "ﬂlèches" sur le clavier provoquent les déplacements verticales et horizontales, mais modifiéees avec 
CTRL elle permettent les d´eplacements sur les deux diagonales. Les règles de jeux sont simples. Pistolero peut tirer une balle d’argent 
toujours dans la direction en face de lui. Si la balle touche un démon celui-ci est tué sur le champs. 
On pourra tirer en utilisant le clavier mais aussi avec un bouton de la souris. Le trajet de la balle doit être visible de telle sorte 
qu’on puisse voir le trajectoire, et le démon touché par la balle devait changer brievement forme avant de disparaître (il doit 
"saignée").

Le démon peut tuer (manger) le pistolero en le rattrapant (la collision avec le pistolero). Cela termine le jeu. 
Les démons changent la direction de mouvement s’il heurtent un obstacle (mettre des obstacles sur l’arène de jeu) ou spontanément 
(mais pas trop souvent et pas tous les démons au même moment). Utiliser un générateur de nombres aléatoires. Avec MVC 
(modèle vue contrôleur) bien structurée on peut essayer plusieurs règles de mouvement de démons, par exemple — les démons changent 
la direction de façon aléatoires, ils sont comme aveugles, — ou bien ils ont une tendance de suivre le pistolero et se diriger vers lui, 
ils ne sont pas aveugles mais veulent bien sa peau et veulent le rattraper. 
— on peut même penser de mettre les démons intelligents qui essaient d’éviter la ligne de tire. 

Pour changer un peu par rapport à d’autres jeux de ce type on demande implementer les démons de deux sexes, mâles et femelles. 
Si deux démons mâles entrent en collision un seul survivra l’autre sera mangé, par contre une rencontre d’un démon mâle et un 
démon femelle fait naître un nouveau démon (choisir les icônes diﬀérentes pour les mâles et les femelles) .
