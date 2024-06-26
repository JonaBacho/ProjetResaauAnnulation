package cm.jonabacho.serviceannulation.repository;

import cm.jonabacho.serviceannulation.model.*;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface VoyageRepository extends CassandraRepository<Voyage, UUID> {

    @Query("SELECT * FROM voyage WHERE idAgence = ?0")
    Iterable<Voyage> findByIdAgnce(UUID idAgence);

    @Query("UPDATE Voyage SET dateDepart = ?1, lieuDepart = ?2, lieuArrive = ?3, nbrPlace = ?4, datePublication = ?5, dateLimReservation = ?6, dateLimConfirmation = ?7, nbrReservation = ?8, status = ?9, description = ?10, idAgence = ?11, idPeriode = ?12  WHERE idVoyage = ?0")
    void updateVoyage(UUID idVoyage, Instant dateDepart, String lieuDepart, String lieuArrive, int nbrPlace, Instant datePublication, Instant dateLimReservation, Instant dateLimConfirmation, int nbrReservation, int status, String description , UUID idAgence, UUID idPeriode);
}