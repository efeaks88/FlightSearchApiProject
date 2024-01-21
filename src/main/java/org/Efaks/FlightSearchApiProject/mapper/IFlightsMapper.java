package org.Efaks.FlightSearchApiProject.mapper;

import org.Efaks.FlightSearchApiProject.dto.request.CreateFlightsRequestDto;
import org.Efaks.FlightSearchApiProject.dto.request.UpdateFlightsRequestDto;
import org.Efaks.FlightSearchApiProject.repository.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IFlightsMapper {
    IFlightsMapper INSTANCE= Mappers.getMapper(IFlightsMapper.class);

    Flight toFlights(final CreateFlightsRequestDto dto);
    Flight toUpdatedFlights(final UpdateFlightsRequestDto dto);
}
