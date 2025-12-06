package org.example.rideshare.controller;

import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.model.Ride;
import org.example.rideshare.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    // USER: Create Ride --> useful to create the ride for the user
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/rides")
    public ResponseEntity<?> createRide(
            @Valid @RequestBody CreateRideRequest req,
            Authentication auth) {

        String userId = auth.getName();
        Ride ride = rideService.createRide(userId, req);
        return ResponseEntity.ok(ride);
    }

    // DRIVER: View pending ride requests --> this is to view the pending ride requests
    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/driver/rides/requests")
    public ResponseEntity<?> getPendingRequests() {
        List<Ride> rides = rideService.getPendingRequests();
        return ResponseEntity.ok(rides);
    }

    // DRIVER: Accept Ride --->
    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/driver/rides/{rideId}/accept")
    public ResponseEntity<?> acceptRide(
            @PathVariable String rideId,
            Authentication auth) {

        String driverId = auth.getName();
        Ride ride = rideService.acceptRide(rideId, driverId);
        return ResponseEntity.ok(ride);
    }

    // USER/DRIVER: Complete Ride
    @PreAuthorize("hasAnyRole('USER','DRIVER')")
    @PostMapping("/rides/{rideId}/complete")
    public ResponseEntity<?> completeRide(@PathVariable String rideId) {
        Ride ride = rideService.completeRide(rideId);
        return ResponseEntity.ok(ride);
    }

    // USER: Get user's ride history
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/rides")
    public ResponseEntity<?> getUserRides(Authentication auth) {
        String userId = auth.getName();
        List<Ride> rides = rideService.getUserRides(userId);
        return ResponseEntity.ok(rides);
    }
}
