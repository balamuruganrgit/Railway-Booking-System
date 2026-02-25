package com.example.demo.RailwayRepository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.RailwayEntity.Booking;
import com.example.demo.RailwayEntity.Railwayuser;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking findByPnr(String pnr);
    
    @Query("SELECT b FROM Booking b JOIN FETCH b.train JOIN FETCH b.passengers p WHERE b.user = :user ORDER BY b.journeyDate DESC")
    List<Booking> findBookingHistoryWithDetails(@Param("user") Railwayuser user);
    
    @Query(value = "SELECT * FROM booking WHERE user_id = :userId ORDER BY journey_date DESC", nativeQuery = true)
    List<Booking> findByUserId(@Param("userId") Long userId);
    
    @Modifying
    @Query("DELETE FROM Railwaypayment p WHERE p.booking.pnr = :pnr")
    void deletePaymentsByPnr(@Param("pnr") String pnr);

    @Modifying
    @Query("DELETE FROM Railwaypassenger p WHERE p.booking.pnr = :pnr") 
    void deletePassengersByPnr(@Param("pnr") String pnr);

    @Modifying
    @Query("DELETE FROM Booking b WHERE b.pnr = :pnr")
    void deleteBookingByPnr(@Param("pnr") String pnr);


    // ✅ REMOVED PROBLEMATIC METHOD
}
