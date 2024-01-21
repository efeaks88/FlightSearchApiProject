package org.Efaks.FlightSearchApiProject.mapper;

import org.Efaks.FlightSearchApiProject.dto.request.CreateAirportRequestDto;
import org.Efaks.FlightSearchApiProject.dto.request.UpdateAirportRequestDto;
import org.Efaks.FlightSearchApiProject.repository.entity.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAirportMapper {
    IAirportMapper INSTANCE= Mappers.getMapper(IAirportMapper.class);
    Airport toAirport(final CreateAirportRequestDto dto);
    Airport toUpdatedAirport(final UpdateAirportRequestDto dto);
}
