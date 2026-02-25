package com.example.demo.RailwayRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.RailwayEntity.Railwayuser;

@Repository
public interface RailwayuserRepository extends JpaRepository<Railwayuser, Long> {
}
