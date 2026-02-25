package com.example.demo.RailwayRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.RailwayEntity.Train;

public interface TrainRepository extends JpaRepository<Train, Long> {

    List<Train> findBySourceAndDestinationAndJourneyDate(
        String source,
        String destination,
        LocalDate journeyDate
    );
}
