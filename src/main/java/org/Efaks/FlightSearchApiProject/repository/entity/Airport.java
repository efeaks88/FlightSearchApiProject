package org.Efaks.FlightSearchApiProject.repository.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;

import org.Efaks.FlightSearchApiProject.repository.enums.EStatus;

import javax.persistence.Entity;
import javax.persistence.*;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "airportDb")
public class Airport extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder.Default
    private EStatus status=EStatus.ACTIVE;
    private String city;
    private String airportName;
}
