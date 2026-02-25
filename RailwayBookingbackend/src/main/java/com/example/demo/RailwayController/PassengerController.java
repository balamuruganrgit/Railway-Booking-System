package com.example.demo.RailwayController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.RailwayEntity.Railwaypassenger;
import com.example.demo.RailwayService.PassengerService;

@RestController
@RequestMapping("/api/passengers")
@CrossOrigin(origins = "http://localhost:5173")

public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping("/add")
    public List<Railwaypassenger> addPassengers(
            @RequestBody List<Railwaypassenger> passengers) {

        return passengerService.saveAllPassengers(passengers);
    }
}
