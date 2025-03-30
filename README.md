# Schichtverwaltungssystem
Das Schichtverwaltungssystem bietet die Möglichkeit Veranstaltungen anzulegen auf welche sich anschließend Personen auf die in der Veranstaltung definierten Schichten eintragen können.

## Installation/Inbetriebnahme

**Info**: Aktuell ist nur der Betrieb auf einer Maschine (Frontend und Backend auf einem PC) implementiert und getestet.

**Info**: Das Schichtverwaltungssystem ist nur für Windows 10 & 11 implementiert und getestet.

**Info**: Das Frontend ist sowohl am Desktop PCs als auch an Handys nutzbar.

### Frontend
Zur Inbetriebnahme werden nur die HTML, JS und CSS Dateien benötigt.
Das Passwort für den Management Bereich lautet `richtig`
Die `Schichtverwaltung_Overview.html` öffnen um auf das Frontend zu gelangen.

### Backend
Zur Inbetriebnahme des Backend werden folgende punkte benötigt:
1. Eine Java Runtime Environment 22 muss auf dem PC Installiert sein. Diese können beispielsweise bei OpenJDK heruntergeladen werden (https://openjdk.org/)
2. Die `sichtverwaltungs_backend-1.0.jar` muss aus GitHub heruntergeladen werden.
3. Die SQLite Datenbank muss aus GitHub heruntergeladen werden.
4. Im gleichen Verzeichnis in welchem die `sichtverwaltungs_backend-1.0.jar` ist muss sich auch die `SchichtverwaltungsBackendConfig.ymal` befinden. In dieser müssen zwei Einträge vorhanden sein. Einmal `DBpath:` mit dem Pfad zur Datenbank und zweitens `LOGpath:` welches angibt wo sich die Logdatei befinden soll. Sollte die Logdatei noch nicht unter diesem Pfad existieren wird diese durch das Backend angelegt.
5. Das Backend mit `java -jar .\sichtverwaltungs_backend-1.0.jar` aus der Konsole heraus starten.
6. Anschließend wird der Webserver gestartet und die API Schnittstellen des Backend stehen dem Frontend zur Verfügung. In der Konsole werden nun alle anfragen mit jeweiligen HTTP Response Code angezeigt. In der Logdatei finden man zudem noch die übersendeten JSON Bodys.

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

Es werden 9 API Schnittstellen durch das Backend bereit gestellt:
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
			-  `JSON`: Im Body muss sich ein `JSON` nach gleichem Format wie beim **Hinzufügen eines Events** befinden. ***Wichtig***! Neue IDs müssen mit -1  angegeben werden. ***Wichtig***! Die `JSON` muss in einem `{event: JSON}` sitzen.
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
	- *Delete Worker from Shift* (`/deleteWorker/{workerID}`)
		- Übergabe in URL:
			-`{workerID}`: ID welchen die zu Löschende Person Identifiert
		- Rückgabe:
			- `200 - OK`: Rückgabe ist ein einfacher Text welcher angibt das die Aktion erfolgreich war
			- `404 - NOT_FOUND`: Sollte die angegebene `eventID` nicht gefunden werden
			- `500 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
