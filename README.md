# Cryptage RSA

![N|Solid](http://datao.sourceforge.net/java.png)

Projet de Master 2 consistant à réaliser des échanges de messages cryptés avec l'algorithme RSA.

  - Alice et Bob échangent leurs clés publiques
  - Alice crypte un message avec sa clé privée et l'envoie à Bob
  - Bob décrypte le message d'Alice à l'aide de la clé publique qu'elle lui a envoyé et il obtient le message initial

### Installation

Ce projet requiert Java 8 pour s'éxécuter.

Installer les dépendances

```sh
$ mvn clean install
$ mvn eclipse:eclipse
```

Lancer le serveur

```sh
$ ./runService
```

Lancer le client

```sh
$ ./runService
```

### Development

Développé par :
- Adrien CASELLES 
- Kenny GUIOUGOU
