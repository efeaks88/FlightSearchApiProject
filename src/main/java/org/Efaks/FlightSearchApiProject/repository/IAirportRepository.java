package org.Efaks.FlightSearchApiProject.repository;

import org.Efaks.FlightSearchApiProject.repository.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAirportRepository extends JpaRepository<Airport,Long> {
    Optional<Airport> findByAirportName(String name);
}
