package com.example.demo.RailwayEntity;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private String pnr;
    private String journeyDate;
    private double fare;

    @Column(nullable = false)
    private String status;

    @Enumerated(EnumType.STRING)
    private CoachType coachType;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore 
    private Railwayuser user;

    // ✅ PERFECT RELATIONSHIP
    @OneToMany(
        mappedBy = "booking",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY  // 🔥 OPTIMIZED
    )
    @JsonManagedReference 
    private List<Railwaypassenger> passengers;

    // Constructors...



    public Booking(Long bookingId, String pnr, String journeyDate,
                   double fare, Railwayuser user, Train train) {
        this.bookingId = bookingId;
        this.pnr = pnr;
        this.journeyDate = journeyDate;
        this.fare = fare;
        this.user = user;
        this.train = train;
    }



	public Booking() {
	}



	public Object getPayment() {
		// TODO Auto-generated method stub
		return null;
	}




}
