package com.example.demo.RailwayEntity;




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
@Table(name="railwaypayment")
public class Railwaypayment {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long paymentId;

	    private String paymentMode;
	    private String paymentStatus;
	    private double amount;
	    
	   @ManyToOne
	    @JoinColumn(name = "booking_id")
	    private Booking booking;

	    
	    
	    
	    
	    
		public Railwaypayment(Long paymentId, String paymentMode,
				String paymentStatus, double amount, Booking booking) {
			this.paymentId = paymentId;
			this.paymentMode = paymentMode;
			this.paymentStatus = paymentStatus;
			this.amount = amount;
			this.booking =booking;
		}





		public Railwaypayment() {
			
		}
	}

