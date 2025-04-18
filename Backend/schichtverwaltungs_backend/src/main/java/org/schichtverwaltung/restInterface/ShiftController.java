package org.schichtverwaltung.restInterface;

import org.schichtverwaltung.exceptions.BackendException;
import org.schichtverwaltung.exceptions.BlockedActionException;
import org.schichtverwaltung.exceptions.ItemNotFoundException;
import org.schichtverwaltung.exceptions.ValueAlreadySetException;
import org.schichtverwaltung.zUtils.ReturnInfos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.schichtverwaltung.functions.AddShift.doAddShift;
import static org.schichtverwaltung.functions.AddWorkerToShift.doAddWorkerToShift;
import static org.schichtverwaltung.functions.RemoveShift.doRemoveShift;
import static org.schichtverwaltung.functions.RemoveWorkerFromShift.doRemoveWorkerFromShift;
import static org.schichtverwaltung.functions.SelectShift.doSelectShift;
import static org.schichtverwaltung.functions.SelectShift.doSelectShiftOverview;
import static org.schichtverwaltung.functions.UpdateEvent.doUpdateRegisterOnEvent;
import static org.schichtverwaltung.functions.UpdateEvent.doUpdateShowEvent;
import static org.schichtverwaltung.functions.UpdateShift.doUpdateShift;
import static org.schichtverwaltung.logger.Logger.logger;

//Definition der RESTApi Schnittstellen des Backendes

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/shifts")
public class ShiftController {

    //Hinzufügen einer kompletten Schicht
    @PostMapping("/addShift")
    public ResponseEntity<String> processShift(@RequestBody String jsonString) {

        try {
            ReturnInfos returnInfos = doAddShift(jsonString);
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.CREATED).body("Insert Shift: [Successful] " + returnInfos.toString());
            logger(response.getStatusCode().value(), response.getBody(), jsonString);
            return response;
        } catch (BackendException backendException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Insert Shift: [Failed] " + backendException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), jsonString);
            return response;
        } catch (Exception exception) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Insert Shift: [Failed] Major Error!: " + exception.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        }
    }

    //Hinzufügen einer Person zu einer Aufgabe
    @PostMapping("/addWorker")
    public ResponseEntity<String> processWorker(@RequestBody String jsonString) {
        try {
            ReturnInfos returnInfos = doAddWorkerToShift(jsonString);
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.CREATED).body("Insert Worker: [Successful] " + returnInfos.toString());
            logger(response.getStatusCode().value(), response.getBody(), jsonString);
            return response;
        } catch (BackendException backendException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Insert Worker: [Failed] " + backendException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), jsonString);
            return response;
        } catch (BlockedActionException blockedActionException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insert Worker: [Failed] " + blockedActionException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), jsonString);
            return response;
        } catch (ItemNotFoundException itemNotFoundException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insert Worker: [Failed] " + itemNotFoundException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), jsonString);
            return response;
        } catch (Exception exception) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Insert Worker: [Failed] Major Error!: " + exception.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        }
    }

    //Eine gewisse Schicht anfragen
    @GetMapping ("/getShift/{eventID}")
    public ResponseEntity<String> getEvent(@PathVariable int eventID) {
        try {
            String returnString = doSelectShift(eventID);
            ResponseEntity<String> response = new ResponseEntity<>(returnString, HttpStatus.OK);
            logger(response.getStatusCode().value(),"Get Shift: [Successful] (EventID: " + eventID + ") ", returnString);
            return response;
        } catch (BackendException backendException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Get Shift: [Failed] (EventID: " + eventID + ") " + backendException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        } catch (ItemNotFoundException itemNotFoundException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Get Shift: [Failed] (EventID: " + eventID + ") " + itemNotFoundException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        } catch (Exception exception) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Get Shift: [Failed] (EventID: " + eventID + ") Major Error!: " + exception.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        }
    }

    //Alle Veranstaltung und Tage anfragen
    @GetMapping ("/getEvents")
    public ResponseEntity<String> getShift() {
        try {
            String returnString = doSelectShiftOverview();
            ResponseEntity<String> response = new ResponseEntity<>(returnString, HttpStatus.OK);
            logger(response.getStatusCode().value(), "Get Events: [Successful] ", returnString);
            return response;
        } catch (BackendException backendException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Get Events: [Failed] " + backendException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        } catch (Exception exception) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Get Events: [Failed] Major Error!: " + exception.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        }
    }

    //Aktualisieren von Register On Event
    @PutMapping ("/updateRegister/{eventID}")
    public ResponseEntity<String> updateRegister (@RequestBody boolean bool, @PathVariable int eventID) {
        try {
            doUpdateRegisterOnEvent(eventID, bool);
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body("Update Register on Event: [Successful] (EventID: " + eventID + " Boolean Value: " + bool + ") ");
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        } catch (BackendException backendException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Register on Event: [Failed] (EventID: " + eventID + " Boolean Value: " + bool + ") " + backendException);
            logger(response.getStatusCode().value(), response.getBody(),"NO JSON BODY");
            return response;
        } catch (ItemNotFoundException itemNotFoundException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update Register on Event: [Failed] (EventID: " + eventID + " Boolean Value: " + bool + ") " + itemNotFoundException);
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        } catch (ValueAlreadySetException valueAlreadySetException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.CONFLICT).body("Update Register on Event: [Failed] (EventID: " + eventID + " Boolean Value: " + bool + ") " + valueAlreadySetException);
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        } catch (Exception exception) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Register on Event: [Failed] (EventID: " + eventID + " Boolean Value: " + bool + ") Major Error: " + exception.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        }
    }

    //Aktualisieren von Show Event
    @PutMapping ("/updateShowEvent/{eventID}")
    public ResponseEntity<String> updateShowEvent (@RequestBody boolean bool, @PathVariable int eventID) {
        try {
            doUpdateShowEvent(eventID, bool);
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body("Update Show Event: [Successful] (EventID: " + eventID + " Boolean Value: " + bool + ") ");
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        } catch (BackendException backendException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Show Event: [Failed] (EventID: " + eventID + " Boolean Value: " + bool + ") " + backendException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(),"NO JSON BODY");
            return response;
        } catch (ItemNotFoundException itemNotFoundException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update Show Event: [Failed] (EventID: " + eventID + " Boolean Value: " + bool + ") " + itemNotFoundException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        } catch (ValueAlreadySetException valueAlreadySetException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.CONFLICT).body("Update Show Event: [Failed] (EventID: " + eventID + " Boolean Value: " + bool + ") " + valueAlreadySetException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        } catch (Exception exception) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Show Event: [Failed] (EventID: " + eventID + " Boolean Value: " + bool + ") Major Error: " + exception.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        }
    }

    //Aktualisieren einer kompletten Schicht
    @PutMapping ("/updateShift")
    public ResponseEntity<String> updateShift (@RequestBody String jsonString) {
        try {
            doUpdateShift(jsonString);
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body("Update Shift: [Successful] ");
            logger(response.getStatusCode().value(), response.getBody(), jsonString);
            return response;
        } catch (BackendException backendException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Shift: [Failed] " + backendException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(),jsonString);
            return response;
        } catch (ItemNotFoundException itemNotFoundException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update Shift: [Failed] " + itemNotFoundException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), jsonString);
            return response;
        } catch (Exception exception) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Shift: [Failed] Major Error: " + exception.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        }
    }

    //Löschen einer Schicht
    @DeleteMapping ("/deleteShift/{eventID}")
    public ResponseEntity<String> deleteShift (@PathVariable int eventID) {
        try {
            doRemoveShift(eventID);
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body("Delete Shift: [Successful] (EventID: " + eventID + ") ");
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        } catch (BackendException backendException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete Shift: [Failed] (EventID: " + eventID + ") " + backendException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(),"Delete Shift failed: " + backendException.getMessage());
            return response;
        } catch (ItemNotFoundException itemNotFoundException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete Shift: [Failed] (EventID: " + eventID + ") " + itemNotFoundException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        } catch (Exception exception) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete Shift: [Failed] (EventID: " + eventID + ") Major Error: " + exception.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        }
    }

    //Löschen einer Person aus einer Aufgabge
    @DeleteMapping ("/deleteWorker/{workerID}")
    public ResponseEntity<String> deleteWorker (@PathVariable int workerID) {
        try {
            doRemoveWorkerFromShift(workerID);
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body("Delete Worker: [Successful] (WorkerID: " + workerID + ") ");
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        } catch (BackendException backendException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete Worker: [Failed] (WorkerID: " + workerID + ") " + backendException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(),"NO JSON BODY");
            return response;
        } catch (ItemNotFoundException itemNotFoundException) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete Worker: [Failed] (WorkerID: " + workerID + ") " + itemNotFoundException.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        } catch (Exception exception) {
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete Worker: [Failed] (WorkerID: " + workerID + ") Major Error: " + exception.getMessage());
            logger(response.getStatusCode().value(), response.getBody(), "NO JSON BODY");
            return response;
        }
    }
}
