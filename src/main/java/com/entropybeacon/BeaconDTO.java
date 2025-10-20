package com.entropybeacon;

import java.time.Instant;

public class BeaconDTO {
    
    private Instant timestamp;
    private String value;

    public BeaconDTO(Instant timestamp, String value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Instant getTimeStamp() {
        return timestamp;
    }

    public String getValue() {
        return value;
    }

}
