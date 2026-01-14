package com.acme.tickets.users.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Health check controller to verify the first application setup.
 * 
 * <p>
 * This route is intended solely for initial validation purposes, ensuring that the
 * application is correctly configured and operational. It does not perform any in-depth
 * health checks or diagnostics.
 * 
 * It may be removed in the future or reworked to return the application to a healthy
 * state.
 * 
 * </p>
 * 
 */
@RestController
public class HealthCheckController {

    /**
     * Root endpoint for health check. Returns a simple message indicating that the service is
     * operational.
     * 
     * Example response:
     * 
     * <pre>
     * {
     *  "message": "User Service is up and running"
     * }
     * </pre>
     *
     * @return ResponseEntity with a status message in JSON format.
     * 
     */
    @GetMapping("/")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("message", "User Service is up and running"));
    }
}
