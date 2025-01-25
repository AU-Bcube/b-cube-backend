package com.b_cube.website.global.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthyController {

    @GetMapping("/api/healthy")
    public ResponseEntity<String> checkHealth() {
        return new ResponseEntity<>("서버 정상", HttpStatus.OK);
    }
}