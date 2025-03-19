package org.schichtverwaltung.restTest;

import org.schichtverwaltung.functions.AddShift;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shifts") // Definiert den Basis-Pfad für die Endpunkte
public class ShiftController {

    @PostMapping
    public void processShift(@RequestBody String jsonString) {
        AddShift.addShift(jsonString);
    }
}