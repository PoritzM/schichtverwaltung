# Schichtverwaltungssystem
Das Schichtverwaltungssystem bietet die Möglichkeit Veranstaltungen anzulegen auf welche sich anschließend Personen auf die in der Veranstaltung definierten Schichten eintragen können.

## Frontend Todo`s:

- Übersichtsseite
	- Style Desktop
	- Style Mobile
	- Klickbox "Sichtverwaltung" anpassen. (Geht horizontal komplett über die Seite)
	- ~~Mouseover zur Startseite nicht vorhanden (Turnverein St. Ingbert)~~
- Veranstaltungs-Seite
	- Style Desktop
	- Style Mobile
	- Dropdown für Dienste
	- Bestätigung wen erfolgreich in Event eingetragen
	- Seite nicht immer neu laden bei eintragen -> Position beibehalten (Scroll Position)
	- ~~Mouseover zur Startseite nicht vorhanden (Turnverein St. Ingbert)~~
- Verwaltungslogin-Seite
	- ~~Fehlermeldung bei Falschem Passwort~~
	- ~~Mouseover zur Startseite nicht vorhanden (Turnverein St. Ingbert)~~
- Verwaltungsübersicht-Seite
	- Style Desktop
	- ~~Möglichkeit Register On Event -> ON/OFF~~
	- ~~Möglichkeit Show Event -> ON/OFF~~
	- ~~Mouseover zur Startseite nicht vorhanden (Turnverein St. Ingbert)~~
- Veranstaltung-Anlegen-Seite
  	- ~~Style Desktop~~
	- ~~Mouseover zur Startseite nicht vorhanden (Turnverein St. Ingbert)~~
- Veranstaltung-Bearbeiten-Seite
	- Möglichkeit Formular wie bei "Veranstaltung Anlegen" mit werten vorzufühlen und Änderungen an eingetragenen werten durchzuführen  

## Grundstruktur
Es gibt mehrere hierarchiestufen um die Schicht anzulegen und zu verwalten. Diese einzelnen Stufen haben wiederum weitere Informationen
 - Veranstaltungen (Events)
	 - Veranstaltung Name
	 - Veranstaltung Anzeigen
	 - Eintragen zu Veranstaltung erlauben
 - Tage (Days)
	 - Tag an welchem die Veranstaltung ist
 - Dienste (Services)
	 - Dienst Beschreibung
	 - Uhrzeit wann der dienst beginnt
	 - Uhrzeit wann der dienst endet
 - Aufgaben (Tasks)
	 - Aufgaben Beschreibung
	 - Benötigte Personen
 - Personen (Worker)
	 - Name der Person

## RESTApi Schnittstellen

**Wichtig: "Shift" bezeichnet immer das gesamte Konstrukt aus mehreren zu einem Event gehörenden Tagen, Aufgaben usw.**

Es werden 9 API Schnittstellen bereit gestellt:
- **Post**
	- *Post Shift* (`/shifts/addShift`)
		- Übergabe im Body:
			-  `JSON`: Mit den Infos für eine Veranstaltung. Eine Beispiel JSON findet sich unter `Backend/Example/PostShift_input`
		- Rückgabe:
			- `201 - CREATED`: Rückgabe ist ein Text mit allen IDs der erstellten Einträge. Ein Beispiel findet sich unter `Backend/Example/PostShift_output`
			- `500 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
	- *Post Worker* (`/shifts/addWorker`)
		- Übergabe im Body: 
			- `JSON`: Mit den Infos für einen Arbeiter. Eine Beispiel JSON findet sich unter `Backend/Example/PostWorker_input`
		- Rückgabe:
			- `201 - CREATED`: Rückgabe ist ein Text mit allen IDs der erstellten Einträge. Ein Beispiel findet sich unter `Backend/Example/PostShift_output`
			- `403 - FORBIDDEN`: Sollte das Feld `registerOnEvent` im Event auf `false` sein
			- `404 - NOT_FOUND`: Sollte eine der angegebenen IDs nicht vorhanden sein
			- `500 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
- **Get**
	- *Get Shift* (`/getShift/{eventID}`)
		- Übergabe in URL:
			- `{eventID}`: ID welche die Veranstaltung identifiziert
		- Rückgabe:
			- `200 - OK`: Liefert ein `JSON` zurück mit allen Informationen zu dieser Veranstaltung. Ein Beispiel findet sich unter `Backend/Example/GetShift_output`
			- `404 - NOT_FOUND`: Sollte die angegebene `eventID` nicht gefunden werden
			- `500 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
	- *Get Events* (`/getEvents`)
		- Übergabe im Body:
			- Es wird keine Übergabe benötigt.
		- Rückgabe:
			- `200 - OK`: Liefert ein `JSON` zurück mit allen grundlegenden Informationen zu allen Veranstaltungen. Ein Beispiel findet sich unter `Backend/Example/GetEvents_output`
			- `500 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
- **Update**
	- *Update Register on Event* (`/updateRegister/{eventID}`)
		- Übergabe in URL:
			- `{eventID}`: ID welche die Veranstaltung identifiziert
		- Übergabe im Body:
			- `JSON`: Im Body muss sich ein `true` oder `false` befinden (Sonst nicht. Dies muss nur als JSON definiert sein)
		- Rückgabe:
			- `200 - OK`: Rückgabe ist ein einfacher Text welcher angibt das die Aktion erfolgreich war
			- `404 - NOT_FOUND`: Sollte die angegebene `eventID` nicht gefunden werden
			- `409 - CONFLICT`: Sollte der übergebene wert schon dem gesetzten wert übereinstimmen
			- `500 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
	- *Update Show Event* (`/updateShowEvent/{eventID}`)
		- Übergabe in URL:
			- `{eventID}`: ID welche die Veranstaltung identifiziert
		- Übergabe im Body:
			-  `JSON`: Im Body muss sich ein `true` oder `false` befinden (Sonst nicht. Dies muss nur als JSON definiert sein)
		- Rückgabe:
			- `200 - OK`: Rückgabe ist ein einfacher Text welcher angibt das die Aktion erfolgreich war
			- `404 - NOT_FOUND`: Sollte die angegebene `eventID` nicht gefunden werden
			- `409 - CONFLICT`: Sollte der übergebene wert schon dem gesetzten wert übereinstimmen
			- `500 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
	- *Update Shift* (`/updateShift`)
		- Übergabe im Body:
			-  `JSON`: Im Body muss sich ein `JSON` nach gleichem Format wie beim **Geten eines Events** befinden. ***Wichtig***! Neue IDs müssen mit -1 angegeben werden. ***Wichtig***! Die `JSON` muss in einem `{event: {JSON}}` sitzen.
		- Rückgabe:
			- `404 - NOT_FOUND`: Sollte eine der angegebenen IDs nicht gefunden werden.
			- `500 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
- **Delete**
	- *Delete Shift* (`/deleteWorker/{workerID}`)
		- Übergabe in URL:
			-`{eventID}`: ID welche die Veranstaltung identifiziert
		- Rückgabe:
			- `200 - OK`: Rückgabe ist ein einfacher Text welcher angibt das die Aktion erfolgreich war
			- `404 - NOT_FOUND`: Sollte die angegebene `eventID` nicht gefunden werden
			- `500 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
	- *Delete Worker from Shift* (`/deleteShift/{eventID}`)
		- Übergabe in URL:
			-`{eventID}`: ID welche die Veranstaltung identifiziert
		- Rückgabe:
			- `200 - OK`: Rückgabe ist ein einfacher Text welcher angibt das die Aktion erfolgreich war
			- `404 - NOT_FOUND`: Sollte die angegebene `eventID` nicht gefunden werden
			- `500 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
