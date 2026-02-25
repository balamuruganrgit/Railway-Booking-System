package com.example.demo.RailwayEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;  // 🔥 ADD THIS IMPORT
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="railwaypassenger")
public class Railwaypassenger {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    @Column(nullable = false)
    private String passengerName;
    
    private int age;
    private String gender;
    
    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonBackReference  // 🔥 ADD THIS LINE
    private Booking booking;

    public Railwaypassenger() {
    }
   
    public Railwaypassenger(Long passengerId, String passengerName, int age, String gender, Booking booking) {
        this.passengerId = passengerId;
        this.passengerName = passengerName;
        this.age = age;
        this.gender = gender;
    }
}
