package cm.jonabacho.serviceannulation.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import cm.jonabacho.serviceannulation.model.Periode;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PeriodeRepository extends CassandraRepository<Periode, UUID> {

    @Query("SELECT * FROM Periode WHERE idAgence = ?0")
    Iterable<Periode> findByIdAgence(UUID idAgence);

    @Transactional
    @Query("UPDATE Periode SET dateDebut = ?1, dateFin = ?2, tauxPeriode = ?3 WHERE idPeriode = ?0")
    void updatePeriode(UUID idPeriode, Instant dateDebut, Instant dateFin, BigDecimal tauxPeriode);
}