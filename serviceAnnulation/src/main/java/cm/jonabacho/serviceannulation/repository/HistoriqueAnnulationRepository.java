package cm.jonabacho.serviceannulation.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import cm.jonabacho.serviceannulation.model.HistoriqueAnnulation;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HistoriqueAnnulationRepository extends CassandraRepository<HistoriqueAnnulation, UUID> {
    @Query("SELECT * FROM historiqueannulation WHERE idUser = ?0")
    List<HistoriqueAnnulation> findByIdUser(UUID idUser);

    @Query("SELECT * FROM historiqueannulation WHERE idAgence = ?0")
    List<HistoriqueAnnulation> findByIdAgence(UUID idAgence);

    @Query("SELECT * FROM historiqueannulation WHERE idPeriode = ?0")
    List<HistoriqueAnnulation> findByIdPeriode(UUID idPeriode);

    @Query("SELECT * FROM historiqueannulation WHERE idUser = ?0 AND idAgence = ?1 ALLOW FILTERING")
    List<HistoriqueAnnulation> findByIdUserAndIdAgence(UUID idUser, UUID idAgence);

    @Query("SELECT * FROM historiqueannulation WHERE idUser = ?0 AND idPeriode = ?1 ALLOW FILTERING")
    List<HistoriqueAnnulation> findByIdUserAndIdPeriode(UUID idUser, UUID idPeriode);

    @Query("SELECT * FROM historiqueannulation WHERE idAgence = ?0 AND idPeriode = ?1 ALLOW FILTERING")
    List<HistoriqueAnnulation> findByIdAgenceAndIdPeriode(UUID idAgence, UUID idPeriode);

    @Query("SELECT * FROM historiqueannulation WHERE idUser = ?0 AND idAgence = ?1 AND idPeriode = ?2 LIMIT 1 ALLOW FILTERING")
    Optional<HistoriqueAnnulation> findByIdUserAndIdAgenceAndIdPeriode(UUID idUser, UUID idAgence, UUID idPeriode);

    @Transactional
    @Query("UPDATE HistoriqueAnnulation SET statusAnnulation = ?1, causeAnnulation = ?2, dateAnnulation = ?3, descriptionCause = ?4, tauxIndemnisation = ?5, idUser = ?6, idVoyage = ?7, idAgence = ?8, idPeriode = ?9 WHERE idH = ?0")
    void updateHistoriqueAnnulation(UUID idH, String statusAnnulation, String causeAnnulation, Instant dateAnnulation, String descriptionCause, BigDecimal tauxIndemnisation, UUID idUser, UUID idVoyage, UUID idAgence, UUID idPeriode);
}