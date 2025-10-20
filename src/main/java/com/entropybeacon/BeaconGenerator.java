package com.entropybeacon;

import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Date;
import java.util.Arrays;
import java.util.HexFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;



@Service
public class BeaconGenerator {

    private static final String FILENAME = "beacon.log";
    private static final int BEACON_SIZE_BYTES = 16;
    private volatile String latestBeaconValue = "No beacon generated yet.";


    @Scheduled(cron = "0 * * * * *")
   
    public void beaconGenertorAndLog() 
    {
        System.out.println("[Scheduled Task] Generating Beacon Signal ...");

        try {
            String beaconValue = generateBeaconValue();
            
            this.latestBeaconValue = beaconValue;

            String jsonLog = createJSONlog(beaconValue);

            System.out.println("Timestamp: " + DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
            System.out.println("Beacon Value: " + beaconValue);


            writeToFile(jsonLog);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
/* 
    public static void main(String[] args) {
            System.out.println("Beacon Generator");
    
    
            String beaconValue = generateBeaconValue();
            String jsonLog = createJSONlog(beaconValue);
            
            System.out.println("Timestamp: " + DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
            System.out.println("Beacon Value: " + beaconValue);
            
            writeToFile(jsonLog);

    }
*/
    
    private String generateBeaconValue() {

        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[BEACON_SIZE_BYTES];
        random.nextBytes(randomBytes);

        HexFormat hex = HexFormat.of();
        String beaconValue = hex.formatHex(randomBytes);
        
        return beaconValue;
    }

    private String createJSONlog(String beaconValue) {

        Instant timestamp = Instant.now();
        String formattedTimeStamp = DateTimeFormatter.ISO_INSTANT.format(timestamp);
        String jsonLog = String.format("{ \n \"timestamp\": \"%s\", \n \"beaconValue\": \"%s\" \n}, \n", formattedTimeStamp, beaconValue);
        return jsonLog;

    }

    private void writeToFile(String content) throws IOException {

            Files.writeString(Paths.get(FILENAME), content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("Success: Writing to log file.");

    }

    public String getLatestBeaconValue() {

        return this.latestBeaconValue;
    }

}
