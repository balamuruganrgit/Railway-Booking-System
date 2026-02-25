package com.example.demo.RailwayController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.RailwayDTO.BookingDTO;
import com.example.demo.RailwayDTO.BookingRequest;
import com.example.demo.RailwayDTO.PassengerDto;
import com.example.demo.RailwayEntity.Booking;
import com.example.demo.RailwayEntity.Railwaypassenger;
import com.example.demo.RailwayEntity.Railwayuser;
import com.example.demo.RailwayRepository.BookingRepository;
import com.example.demo.RailwayRepository.RailwayuserRepository;
import com.example.demo.RailwayService.BookingService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    @Autowired private BookingService bookingService;
    @Autowired private BookingRepository bookingRepository;
    
    // ✅ REMOVED unused railwayuserRepository - NO warnings!

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createBooking(@RequestBody BookingRequest request) {
        Booking booking = bookingService.createBooking(request);
        Map<String, Object> response = new HashMap<>();
        response.put("bookingId", booking.getBookingId());
        response.put("pnr", booking.getPnr());
        response.put("journeyDate", booking.getJourneyDate());
        response.put("fare", booking.getFare());
        response.put("coachType", booking.getCoachType());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<Booking> getBooking(@PathVariable String pnr) {
        Booking booking = bookingService.getBookingByPnr(pnr);
        return booking != null ? ResponseEntity.ok(booking) : ResponseEntity.notFound().build();
    }

    @Autowired private RailwayuserRepository railwayuserRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getUserBookings(@PathVariable Long userId) {
        try {
            // ✅ Get user first
            Railwayuser user = railwayuserRepository.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.ok(new ArrayList<>()); // Empty list if user not found
            }
            
            // ✅ Fetch bookings WITH train + passengers
            List<Booking> bookings = bookingRepository.findBookingHistoryWithDetails(user);
            
            System.out.println("🔍 Found " + bookings.size() + " bookings for userId=" + userId);
            
            // ✅ Convert to DTOs
            List<BookingDTO> dtos = bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
                
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            System.out.println("❌ Error fetching bookings: " + e.getMessage());
            return ResponseEntity.ok(new ArrayList<>()); // Safe fallback
        }
    }



    // ✅ SIMPLIFIED history endpoint
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<BookingDTO>> getBookingHistory(@PathVariable Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        System.out.println("🔍 /history/" + userId + " → Found " + bookings.size() + " bookings");
        
        List<BookingDTO> result = bookings.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
 // ✅ ADD THIS METHOD in BookingController
    private PassengerDto convertPassengerToDTO(Railwaypassenger passenger) {
        PassengerDto dto = new PassengerDto();
        dto.setPassengerName(passenger.getPassengerName());
        dto.setAge(passenger.getAge());
        dto.setGender(passenger.getGender());
        return dto;
    }


    // ✅ SAFE convertToDTO - No crashes
    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setPnr(booking.getPnr());
        dto.setJourneyDate(booking.getJourneyDate());
        dto.setFare(booking.getFare());
        dto.setCoachType(booking.getCoachType().name());
        dto.setStatus(booking.getStatus());
        
        // ✅ PASSENGERS - Now works!
        List<PassengerDto> passengerDtos = booking.getPassengers().stream()
            .map(this::convertPassengerToDTO)  // ✅ Method exists now
            .collect(Collectors.toList());
        dto.setPassengers(passengerDtos);
        
        // ✅ TRAIN DATA
        if (booking.getTrain() != null) {
            dto.setTrainName(booking.getTrain().getTrainName());
            dto.setSource(booking.getTrain().getSource());
            dto.setDestination(booking.getTrain().getDestination());
            dto.setDepartureTime(booking.getTrain().getDepartureTime());
            dto.setReachTime(booking.getTrain().getReachTime());
        }
        
        return dto;
    }
    @DeleteMapping("/cancel/{pnr}")
    @CrossOrigin(origins = "http://localhost:5173")
    @Transactional
    public ResponseEntity<String> cancelBooking(@PathVariable String pnr) {
        try {
            System.out.println("🔍 Cancel PNR: " + pnr);
            
            // ✅ DELETE ALL CHILD TABLES FIRST (3 tables total)
            bookingRepository.deletePaymentsByPnr(pnr);      // railwaypayment
            bookingRepository.deletePassengersByPnr(pnr);    // railwaypassenger  
            bookingRepository.deleteBookingByPnr(pnr);       // booking
            
            System.out.println("✅ FULLY DELETED: " + pnr);
            return ResponseEntity.ok("Booking cancelled successfully");
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            return ResponseEntity.status(500).body("Cancel failed");
        }
    }

    }






