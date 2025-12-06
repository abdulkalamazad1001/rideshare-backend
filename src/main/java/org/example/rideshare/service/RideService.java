package org.example.rideshare.service;

import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.exception.BadRequestException;
import org.example.rideshare.exception.NotFoundException;
import org.example.rideshare.model.Ride;
import org.example.rideshare.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    // USER: Request a Ride
    public Ride createRide(String userId, CreateRideRequest req) {
        Ride ride = new Ride();
        ride.setUserId(userId);
        ride.setPickupLocation(req.getPickupLocation());
        ride.setDropLocation(req.getDropLocation());
        ride.setStatus("REQUESTED");

        return rideRepository.save(ride);
    }

    // DRIVER: View Requested Rides
    public List<Ride> getPendingRequests() {
        return rideRepository.findByStatus("REQUESTED");
    }

    // DRIVER: Accept Ride
    public Ride acceptRide(String rideId, String driverId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getStatus().equals("REQUESTED")) {
            throw new BadRequestException("Ride is already accepted or completed");
        }

        ride.setDriverId(driverId);
        ride.setStatus("ACCEPTED");

        return rideRepository.save(ride);
    }

    // USER or DRIVER: Complete Ride
    public Ride completeRide(String rideId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getStatus().equals("ACCEPTED")) {
            throw new BadRequestException("Ride must be ACCEPTED before completing");
        }

        ride.setStatus("COMPLETED");
        return rideRepository.save(ride);
    }

    // USER: View My Rides
    public List<Ride> getUserRides(String userId) {
        return rideRepository.findByUserId(userId);
    }
}
