package org.schichtverwaltung.exceptions;

//Exception sollte der wert der gesetzt werden soll schon gesetzt ist
public class ValueAlreadySetException extends RuntimeException {
    public ValueAlreadySetException(String message) {
        super(message);
    }
}
