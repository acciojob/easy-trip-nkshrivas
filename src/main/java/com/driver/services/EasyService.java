package com.driver.services;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repositories.EasyRepo;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EasyService {
    EasyRepo er=new EasyRepo();
    public String addAirport(Airport airport) {
        return er.addAirport(airport);
    }

    public String getLargest() {
        return er.getLargestAir();
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        return er.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        return er.getNumberOfPeopleOn(date,airportName);
    }

    public int calculateFlightFare(Integer flightId) {
        return er.calculateFlightFare(flightId);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        return er.bookATicket(flightId,passengerId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        return er.cancelATicket(flightId,passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return er.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public String addFlight(Flight flight) {
        return er.addFlight(flight);
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        return er.getAirportNameFromFlightId(flightId);
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        return er.calculateRevenueOfAFlight(flightId);
    }

    public String addPassenger(Passenger passenger) {
        return er.addPassenger(passenger);
    }
}
