package com.example.demo.RailwayDTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponseDto {
    private Long bookingId;
    private String pnr;
    private String journeyDate;
    private Double fare;
    private String coachType;
    private String trainName;
    private List<PassengerDto> passengers;
    
    // getters/setters
}


