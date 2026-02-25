package com.example.demo.RailwayDTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BookingDataDTO {
    private Long trainId;
    private Long userId;
    private String coachType;
    private String journeyDate;
    private double Fare;
    private List<PassengerDto> passengers;
    // getters/setters
    public double getTotalFare() {
        // ✅ Coach-based pricing (Rockfort Express prices)
        switch (this.coachType) {
            case "FIRST_AC":  return 1800.0;  // Your S62 booking
            case "SECOND_AC": return 650.0;
            case "THIRD_AC":  return 450.0;
            case "GENERAL":   return 255.0;
            default:          return this.Fare > 0 ? this.Fare : 255.0;
        }
    }

	
}

