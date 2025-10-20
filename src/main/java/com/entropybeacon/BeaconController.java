package com.entropybeacon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")

public class BeaconController {
    
    private final BeaconGenerator beaconService;

    @Autowired
    public BeaconController(BeaconGenerator beaconService) {
        this.beaconService = beaconService;
    }

    @GetMapping("/latest-beacon")
    public String getLatestBeacon() {
        return beaconService.getLatestBeaconValue();
    }
}
