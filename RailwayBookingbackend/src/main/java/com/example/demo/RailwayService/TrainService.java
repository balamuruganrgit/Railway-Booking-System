package com.example.demo.RailwayService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.RailwayEntity.Train;
import com.example.demo.RailwayRepository.TrainRepository;

@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;

    // ===============================
    // ADD TRAIN
    // ===============================
    public Train addTrain(Train train) {

        // ✅ calculate reach time only if values exist
        if (train.getDepartureTime() != null && train.getJourneyTime() != null) {
            train.setReachTime(
                calculateReachTime(
                    train.getDepartureTime(),
                    train.getJourneyTime()
                )
            );
        }

        return trainRepository.save(train);
    }

    // ===============================
    // SEARCH TRAINS (SOURCE + DEST + DATE)
    // ===============================
    public List<Train> searchTrains(
            String source,
            String destination,
            LocalDate journeyDate) {

        return trainRepository
                .findBySourceAndDestinationAndJourneyDate(
                        source,
                        destination,
                        journeyDate
                );
    }

    // ===============================
    // GET ALL TRAINS
    // ===============================
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    // ===============================
    // REACH TIME CALCULATION
    // ===============================
    private String calculateReachTime(String departure, String journey) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

        LocalTime depTime =
                LocalTime.parse(departure.trim().toUpperCase(), formatter);

        int hours = 0;
        int minutes = 0;

        // supports: "5h 45m", "5h", "45m"
        if (journey.contains("h")) {
            hours = Integer.parseInt(
                    journey.substring(0, journey.indexOf("h")).trim()
            );
        }

        if (journey.contains("m")) {
            String minPart = journey.substring(
                    journey.lastIndexOf("h") + 1
            ).replace("m", "").trim();

            if (!minPart.isEmpty()) {
                minutes = Integer.parseInt(minPart);
            }
        }

        return depTime
                .plusHours(hours)
                .plusMinutes(minutes)
                .format(formatter);
    }
}
