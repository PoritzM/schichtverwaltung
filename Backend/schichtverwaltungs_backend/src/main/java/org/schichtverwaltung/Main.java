package org.schichtverwaltung;

import org.schichtverwaltung.objectStructure.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static org.schichtverwaltung.dbTools.InitConnection.connectToDB;

public class Main {
    public static void main(String[] args) {

        connectToDB();


//        EventPool eventPool = new EventPool();
//
//        Event event1 = new Event(1, "Stadtfest 2024", true, true);
//            Day day1 = new Day(1, LocalDate.of(2024,6,4));
//            event1.addDay(day1);
//                Service service1 = new Service(1, "Dienst 1", LocalTime.of(17,0), LocalTime.of(19,0));
//                day1.addService(service1);
//                    Task task1 = new Task(1, "Spülen");
//                    service1.addTask(task1);
//                        Worker worker1 = new Worker(1, "Moritz");
//                        task1.addWorker(worker1);
//                        Worker worker2 = new Worker(2, "Stefan");
//                        task1.addWorker(worker2);
//                    Task task2 = new Task(2, "Bier Zapfen");
//                    service1.addTask(task2);
//                        Worker worker3 = new Worker(3, "Felix");
//                        task2.addWorker(worker3);
//                    Task task3 = new Task(3, "DJ");
//                    service1.addTask(task3);
//                Service service2 = new Service(2, "Dienst 2", LocalTime.of(19,0), LocalTime.of(21,0));
//                day1.addService(service2);
//                    Task task4 = new Task(4, "Spülen");
//                    service1.addTask(task4);
//                    Task task5 = new Task(5, "Bier Zapfen");
//                    service1.addTask(task5);
//                    Task task6 = new Task(6, "DJ");
//                    service1.addTask(task6);
//                Service service3 = new Service(3, "Dienst 3", LocalTime.of(21,0), LocalTime.of(23,0));
//                day1.addService(service3);
//                    Task task7 = new Task(7, "Spülen");
//                    service1.addTask(task7);
//                    Task task8 = new Task(8, "Bier Zapfen");
//                    service1.addTask(task8);
//                    Task task9 = new Task(9, "DJ");
//                    service1.addTask(task9);
//                Service service4 = new Service(4, "Dienst 4", LocalTime.of(23,0), LocalTime.of(2,0));
//                day1.addService(service4);
//
//        event1.print();


//            Day day2 = new Day(2, LocalDate.of(2024,6,5));
//            event1.addDay(day1);

    }
}