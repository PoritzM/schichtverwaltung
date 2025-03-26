package org.schichtverwaltung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.schichtverwaltung.logger.Logger.logger;

@SpringBootApplication
public class SchichtverwaltungsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchichtverwaltungsBackendApplication.class, args);
        
        String filePath = System.getProperty("user.dir");
        logger(100, "Webserver Started - " + "Path of Backend: " + filePath, "NO JSON BODY");
    }
}