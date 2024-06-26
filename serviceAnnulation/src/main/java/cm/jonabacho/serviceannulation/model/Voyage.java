package cm.jonabacho.serviceannulation.model;


import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Table
@Data
public class Voyage {
    @PrimaryKey
    private UUID idVoyage;
    private Instant dateDepart;
    private String lieuDepart;
    private String lieuArrive;
    private int nbrPlace;
    private Instant datePublication;
    private Instant dateLimReservation;
    private Instant dateLimConfirmation;
    private int nbrReservation;
    private int status;  // nouveau
    private String description;  // nouveau
    private UUID idAgence;
    private UUID idPeriode;

}