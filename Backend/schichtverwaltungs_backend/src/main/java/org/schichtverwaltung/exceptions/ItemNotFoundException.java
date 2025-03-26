package org.schichtverwaltung.exceptions;

//Exception für sollte der gesuchte wert nicht gefunden werden
public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
