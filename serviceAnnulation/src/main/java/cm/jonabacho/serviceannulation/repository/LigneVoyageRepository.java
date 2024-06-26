package cm.jonabacho.serviceannulation.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import cm.jonabacho.serviceannulation.model.LigneVoyage;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LigneVoyageRepository extends CassandraRepository<LigneVoyage, UUID> {

    @Query("SELECT * FROM lignevoyage WHERE idUser = ?0")
    List<LigneVoyage> findByIdUser(UUID idUser);

    @Query("SELECT * FROM lignevoyage WHERE idVoyage = ?0")
    List<LigneVoyage> findByIdVoyage(UUID idVoyage);

    @Query("SELECT * FROM lignevoyage WHERE idUser = ?0 AND idVoyage = ?1  LIMIT 1 ALLOW FILTERING")
    Optional<LigneVoyage> findByIdUserAndIdVoyage(UUID idUser, UUID idVoyage);

    @Transactional
    @Query("UPDATE lignevoyage SET status = ?1, prixPaye = ?2, classe = ?3, dateReservation = ?4, dateConfirmation = ?5 WHERE idLigne = ?0")
    void updateLigneVoyage(UUID idLigne, String status, BigDecimal prixPaye, String classe, Instant dateReservation, Instant dateConfirmation);
}

