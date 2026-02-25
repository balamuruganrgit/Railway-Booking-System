package com.example.demo.RailwayDTO;

import java.util.List;

public class BookingDTO {
    private Long bookingId;
    private String pnr;
    private String journeyDate;
    private double fare;        // ✅ Changed to primitive
    private String coachType;
    private String status;
    private String trainName;
    private String source;
    private String destination;
    private String departureTime;
    private String reachTime;
    private List<PassengerDto> passengers;

    // ✅ COMPLETE GETTERS & SETTERS - SINGLE SOURCE OF TRUTH
    
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    
    public String getPnr() { return pnr; }
    public void setPnr(String pnr) { this.pnr = pnr; }
    
    public String getJourneyDate() { return journeyDate; }
    public void setJourneyDate(String journeyDate) { this.journeyDate = journeyDate; }
    
    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }
    
    public String getCoachType() { return coachType; }
    public void setCoachType(String coachType) { this.coachType = coachType; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    
    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
    
    public String getReachTime() { return reachTime; }
    public void setReachTime(String reachTime) { this.reachTime = reachTime; }
    
    public List<PassengerDto> getPassengers() { return passengers; }
    public void setPassengers(List<PassengerDto> passengers) { this.passengers = passengers; }
}
