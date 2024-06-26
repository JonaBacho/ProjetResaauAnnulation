package cm.jonabacho.serviceannulation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Table
@Data
public class HistoriqueAnnulation {
    @Id
    @PrimaryKey
    private UUID idH;
    private String statusAnnulation;
    private String causeAnnulation;
    private Instant dateAnnulation;
    private String descriptionCause;
    private BigDecimal tauxIndemnisation;
    private UUID idUser;
    private UUID idVoyage; // nouveau
    private UUID idAgence;
    private UUID idPeriode;
}