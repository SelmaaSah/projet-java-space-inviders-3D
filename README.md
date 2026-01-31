- **Réalisatrice du projet** : Sahraoui Selma
- **Formation** : LP PROJET WEB
- **Date** : Janvier 2026


# 🎮 Space Invaders 3D (JOGL)

Un remakee du célèbre jeu d'arcade **Space Invaders**, développé en **Java** avec la bibliothèque graphique **OpenGL (JOGL)**. Ce projet utilise des primitives 3D (Cubes, Carrés) et un moteur de particules pour recréer une expérience arcade rétro-moderne.

## Fonctionnalités

* **Moteur 3D :** Utilisation de `GLJPanel` et `GL2` pour le rendu.
* **Système de Particules :**
* 🌌 **Champ d'étoiles** animé en arrière-plan (effet de vitesse).
* 💥 **Explosions** dynamiques lors de la destruction des ennemis (génération de particules colorées qui s'estompent).


* **Gameplay complet :**
* Déplacement du joueur et Tir.
* Système de **Score**.
* Conditions de **Victoire** (tous les aliens détruits) et de **Défaite** (invasion réussie).
* Fenêtre de fin de jeu (Pop-up).


## 🕹️ Contrôles

* ⬅️ **Flèche Gauche** : Déplacer le vaisseau à gauche.
* ➡️ **Flèche Droite** : Déplacer le vaisseau à droite.
* **Espace** : Tirer un missile.

## 📂 Structure du Code

Le projet est organisé comme ci-dessous :

* **`MainGL.java`** : Le cœur du jeu. Contient la boucle de rendu (`display`), la gestion des entrées clavier et la logique principale (collisions, score, gameloop).
* **`GraphicalObject.java`** : Classe abstraite mère définissant tout objet affichable (position x,y,z, rotation, couleur).
* **`Square.java` / `Cube.java**` : Formes géométriques de base héritant de `GraphicalObject`.
* **`Alien.java`** : Hérite de `Cube`. Représente un ennemi avec ses propriétés spécifiques.
* **`Projectile.java`** : Hérite de `Square`. Représente les missiles tirés par le joueur.
* **`ParticleSystem.java` & `Particle.java**` : Gère le champ d'étoiles en fond.
* **`Explosion.java` & `ParticleExplosion.java**` : Gère les effets pyrotechniques lors des impacts.


