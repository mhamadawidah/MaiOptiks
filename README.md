# Prototyp einer Branchenlösung für das Optikerhandwerk zur Inventar- und Kundenbetreuung

[Home](http://localhost:8080/)   
[Swagger UI (API Documentation)](http://localhost:8080/api/swagger)   
[H2 Console (Database)](http://localhost:8080/console)   
 

## Database
| JDBC URL  | jdbc:h2:file:~/MaiOptiks/database |
|-----------|-----------------------------------|
| User Name | admin                             |
| Password  | admin                             |


## Springboot + Thymeleaf

Der Sinn der MVC-Design-Architektur (Model–View–Controller) besteht darin, Anfragen immer an Controller zu senden, die sie dann an Views weiterleiten.
D.h. jede View (HTML-Seite) sollte ihren eigenen Controller haben.

- HTML-Seiten werden unter ``src/main/resources/templates`` erstellt. Es können auch Unterordner angelegt werden.
- Controller werden unter ``src/main/java//MaiOptiks/routing`` erstellt.

### Beispiel einer Seite
``` html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" th:href="@{/css/main.css}"/>
</head>
<body>
<p>Hello Thymeleaf!<p>
</body>
</html>

```

### Der Controller
``` java
package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @RequestMapping("/myurl") // beliebige URL
    public String myMethod() {
        return "subfolder/mypage"; // Seitenpfad
    }
}
```

### Verlinkung
Sobald die Seite einen eigenen Controller hat, kann sie wie folgt verlinkt werden
``` html
<a th:href="@{/myurl}"> Click me! </a> <!-- Hier wirde die Url der Mapping-Methode verwendet -->
```


## Ist-Analyse

- Die Software verwendet ein veraltetes Datenbanksystem (MS-Access).
- Die Gestaltung der Benutzeroberfläche ist nicht mehr aktuell.

## Soll-Analyse

- Es soll ein modernes Datenbanksystem (SQL) verwendet werden.
- Es soll eine moderne, benutzerfreundliche Weboberfläche gestaltet werden.

## Struktur
* Frontend: HTML
* Backend: Java Springboot
* Database: MySQL

## Git Befehle
| Befehl                        | Beschreibung                            |
|-------------------------------|-----------------------------------------|
| git fetch                     | Zieht alle Änderungen                   |
| git merge origin/develop      | Merged diese Änderungen                 |
| git checkout <branch_name>    | Branch wechseln                         |
| git checkout -b <branch_name> | Neue Branch erstellen und rein wechseln |
| git pull \<repository\>       | Daten aus Repository ziehen             |

## Links
[Lastenheft](https://kstlinfo-my.sharepoint.com/:w:/g/personal/marten_knystock_campus_kstl_de/EWdrL29u_n9MoWcfNHLSBcoBSCiM-zFt9eo9uOuwIlvDog?e=k9mJ6w)
<br>
[Pflichtenheft](https://kstlinfo-my.sharepoint.com/:w:/g/personal/tom_volmer_campus_kstl_de/EQo7P0h-HmlJqI1qdyyq3ZwBZkbRvNAZmD0urwarAb6m0w?rtime=PGvW8PWA2kg)
<br>
[Netzplan](https://kstlinfo-my.sharepoint.com/:x:/g/personal/marten_knystock_campus_kstl_de/EStvpgzskKpLkfu888DZk0cBtcmsMb1jRR7kZ5JvLuGoBw)
<br>
[Database ER Diagram](https://lucid.app/lucidchart/08941d25-94ba-4ccc-87b8-e5279fa2f4c5/edit?viewport_loc=-199%2C-21%2C3426%2C1558%2C0_0&invitationId=inv_fa3c614f-6ac6-4183-a9d4-6889f62b9cfc#)
<br>
[Planung UI](https://app.moqups.com/Zbmm5mG5cZXwIF5PqtXo1HitC3PoHLhZ/view/page/ad64222d5)
<br>
[SQL Commands](https://kstlinfo-my.sharepoint.com/:w:/g/personal/tom_volmer_campus_kstl_de/EYrUUIaw3alJsl7vVZM0Y0ABsxy7KA6Vl1CtLfj-cNjzYA?e=UhQrtM)
<br>
[Gestaltungsprinzipien-GUI](https://kstlinfo-my.sharepoint.com/:w:/g/personal/frederik_niehaus_campus_kstl_de/EcWEIKDGMy1ArE_qtgtFMDMBiosmJyTMwmDV3nzhrczZiQ?e=J3oAxF)
<br>
[Sequenzdiagramm](https://lucid.app/lucidchart/46e6ae4b-b368-4175-91a9-97e53a2ae29a/edit?viewport_loc=485%2C546%2C2969%2C1350%2C0_0&invitationId=inv_a4d777bd-0825-4ba3-bb75-bf0e3e5f04bc)
<br>
[Klassen- & Aktivitätsdiagramm](https://lucid.app/lucidchart/74e23248-d422-45c8-9d10-2eb39771ab80/edit?invitationId=inv_5070b5eb-c54e-4dc1-87b9-4d0cc70f2180&page=0_0#)
<br>
[Offene Fragen](https://kstlinfo-my.sharepoint.com/:w:/g/personal/tom_volmer_campus_kstl_de/EUrBypv57p1ErC1QBrgPygkBY4vRZXs8ILlXe7xXHVHI8g?e=I6wIcR)
## Links external ressources
[ISO-3166 ALPHA2](https://de.wikipedia.org/wiki/ISO-3166-1-Kodierliste)
<br>

## Vorgehensweise Bestellvorgang
Stärke (Spähre, Zylinder, Achse, Addition, Wisus(Monokular), Wisus(Binokular), PD(Augenabstand), Höhe, HSA(Hornhautscheitelabstand)--> Hersteller auswahl(Zeiss etc. 
--> Glasart(Gleitsicht, Bifokal oder Einstärken Glas) --> Material(Mineral oder Kunststoff)
