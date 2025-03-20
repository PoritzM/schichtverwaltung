package org.schichtverwaltung.exceptions;

public class ValueAlreadySetException extends RuntimeException {
    public ValueAlreadySetException(String message) {
        super(message);
    }
}
