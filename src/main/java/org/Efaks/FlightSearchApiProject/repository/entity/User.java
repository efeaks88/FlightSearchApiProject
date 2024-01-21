package org.Efaks.FlightSearchApiProject.repository.entity;


import javax.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.Efaks.FlightSearchApiProject.repository.enums.ERole;
import org.Efaks.FlightSearchApiProject.repository.enums.EStatus;



@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "userDb")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    @Builder.Default
    private ERole role = ERole.USER;
    @Builder.Default
    private EStatus status=EStatus.ACTIVE;
}
