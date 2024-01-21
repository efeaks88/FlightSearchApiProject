package org.Efaks.FlightSearchApiProject.controller;


import lombok.RequiredArgsConstructor;
import org.Efaks.FlightSearchApiProject.dto.request.CreateFlightsRequestDto;
import org.Efaks.FlightSearchApiProject.dto.request.UpdateFlightsRequestDto;
import org.Efaks.FlightSearchApiProject.repository.entity.Flight;
import org.Efaks.FlightSearchApiProject.service.FlightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


import static org.Efaks.FlightSearchApiProject.constants.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(FLIGHTS)
public class FlightsController {
    private final FlightService flightsService;

    @PostMapping(CREATE)
    public ResponseEntity<Flight> createFlights(@RequestBody CreateFlightsRequestDto dto) {
        return ResponseEntity.ok(flightsService.createFlights(dto));

    }

    @PutMapping(UPDATE)
    public ResponseEntity<Flight> updateFlights(@RequestBody UpdateFlightsRequestDto dto) {
        return ResponseEntity.ok(flightsService.updateFlights(dto));
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<Flight> deleteFlights(Long id) {
        return ResponseEntity.ok(flightsService.deleteFlights(id));
    }

    @GetMapping(GET_ALL)
    public ResponseEntity<List<Flight>> getAll() {
        return ResponseEntity.ok(flightsService.findAll());
    }

    @GetMapping(FINDFLIGHTS)
    public List<Flight> findFlights(@RequestParam String departureAirport,
                                    @RequestParam String arrivalAirport,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime returnTime) {
        return flightsService.findFlights(departureAirport, arrivalAirport, departureTime, returnTime);
    }

}