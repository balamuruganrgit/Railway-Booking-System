package com.example.demo.RailwayDTO;

import java.util.List;

import com.example.demo.RailwayEntity.CoachType;
import com.example.demo.RailwayEntity.Railwaypassenger;

public class BookingRequest {

    private Long trainId;
    private Long userId;
    private List<Railwaypassenger> passengers;
//    private int age;
//    private String gender;
	private String journeyDate;
	private CoachType coachType;

    // getters & setters
    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Railwaypassenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Railwaypassenger> passengers) {
        this.passengers = passengers;
    }


//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//
    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

	public CoachType getCoachType() {
		return coachType;
	}

	public void setCoachType(CoachType coachType) {
		this.coachType = coachType;
	}
}

