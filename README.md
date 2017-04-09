# Teaching-HEIGVD-RES-2017-Labo-SMTP

## Cadre

Ce laboratoire a été réalisé dans le but de comprendre le fonctionnement du protocole SMTP. Le but du laboratoire est de réaliser une application permettant de générer des messages électroniques forgés (l'identité de l'expéditeur, apparaissant chez les destinataies, peut être faussée). Cette réalisation a pour but de maîtriser le protocole de communication avec un serveur SMTP (côté client).

## Application

Cette application permet d'envoyer des mails forgés à des listes d'adresse e-mail proposées.

* La liste des adresses e-mail est définie par le fichier "src/sources/destinataires.txt" chaque adresse devant être séparée par un retour à la ligne

* La liste des messages est définie par le fichier "src/sources/messages.txt" la structure de ce fichier est la suivante: **sujet du message **retour à la ligne **corps du message (retours à la ligne supportés pour structurer le message) **retour à la ligne **mot clé ENDOFMSAGE pour signaler que le corps du message est terminé

* L'application commence par demander combien de groupes d'envoi nous voulons (1 minimum)

* Elle propose ensuite de composer les différents groupes avec une liste composée des adresses disponibles dans destinataires.txt (2 adresses minimum par groupe)

* L'étape suivante consiste à entrer, pour chaque groupe, une adresse qui sera l'expéditeur du e-mail.

* Ensuite, il est demandé à l'utilisateur de sélectionner, pour chaque groupe, quel message (parmis ceux disponibles dans messages.txt) sera envoyé à la liste d'adresses associée au groupe.

* Une fois la configuration terminée, l'application enverra la collection de messages.

## Configuration

Pour l'ajout d'adresses à la liste et l'ajout de messages, voir la section Application. Actuellement, l'application se connecte à un serveur SMTP configuré en local (localhost, 25). Pour se connecter à un autre serveur SMTP, il suffit d'éditer le fichier "src/prankmail/Main.java". Vous trouverez dans celui-ci, à la ligne 10, le serveur. Remplacez "localhost" par le DNS ou l'adresse de votre serveur SMTP. À la ligne suivante, vous pouvez également modifier le port si ce n'est pas le 25.








