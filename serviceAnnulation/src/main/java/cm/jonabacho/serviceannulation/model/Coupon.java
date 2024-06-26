package cm.jonabacho.serviceannulation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Table
@Data
public class Coupon {
    @PrimaryKey
    private UUID idCoupon;
    private UUID idUser;
    private Instant dateDebut;
    private Instant dateFin;
    private String state; // 1 pour valide , 0 sinon
    private BigDecimal valeur;
    private UUID idAgence;

}