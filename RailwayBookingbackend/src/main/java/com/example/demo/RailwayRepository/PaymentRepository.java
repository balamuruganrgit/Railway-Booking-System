package com.example.demo.RailwayRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.RailwayEntity.Railwaypayment;

public interface PaymentRepository extends JpaRepository<Railwaypayment, Long> {

    Railwaypayment findByBookingBookingId(Long bookingId);
}
