package com.example.demo.RailwayService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.RailwayDTO.BookingRequest;
import com.example.demo.RailwayEntity.Booking;
import com.example.demo.RailwayEntity.Railwayuser;
import com.example.demo.RailwayEntity.Train;
import com.example.demo.RailwayEntity.TrainCoach;
import com.example.demo.RailwayRepository.BookingRepository;
import com.example.demo.RailwayRepository.TrainCoachRepository;
import com.example.demo.RailwayRepository.TrainRepository;
import com.example.demo.RailwayRepository.UserRepository;
import com.example.demo.exception.ApiException;

import jakarta.transaction.Transactional;
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private TrainCoachRepository trainCoachRepository;

    @Autowired
    private UserRepository userRepository;
    @Transactional
    public Booking createBooking(BookingRequest request) {

        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new RuntimeException("Train not found"));

        Railwayuser user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TrainCoach coach = trainCoachRepository
                .findByTrainAndCoachType(train, request.getCoachType())
                .orElseThrow(() -> new RuntimeException("Coach not found"));

        int passengersCount = request.getPassengers().size();

        if (coach.getAvailableSeats() < passengersCount) {
            throw new ApiException("Not enough seats available");
        }

        coach.setAvailableSeats(coach.getAvailableSeats() - passengersCount);
        trainCoachRepository.save(coach);

        Booking booking = new Booking();
        booking.setTrain(train);
        booking.setUser(user);
        booking.setCoachType(request.getCoachType());
        booking.setJourneyDate(request.getJourneyDate());
        booking.setFare(coach.getFare() * passengersCount);
        booking.setPnr("PNR" + System.currentTimeMillis());
        booking.setStatus("PENDING");

        // ✅ VALIDATE + LINK PASSENGERS
        request.getPassengers().forEach(p -> {
            if (p.getPassengerName() == null || p.getPassengerName().trim().isEmpty()) {
                throw new ApiException("Passenger name is required");
            }
            p.setBooking(booking);
        });

        booking.setPassengers(request.getPassengers());

        return bookingRepository.save(booking);
    }

    

    public Booking getBookingByPnr(String pnr) {
        return bookingRepository.findByPnr(pnr);
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }



    public void saveAll(List<Booking> bookings) {
        bookingRepository.saveAll(bookings);
    }

}
