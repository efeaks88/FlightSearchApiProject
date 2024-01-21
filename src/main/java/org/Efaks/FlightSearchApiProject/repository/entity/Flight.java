package org.Efaks.FlightSearchApiProject.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.Efaks.FlightSearchApiProject.repository.enums.EStatus;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "flightsDb")
public class Flight extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder.Default
    private EStatus status=EStatus.ACTIVE;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime returnTime;
    private Double price;

}
