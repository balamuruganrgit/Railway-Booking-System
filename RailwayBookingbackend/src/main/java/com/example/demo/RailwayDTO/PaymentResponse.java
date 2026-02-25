package com.example.demo.RailwayDTO;

import lombok.Getter;

@Getter
public class PaymentResponse {
    private Long paymentId;
    private String paymentStatus;
    private String paymentMode;
    private double amount;
    private Long bookingId;

    public PaymentResponse(Long paymentId, String paymentStatus,
                           String paymentMode, double amount, Long bookingId) {
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
        this.paymentMode = paymentMode;
        this.amount = amount;
        this.bookingId = bookingId;
    }

    // getters
}

