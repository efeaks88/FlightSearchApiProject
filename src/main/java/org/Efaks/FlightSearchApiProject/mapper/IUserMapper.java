package org.Efaks.FlightSearchApiProject.mapper;


import org.Efaks.FlightSearchApiProject.dto.request.CreateUserRequestDto;
import org.Efaks.FlightSearchApiProject.dto.request.UpdateUserRequestDto;
import org.Efaks.FlightSearchApiProject.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE= Mappers.getMapper(IUserMapper.class);
    User toUser(final CreateUserRequestDto dto);
    User toUpdateUser(final UpdateUserRequestDto dto);
}
