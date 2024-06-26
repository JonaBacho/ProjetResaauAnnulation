package cm.jonabacho.serviceannulation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.UUID;
import lombok.Data;

@Table
@Data
public class AgenceVoyage {
    @PrimaryKey
    private UUID idAgence;
    private String nom;
    private String localisationGPS;
    private String telephone;
    private String email;
    private String password;
}
