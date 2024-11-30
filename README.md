# PROJECT3

## **1. Procédure pour installer **
Exécutez les commandes suivantes dans votre terminal pour cloner le projet et accéder au répertoire :

```bash
git clone git@github.com:Galouu/PROJECT3.git
cd PROJECT3
```

\\\\\======================================\\\\\

## **2. Configurer le fichier application.properties **
Ouvrez le fichier src/main/resources/application.properties et configurez-le avec vos informations MySQL :

```bash
spring.application.name=SpringSecurityAuth
spring.datasource.url=jdbc:mysql://localhost:3306/rental_app
spring.datasource.username=root
spring.datasource.password=<TON_MOT_DE_PASSE_MYSQL>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.mvc.pathmatch.matching-strategy=ant_path_matcher
```
Remarque : Remplacez <TON_MOT_DE_PASSE_MYSQL> par le mot de passe de votre utilisateur MySQL.

\\\\\======================================\\\\\

## **3. Lancer le projet **

Avec un IDE (IntelliJ IDEA, Eclipse, etc.)
Ouvrez le projet dans votre IDE.

Allez dans Exécuter et cliquez sur "Exécuter"
L'application sera disponible sur http://localhost:8080.

\\\\\======================================\\\\\

## **4. Procédure d’installation de la base de données **
Créer une base de données : 

```bash
mysql -u root -p
CREATE DATABASE rental_app;
```

\\\\\======================================\\\\\

## **5. Swagger **

Accédez à l'URL suivante une fois l'application démarrée : http://localhost:8080/swagger-ui.html

