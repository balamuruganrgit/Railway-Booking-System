package com.example.demo.RailwayService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.RailwayEntity.Railwaypassenger;
import com.example.demo.RailwayRepository.PassengerRepository;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public List<Railwaypassenger> saveAllPassengers(List<Railwaypassenger> passengers) {
        return passengerRepository.saveAll(passengers);
    }
}
