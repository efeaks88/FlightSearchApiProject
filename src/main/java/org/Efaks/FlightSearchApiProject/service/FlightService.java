package org.Efaks.FlightSearchApiProject.service;


import org.Efaks.FlightSearchApiProject.dto.request.CreateFlightsRequestDto;
import org.Efaks.FlightSearchApiProject.dto.request.UpdateFlightsRequestDto;
import org.Efaks.FlightSearchApiProject.exception.EErrorType;
import org.Efaks.FlightSearchApiProject.exception.UserManagerException;
import org.Efaks.FlightSearchApiProject.mapper.IFlightsMapper;
import org.Efaks.FlightSearchApiProject.repository.IFlightsRepository;
import org.Efaks.FlightSearchApiProject.repository.entity.Flight;
import org.Efaks.FlightSearchApiProject.repository.enums.EStatus;
import org.Efaks.FlightSearchApiProject.utility.ServiceManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FlightService extends ServiceManager<Flight,Long> {
    private final IFlightsRepository iFlightsRepository;

    public FlightService(IFlightsRepository iFlightsRepository) {
        super(iFlightsRepository);
        this.iFlightsRepository = iFlightsRepository;
    }

    public Flight createFlights(CreateFlightsRequestDto dto) {
        Flight flight = IFlightsMapper.INSTANCE.toFlights(dto);
        save(flight);
        return flight;
    }

    public Flight updateFlights(UpdateFlightsRequestDto dto) {
        Optional<Flight> existFlights = iFlightsRepository.findById(dto.getId());
        if (existFlights.isEmpty() || existFlights.get().getStatus().equals(EStatus.DELETED))
            throw new UserManagerException(EErrorType.FLIGHTS_NOT_FOUND);
        Flight updatedFlights = IFlightsMapper.INSTANCE.toUpdatedFlights(dto);
        updatedFlights.setId(existFlights.get().getId());
        updatedFlights.setCreateDate(existFlights.get().getCreateDate());
        update(updatedFlights);
        return updatedFlights;
    }
    public Flight deleteFlights(Long id) {
        Optional<Flight> existsFlight = iFlightsRepository.findById(id);
        if (existsFlight.isEmpty() || existsFlight.get().getStatus().equals(EStatus.DELETED))
            throw new UserManagerException(EErrorType.FLIGHTS_NOT_FOUND);
        existsFlight.get().setStatus(EStatus.DELETED);
        update(existsFlight.get());
        return existsFlight.get();
    }
    public List<Flight> findFlights(String departureAirport, String arrivalAirport,
                                    LocalDateTime departureTime, LocalDateTime returnTime) {
        List<Flight> departureFlights = iFlightsRepository.findByDepartureAirportAndArrivalAirportAndDepartureTime(
                departureAirport, arrivalAirport, departureTime);
        Stream<Flight> returnFlightsStream = returnTime == null ?
                Stream.empty() :
                iFlightsRepository.findByDepartureAirportAndArrivalAirportAndDepartureTime(
                        arrivalAirport, departureAirport, returnTime).stream();

        return Stream.concat(departureFlights.stream(), returnFlightsStream)
                .collect(Collectors.toList());
    }
    @Scheduled(cron = "0 0 1 * * ?")
    public void fetchAndSaveMockFlights() {
        List<Flight> mockFlights = getMockFlights();
        mockFlights.forEach(this::save);
    }

    private List<Flight> getMockFlights() {
        return List.of(
                Flight.builder()
                        .departureAirport("IST")
                        .arrivalAirport("JFK")
                        .departureTime(LocalDateTime.of(2024, 1, 1, 10, 0))
                        .returnTime(LocalDateTime.of(2024, 1, 1, 14, 0))
                        .price(500.0)
                        .status(EStatus.ACTIVE)
                        .build(),
                Flight.builder()
                        .departureAirport("LAX")
                        .arrivalAirport("LHR")
                        .departureTime(LocalDateTime.of(2024, 1, 2, 11, 0))
                        .returnTime(LocalDateTime.of(2024, 1, 2, 15, 0))
                        .price(600.0)
                        .status(EStatus.ACTIVE)
                        .build()
        );
    }
}
