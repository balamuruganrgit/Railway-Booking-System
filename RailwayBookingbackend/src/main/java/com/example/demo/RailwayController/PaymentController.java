package com.example.demo.RailwayController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.RailwayDTO.BookingDataDTO;
import com.example.demo.RailwayDTO.PaymentRequest;
import com.example.demo.RailwayEntity.Booking;
import com.example.demo.RailwayEntity.CoachType;
import com.example.demo.RailwayEntity.Railwaypassenger;
import com.example.demo.RailwayEntity.Railwaypayment;
import com.example.demo.RailwayEntity.Railwayuser;
import com.example.demo.RailwayEntity.Train;
import com.example.demo.RailwayRepository.BookingRepository;
import com.example.demo.RailwayRepository.PaymentRepository;
import com.example.demo.RailwayRepository.RailwayuserRepository;
import com.example.demo.RailwayRepository.TrainRepository;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private RailwayuserRepository userRepository;

    // ✅ ONLY THIS METHOD - Perfect Payment Flow!
    @PostMapping("/confirm-payment")
    public ResponseEntity<Map<String, Object>> confirmPayment(@RequestBody PaymentRequest req) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            BookingDataDTO data = req.getBookingData();
            
            Railwaypayment payment = new Railwaypayment();
            payment.setPaymentStatus("SUCCESS");
            payment.setPaymentMode(req.getPaymentMode());
            payment.setAmount(req.getAmount());
            paymentRepository.save(payment);  // ✅ Used directly!

            // 2️⃣ Find Train & User
            Train train = trainRepository.findById(data.getTrainId())
                .orElseThrow(() -> new RuntimeException("Train not found"));
            Railwayuser user = userRepository.findById(data.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

            // 3️⃣ Create Booking + Passengers
            Booking booking = new Booking();
            booking.setPnr("PNR" + System.currentTimeMillis());
            booking.setJourneyDate(data.getJourneyDate());
            booking.setFare(data.getTotalFare());
            booking.setStatus("CONFIRMED");
            booking.setCoachType(CoachType.valueOf(data.getCoachType()));
            booking.setTrain(train);
            booking.setUser(user);

            List<Railwaypassenger> passengerList = data.getPassengers().stream()
                .map(p -> {
                    Railwaypassenger pass = new Railwaypassenger();
                    pass.setBooking(booking);
                    pass.setPassengerName(p.getPassengerName());
                    pass.setAge(p.getAge());
                    pass.setGender(p.getGender());
                    return pass;
                })
                .collect(Collectors.toList());

            booking.setPassengers(passengerList);
            Booking savedBooking = bookingRepository.save(booking);

            response.put("success", true);
            response.put("pnr", booking.getPnr());
            response.put("bookingId", savedBooking.getBookingId());
            response.put("message", "Booking confirmed successfully!");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
