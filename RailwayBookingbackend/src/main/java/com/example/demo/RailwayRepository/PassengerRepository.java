package com.example.demo.RailwayRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.RailwayEntity.Railwaypassenger;

public interface PassengerRepository extends JpaRepository<Railwaypassenger, Long> {
}
