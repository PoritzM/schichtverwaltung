package org.schichtverwaltung.exceptions;

//Exception für allgemeine Fehler im Backend
public class BackendException extends Exception {

    public BackendException (String message) {
        super(message);
    }
}
