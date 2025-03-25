# Schichtverwaltungssystem
Das Schichtverwaltungssystem bietet die Möglichkeit Veranstaltungen anzulegen auf welche sich anschließend Personen auf die in der Veranstaltung definierten Schichten eintragen können.

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

## Frontend

## Backend
Das Backend basiert auf Java und bietet eine RESTful API Schnittstelle über http/https an um an die Daten aus der SQLite Datenbank zu gelangen.

**Wichtig: "Shift" bezeichnet immer das gesamte Konstrukt aus mehreren zu einem Event von mehreren Tagen Aufgaben usw.**

### Schnittstellen

Es werden 8 API Schnittstellen bereit gestellt:
- **Post**
	- *Post Shift* (`/shifts/addShift`)
		- Übergabe:
			-  `JSON`: Mit den Infos für eine Veranstaltung. Eine Beispiel JSON findet sich unter `Backend/Example/PostShift_input`
		- Rückgabe:
			- `201 - CREATED`: Rückgabe ist ein Text mit allen IDs der erstellten Einträge. Ein Beispiel findet sich unter `Backend/Example/PostShift_output`
			- `400 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehler
	- *Post Worker* (`/shifts/addWorker`)
		- Übergabe: 
			- `JSON`: Mit den Infos für einen Arbeiter. Eine Beispiel JSON findet sich unter `Backend/Example/PostWorker_input`
		- Rückgabe:
			- `201 - CREATED`: Rückgabe ist ein Text mit allen IDs der erstellten Einträge. Ein Beispiel findet sich unter `Backend/Example/PostShift_output`
			- `400 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehler
			- `403 - FORBIDDEN`: Sollte das Feld `registerOnEvent` im Event auf `false` sein
			- `404 - NOT_FOUND`: Sollte eine der angegebenen IDs nicht vorhanden sein
- **Get**
	- *Get Shift* (`/getShift/{eventID}`)
		- Übergabe:
			- `{eventID}`: ID welche die Veranstaltung identifiziert
		- Rückgabe:
			- `200 - OK`: Liefert ein `JSON` zurück mit allen Informationen zu dieser Veranstaltung. Ein Beispiel findet sich unter `Backend/Example/GetShift_output`
			- `400 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
			- `404 - NOT_FOUND`: Sollte die angegebene `eventID` nicht gefunden werden
	- *Get Events* (`/getEvents`)
		- Übergabe:
			- Es wird keine Übergabe benötigt.
		- Rückgabe:
			- `200 - OK`: Liefert ein `JSON` zurück mit allen grundlegenden Informationen zu allen Veranstaltungen. Ein Beispiel findet sich unter `Backend/Example/GetEvents_output`
			- `400 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
- **Update**
	- *Update Register on Event* (`/updateRegister/{eventID}`)
		- Übergabe:
			- `{eventID}`: ID welche die Veranstaltung identifiziert
			- `JSON`: Im Body muss sich ein `true` oder `false` befinden (Sonst nicht. Dies muss nur als JSON definiert sein)
		- Rückgabe:
			- `200 - OK`: Rückgabe ist ein einfacher Text welcher angibt das die Aktion erfolgreich war
			- `400 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
			- `404 - NOT_FOUND`: Sollte die angegebene `eventID` nicht gefunden werden
			- `409 - CONFLICT`: Sollte der übergebene wert schon dem gesetzten wert übereinstimmen
	- *Update Show Event* (`/updateShowEvent/{eventID}`)
		- Übergabe:
			-`{eventID}`: ID welche die Veranstaltung identifiziert
			- `JSON`: Im Body muss sich ein `true` oder `false` befinden (Sonst nicht. Dies muss nur als JSON 
		- Rückgabe:
			- `200 - OK`: Rückgabe ist ein einfacher Text welcher angibt das die Aktion erfolgreich war
			- `400 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
			- `404 - NOT_FOUND`: Sollte die angegebene `eventID` nicht gefunden werden
			- `409 - CONFLICT`: Sollte der übergebene wert schon dem gesetzten wert übereinstimmen
- **Delete**
	- *Delete Shift* (`/deleteWorker/{workerID}`)
		- Übergabe:
			-`{eventID}`: ID welche die Veranstaltung identifiziert
		- Rückgabe:
			- `200 - OK`: Rückgabe ist ein einfacher Text welcher angibt das die Aktion erfolgreich war
			- `400 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
			- `404 - NOT_FOUND`: Sollte die angegebene `eventID` nicht gefunden werden
	- *Delete Worker from Shift* (`/deleteShift/{eventID}`)
		- Übergabe:
			-`{eventID}`: ID welche die Veranstaltung identifiziert
		- Rückgabe:
			- `200 - OK`: Rückgabe ist ein einfacher Text welcher angibt das die Aktion erfolgreich war
			- `400 - INTERNAL_SERVER_ERROR`: Allgemeiner undefinierter Fehlern
			- `404 - NOT_FOUND`: Sollte die angegebene `eventID` nicht gefunden werden
