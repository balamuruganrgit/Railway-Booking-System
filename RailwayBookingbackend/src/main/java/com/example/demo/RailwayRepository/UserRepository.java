package com.example.demo.RailwayRepository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.RailwayEntity.Railwayuser;

public interface UserRepository extends JpaRepository<Railwayuser, Long> {
	 Optional<Railwayuser> findByEmail(String email);
	  boolean existsByEmail(String email);
	    boolean existsByMobile(String mobile);
}
