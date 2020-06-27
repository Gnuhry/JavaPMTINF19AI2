### Installationsanweisung
Im folgenden finden Sie eine Erklärung zur Installation der Applikation und des Docker-Containers für die Datenbank:

1. Docker-Desktop installieren ([Docker-Desktop](https://www.docker.com/products/docker-desktop))
2. Docker-Image über `docker pull j4yj4y/dhbw-mysql` installieren
3. Die Datenbank über den Befehl `docker run -p 3306:3306 --name dhbw -d j4yj4y/dhbw-mysql` starten
4. Auf [GitHub](https://github.com/Gnuhry/JavaPMTINF19AI2/releases/latest) den neusten Release downloaden und entpacken
5. Java 14 installieren und zur Path-Variable hinzufügen
(Damit die .exe funktioniert, muss die JAVA_HOME Variable auf Java 14 gesetzt sein; Beispiel: C:\programme\java\jdk_14)
6. Die .exe per Doppelklick starten oder die JAR über die Batch-Datei starten
7. Datenbank kann über den Befehl `docker stop dhbw` gestoppt werden
8. Zum erneuten Starten kann `docker start dhbw` genutzt werden


### Tastenkombinationen im Überblick Fenster:
Tastenkombination|Erklärung
-----------------|-------------------
|delete/entf|löschen|
|escape|auswahl entfernen|
|enter|bearbeiten (nur wenn eine zeiele ausgewählt ist)|
|control u|mail zu uni mail ändern|
|control m|mail senden|
|control shift m|mail mit direkot beim kurs senden|
|control r|refresh|
|control left|zum linken Tab wechseln|
|control right|zum rechtenTab wechseln|
|control und klick auf email addresse|Mail zur addresse|                          
