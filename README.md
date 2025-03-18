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

### Beispiel Veranstaltung


				





- **Veranstaltung**: Wettkampf
	- **Veranstaltung Anzeigen:** Ja
	- **Eintragen zu Veranstaltung erlauben:** Ja
	- **Tage der Veranstaltung:**
		- **Tag:** 18.03.2025
			- **Dienste dieses Tages:**
				- **Beschreibung:** Vorbereitung
				- **Startzeit:** 16:00
				- **Endzeit:** 20:00
				- **Aufgaben des Dienstes:**
					- Beschreibung:
						- Benötigte
		- **Tag:** 19.03.2025

## Frontend

## Backend

### Schnittstellen

    { 	
    	    "worker": "Max", 	
    	    "eventID": 1, 	
    	    "dayID": 2, 	
    	    "serviceID": 2, 	
    	    "tastID": 3 
    }
