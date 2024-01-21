package org.Efaks.FlightSearchApiProject.controller;


import lombok.RequiredArgsConstructor;
import org.Efaks.FlightSearchApiProject.dto.request.CreateAirportRequestDto;
import org.Efaks.FlightSearchApiProject.dto.request.UpdateAirportRequestDto;
import org.Efaks.FlightSearchApiProject.repository.entity.Airport;
import org.Efaks.FlightSearchApiProject.service.AirportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

;
import static org.Efaks.FlightSearchApiProject.constants.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AIRPORT)
public class AirportController {
    private final AirportService airportService;
    @PostMapping(CREATE)

    public ResponseEntity<Airport> createAirport(@RequestBody CreateAirportRequestDto dto){
        return ResponseEntity.ok(airportService.createAirport(dto));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Airport> updateAirport(@RequestBody UpdateAirportRequestDto dto){
        return ResponseEntity.ok(airportService.updateAirport(dto));
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<Airport> deleteFlights(Long id){
        return ResponseEntity.ok(airportService.deleteAirport(id));
    }

    @GetMapping(GET_ALL)
    public ResponseEntity<List<Airport>> getAll(){
        return ResponseEntity.ok(airportService.findAll());
    }
}
