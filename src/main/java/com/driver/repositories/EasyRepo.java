package com.driver.repositories;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EasyRepo {

    HashMap<String,Airport> airportDb;
    HashMap<Integer,Flight> flightDb;

    HashMap<Integer, List<Integer>> bookingDb;
    HashMap<Integer,Passenger> passengerDb;





    public EasyRepo(){
        airportDb=new HashMap<>();
        flightDb=new HashMap<>();
        bookingDb=new HashMap<>();
        passengerDb=new HashMap<>();



    }
    public String addAirport(Airport airport) {
        if(!airportDb.containsKey(airport.getAirportName())){
            airportDb.put(airport.getAirportName(),airport);
            return "SUCCESS";
        }
        return "Airport Already Exists";
    }

    public String getLargestAir() {
        int noOfTerm=0;
        String nameOfAir="";
        for(Map.Entry<String,Airport> entry:airportDb.entrySet()){
           String currNameOfAir=entry.getKey();
           int currNoOfTerm = entry.getValue().getNoOfTerminals();
            if(currNoOfTerm>noOfTerm || (currNoOfTerm==noOfTerm && currNameOfAir.compareTo(nameOfAir)<0)){
                nameOfAir=currNameOfAir;
                noOfTerm=currNoOfTerm;
            }

        }
        return  nameOfAir;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double shortest=Double.MAX_VALUE;
        for(Map.Entry<Integer,Flight> entry:flightDb.entrySet()){

            Flight fNow=entry.getValue();
            City fCity=fNow.getFromCity();
            City tCity=fNow.getToCity();
            double sDuration=fNow.getDuration();

            //check if cities are matched and duration is smallest
            if(fCity.compareTo(fromCity)==0 && tCity.compareTo(toCity)==0 && shortest>sDuration){
                    shortest=sDuration;
            }
        }

        return (shortest!=Double.MAX_VALUE) ? shortest:-1;

    }

    public int getNumberOfPeopleOn(Date date, String airportName) {

        //Calculate the total number of people who have flights on that day on a particular airport
        //This includes both the people who have come for a flight and who have landed on an airport after their flight



        int peopleCount=0;

            //from airport db we will get city using airport name
        if(!airportDb.isEmpty()) {
            City city = airportDb.get(airportName).getCity();
            if (!flightDb.isEmpty()) {
                //check all flights which city matched the airport city then
                for (Flight ft : flightDb.values()) {
                    // it is from city than match the date of flight with the date
                    if (ft.getFromCity().equals(city) && date.equals(ft.getFlightDate())) {
                        peopleCount += ft.getMaxCapacity();
                    } else if (ft.getToCity().equals(city) && date.equals(new Date(ft.getFlightDate().getTime() + (long) (ft.getDuration() * 60 * 60 * 1000)))) {
                        peopleCount += ft.getMaxCapacity();
                    }
                }
            }
        }

        return peopleCount;
    }

    public int calculateFlightFare(Integer flightId) {
        //Calculation of flight prices is a function of number of people who have booked the flight already.
        //Price for any flight will be : 3000 + noOfPeopleWhoHaveAlreadyBooked*50
        //Suppose if 2 people have booked the flight already : the price of flight for the third person will be 3000 + 2*50 = 3100
        //This will not include the current person who is trying to book, he might also be just checking price

        int noOfP=1;
        if(!bookingDb.isEmpty() && bookingDb.containsKey(flightId)){
             noOfP+=bookingDb.get(flightId).size();
        }
        return noOfP*50+3000;



    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        //If the numberOfPassengers who have booked the flight is greater than : maxCapacity, in that case :
        //return a String "FAILURE"
        //Also if the passenger has already booked a flight then also return "FAILURE".
        //else if you are able to book a ticket then return "SUCCESS"


        if(  bookingDb.isEmpty() || bookingDb.get(flightId).contains(passengerId) || bookingDb.get(flightId).size()>=flightDb.get(flightId).getMaxCapacity() )
            return "FAILURE";



         if(!bookingDb.containsKey(flightId)){
             List<Integer> pas=new ArrayList<>();
             pas.add(passengerId);
             bookingDb.put(flightId,pas);
         }
         else {
             bookingDb.get(flightId).add(passengerId);
         }

        return "SUCCESS";
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        //If the passenger has not booked a ticket for that flight or the flightId is invalid or in any other failure case
        // then return a "FAILURE" message
        // Otherwise return a "SUCCESS" message
        // and also cancel the ticket that passenger had booked earlier on the given flightId

        if(bookingDb.isEmpty() || !bookingDb.containsKey(flightId) || !bookingDb.get(flightId).contains(passengerId)){
            return "FAILURE";
        }
        bookingDb.remove(flightId);
        return "SUCCESS";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        //Tell the count of flight bookings done by a passenger: This will tell the total count of flight bookings done by a passenger :
        int pBookCount=0;
        if(!bookingDb.isEmpty()) {
            for (List<Integer> pb : bookingDb.values()) {
                if (pb.contains(passengerId)) {
                    pBookCount++;
                }
            }
        }
        return pBookCount;
    }

    public String addFlight(Flight flight) {
        flightDb.put(flight.getFlightId(),flight);
        return "SUCCESS";

    }

    public String getAirportNameFromFlightId(Integer flightId) {
        //We need to get the starting airportName from where the flight will be taking off (Hint think of City variable if that can be of some use)
        //return null incase the flightId is invalid or you are not able to find the airportName

        if(!flightDb.isEmpty() && flightDb.containsKey(flightId)){
            Flight ft=flightDb.get(flightId);
            City airportCity=ft.getFromCity();
            if(!airportDb.isEmpty()) {
                for (Airport ap : airportDb.values()) {
                    if (ap.getCity().equals(airportCity)) {
                        return ap.getAirportName();
                    }
                }
            }
        }
        return null;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        //Calculate the total revenue that a flight could have
        //That is of all the passengers that have booked a flight till now and then calculate the revenue
        //Revenue will also decrease if some passenger cancels the flight

        return 0;

    }

    public String addPassenger(Passenger passenger) {
        passengerDb.put(passenger.getPassengerId(),passenger);
        return "SUCCESS";
    }
}
