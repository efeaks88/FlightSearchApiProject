package org.Efaks.FlightSearchApiProject.service;


import org.Efaks.FlightSearchApiProject.dto.request.CreateAirportRequestDto;
import org.Efaks.FlightSearchApiProject.dto.request.UpdateAirportRequestDto;
import org.Efaks.FlightSearchApiProject.exception.EErrorType;
import org.Efaks.FlightSearchApiProject.exception.UserManagerException;
import org.Efaks.FlightSearchApiProject.mapper.IAirportMapper;
import org.Efaks.FlightSearchApiProject.repository.IAirportRepository;
import org.Efaks.FlightSearchApiProject.repository.entity.Airport;
import org.Efaks.FlightSearchApiProject.repository.enums.EStatus;
import org.Efaks.FlightSearchApiProject.utility.JwtTokenProvider;
import org.Efaks.FlightSearchApiProject.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirportService extends ServiceManager<Airport,Long> {
    private  final IAirportRepository iAirportRepository;
    private final JwtTokenProvider jwtTokenManager;

    public AirportService(IAirportRepository iAirportRepository, JwtTokenProvider jwtTokenManager) {
        super(iAirportRepository);
        this.iAirportRepository = iAirportRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public Airport createAirport(CreateAirportRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("CreateAirportRequestDto cannot be null.");
        }

        String airportName = dto.getAirportName();
        Optional<Airport> existAirportName = iAirportRepository.findByAirportName(airportName);

        if (existAirportName.isPresent()) {
            throw new UserManagerException(EErrorType.AIRPORT_NAME_USED, airportName + " is used.");
        }

        Airport airport = IAirportMapper.INSTANCE.toAirport(dto);
        save(airport);
        return airport;
    }


    public Airport updateAirport(UpdateAirportRequestDto dto) {
        Optional<Airport> existAirport = iAirportRepository.findById(dto.getId());
        if (existAirport.isEmpty() || existAirport.get().getStatus().equals(EStatus.DELETED))
            throw new UserManagerException(EErrorType.AIRPORT_NOT_FOUND);
        Airport updatedAirport = IAirportMapper.INSTANCE.toUpdatedAirport(dto);
        updatedAirport.setId(existAirport.get().getId());
        updatedAirport.setCreateDate(existAirport.get().getCreateDate());
        update(updatedAirport);
        return updatedAirport;
    }

    public Airport deleteAirport(Long id) {
        Optional<Airport> existsAirport = iAirportRepository.findById(id);
        if (existsAirport.isEmpty() || existsAirport.get().getStatus().equals(EStatus.DELETED))
            throw new UserManagerException(EErrorType.AIRPORT_NOT_FOUND);
        existsAirport.get().setStatus(EStatus.DELETED);
        update(existsAirport.get());
        return existsAirport.get();
    }
}
