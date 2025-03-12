package org.schichtverwaltung.objectStructure;

import java.util.ArrayList;

public class EventPool {

    private ArrayList<Event> events;

    public void addEvent (Event event) {
        events.add(event);
    }
}
