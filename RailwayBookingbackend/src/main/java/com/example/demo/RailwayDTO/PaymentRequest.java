package com.example.demo.RailwayDTO;

public class PaymentRequest {

    private String paymentMode;
    private Double amount;
    private BookingDataDTO bookingData;

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

	public BookingDataDTO getBookingData() {
		return bookingData;
	}

	public void setBookingData(BookingDataDTO bookingData) {
		this.bookingData = bookingData;
	}
}

