package org.Efaks.FlightSearchApiProject.repository;

import org.Efaks.FlightSearchApiProject.repository.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IFlightsRepository extends JpaRepository<Flight,Long> {
    List<Flight> findByDepartureAirportAndArrivalAirportAndDepartureTime(
            String departureAirport, String arrivalAirport, LocalDateTime departureTime);

}
