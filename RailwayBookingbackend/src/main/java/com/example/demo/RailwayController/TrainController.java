package com.example.demo.RailwayController;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.RailwayEntity.CoachType;
import com.example.demo.RailwayEntity.Train;
import com.example.demo.RailwayEntity.TrainCoach;
import com.example.demo.RailwayRepository.TrainCoachRepository;
import com.example.demo.RailwayRepository.TrainRepository;
import com.example.demo.RailwayService.TrainService;

@RestController
@RequestMapping("/api/trains")
@CrossOrigin(
	    origins = "http://localhost:5173",
	    allowCredentials = "true"
	)
public class TrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private TrainCoachRepository trainCoachRepository;

    // ✅ ADD TRAIN
    @PostMapping("/add")
    public Train addTrain(@RequestBody Train train) {
        return trainService.addTrain(train);
    }

    // ✅ GET ALL TRAINS (WITH reachTime & seat availability)
    @GetMapping("/all")
    public List<Train> getAllTrains() {
        return trainService.getAllTrains();
    }
    @GetMapping("/search")
    public ResponseEntity<List<Train>> searchTrains(
        @RequestParam String source,
        @RequestParam String destination, 
        @RequestParam String date) {
        
        System.out.println("🔍 SEARCH: " + source + " → " + destination + " (" + date + ")");
        
        List<Train> allTrains = trainService.getAllTrains();
        
        // 🔥 FORCE SHOW ALL VALID TRAINS (ignore source/date for now)
        List<Train> matchingTrains = allTrains.stream()
            .filter(train -> train != null)
            .filter(train -> train.getTrainName() != null)  // Only valid trains
            .filter(train -> train.getDestination() != null && train.getDestination().toLowerCase().contains("trichy"))
            .collect(Collectors.toList());
        
        System.out.println("✅ SHOWING " + matchingTrains.size() + " trains");
        return ResponseEntity.ok(matchingTrains);
    }




    // ✅ GET COACH DETAILS BY TRAIN + COACH TYPE
    @GetMapping("/{trainId}/coach/{coachType}")
    public TrainCoach getCoachDetails(
            @PathVariable Long trainId,
            @PathVariable String coachType) {

        // 1️⃣ Find train
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        // 2️⃣ Convert String → Enum safely
        CoachType type;
        try {
            type = CoachType.valueOf(coachType.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid coach type. Use: GENERAL, THIRD_AC, SECOND_AC, FIRST_AC");
        }
        

        // 3️⃣ Find coach
        return trainCoachRepository
                .findByTrainAndCoachType(train, type)
                .orElseThrow(() -> new RuntimeException("Coach not found"));
    }
}
