package com.example.demo.RailwayRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.RailwayEntity.TrainCoach;
import com.example.demo.RailwayEntity.CoachType;
import com.example.demo.RailwayEntity.Train;

@Repository
public interface TrainCoachRepository extends JpaRepository<TrainCoach, Long> {

    Optional<TrainCoach> findByTrainAndCoachType(
    		Train train, 
    			CoachType coachType);


    
}