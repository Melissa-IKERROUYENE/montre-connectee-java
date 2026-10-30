# Montre connectée personnalisable — Java Swing

Application Java permettant de simuler une montre connectée et de personnaliser son affichage.

Le projet repose sur une conception orientée objet et une interface graphique développée avec Java Swing. Il permet de gérer différents éléments visuels de la montre ainsi que plusieurs informations associées à son fonctionnement.

## Fonctionnalités

- Affichage analogique et numérique de l'heure
- Actualisation automatique de l'heure
- Affichage optionnel des secondes
- Affichage de la date
- Personnalisation du boîtier
- Personnalisation du fond et des aiguilles
- Personnalisation de la police d'affichage
- Gestion d'informations liées à la batterie
- Gestion du rythme cardiaque et de la température corporelle
- Sauvegarde et restauration de la configuration de la montre

## Technologies

- Java
- Swing
- AWT
- Sérialisation Java

## Compétences mobilisées

- Programmation orientée objet
- Organisation d'une application en plusieurs classes
- Conception d'une interface graphique interactive
- Gestion des événements utilisateur
- Affichage graphique dynamique
- Gestion et personnalisation de composants graphiques
- Actualisation temporelle de l'interface
- Sérialisation et désérialisation d'objets
- Sauvegarde et restauration d'une configuration

## Structure du projet

```text
Montre-connectee-personnalisable-Java/
├── montre/
│   ├── Batterie.java
│   ├── ElementOptionnel.java
│   ├── FenetrePrincipale.java
│   ├── Main.java
│   ├── Montre.java
│   ├── RythmeCardiaque.java
│   ├── TemperatureCorporelle.java
│   ├── TempsMontre.java
│   └── ZoneAffichage.java
├── Montre.jar
└── README.md
```

## Architecture

Le projet est organisé en plusieurs classes avec des responsabilités distinctes :

- `Main` contient le point d'entrée de l'application.
- `FenetrePrincipale` gère la fenêtre principale et les interactions avec l'utilisateur.
- `ZoneAffichage` assure le rendu graphique de la montre.
- `Montre` représente la montre et ses paramètres.
- `TempsMontre` gère les informations temporelles utilisées par la montre.
- `ElementOptionnel` représente les éléments d'affichage pouvant être activés ou désactivés.
- `Batterie`, `RythmeCardiaque` et `TemperatureCorporelle` représentent différentes informations associées à la montre connectée.

## Prérequis

Java 17 ou supérieur. Pour compiler, installer un JDK (et pas seulement un environnement Java). Une session graphique est nécessaire pour ouvrir la fenêtre. Aucune dépendance externe.

Les valeurs de batterie, rythme cardiaque et température sont simulées : cette application ne communique pas avec une montre physique.

## Compilation et exécution

Depuis le dossier racine du projet :

```bash
javac -encoding UTF-8 montre/*.java
java montre.Main
```

Le fichier `Montre.jar` a été recompilé à partir des sources présentes dans ce dossier pour Java 17. Pour lancer cette version :

```bash
java -jar Montre.jar
```

## Sauvegardes

Menu **Fichier → Sauvegarder / Ouvrir** : les paramètres visuels et les indicateurs sont enregistrés dans un fichier `.ser`. Le choix de l’heure simulée n’est pas enregistré : la gestion du temps reste indépendante de la configuration. Les images de fond sont référencées par leur chemin : si une image est déplacée ou si la configuration change d’ordinateur, il faut la sélectionner à nouveau. Charger uniquement des sauvegardes provenant de sources de confiance.

## Contexte

Projet universitaire réalisé dans le cadre de ma formation en informatique.

Ce projet m'a permis de mettre en pratique la programmation orientée objet à travers le développement d'une application Java complète, intégrant une interface graphique interactive, plusieurs possibilités de personnalisation et un mécanisme de sauvegarde de la configuration.
