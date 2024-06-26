package cm.jonabacho.serviceannulation.repository;

import cm.jonabacho.serviceannulation.model.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import cm.jonabacho.serviceannulation.model.AgenceVoyage;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface AgenceVoyageRepository extends CassandraRepository<AgenceVoyage, UUID> {
    Optional<AgenceVoyage> findByEmail(String email);

    @Transactional
    @Query("UPDATE AgenceVoyage SET nom = ?1, localisationGPS = ?2, telephone = ?3, email = ?4, password = ?5 WHERE idAgence = ?0")
    void updateAgenceVoyage(UUID idAgence, String nom, String localisationGPS, String telephone, String email, String password);
}
