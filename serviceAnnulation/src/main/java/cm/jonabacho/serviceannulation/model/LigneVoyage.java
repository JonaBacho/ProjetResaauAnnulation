package cm.jonabacho.serviceannulation.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Table
@Data
public class LigneVoyage {
    @PrimaryKey
    private UUID idLigne;
    private String status;  // etat du voyage (0, 1, 2)
    private BigDecimal prixPaye;
    private String classe;
    private Instant dateReservation;
    private Instant dateConfirmation;
    private UUID idVoyage;
    private UUID idUser;

}