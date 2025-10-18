package main.java.com.entropybeacon;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HexFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;



public class BeaconGenerator {

    private static final String FILENAME = "beacon.log";

    public static void main(String[] args) {
            System.out.println("Beacon Generator");
    
    
            SecureRandom random = new SecureRandom();
            
            byte[] randomBytes = new byte[32];
            random.nextBytes(randomBytes);
            
            HexFormat hex = HexFormat.of();        
            String hexValue = hex.formatHex(randomBytes);
    
            Instant timestamp = Instant.now();
    
            String logEntry = timestamp + ", " + hexValue + "\n";
    
            String formattedTimeStamp = DateTimeFormatter.ISO_INSTANT.format(timestamp);
            String jsonLog = String.format("{ \n \"timestamp\": \"%s\", \n \"beaconValue\": \"%s\" \n}, \n", formattedTimeStamp, hexValue);

        try {

            Files.writeString(Paths.get(FILENAME), jsonLog, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

            System.out.println("Success: Writing to log file.");

        } catch (IOException e) {
            System.out.println("Error: Failed to write to log file." + e.getMessage());
        }
        
        System.out.println("Timestamp: " + timestamp.toString());
        System.out.println("Beacon Value: " + hexValue);


    }
}
