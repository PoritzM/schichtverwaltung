package org.schichtverwaltung.exceptions;

//Exception für wen es nicht erlaubt es diese aktion auszuführen
public class BlockedActionException extends Exception {

    public BlockedActionException (String message) {
        super(message);
    }
}
