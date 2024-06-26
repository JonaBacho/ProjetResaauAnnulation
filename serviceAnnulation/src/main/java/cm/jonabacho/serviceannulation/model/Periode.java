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
public class Periode {
    @PrimaryKey
    private UUID idPeriode;
    private Instant dateDebut;
    private Instant dateFin;
    private BigDecimal tauxPeriode;  // coefficient entre 0 et 1
    private UUID idAgence;

}