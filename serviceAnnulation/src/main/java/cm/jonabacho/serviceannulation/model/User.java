package cm.jonabacho.serviceannulation.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.UUID;
import lombok.Data;


@Data
@Table
public class User {
    @PrimaryKey
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String role;
}
