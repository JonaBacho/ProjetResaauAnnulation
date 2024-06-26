package cm.jonabacho.serviceannulation.service;

import cm.jonabacho.serviceannulation.model.AgenceVoyage;
import cm.jonabacho.serviceannulation.model.Voyage;
import cm.jonabacho.serviceannulation.repository.AgenceVoyageRepository;
import cm.jonabacho.serviceannulation.repository.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoyageService {

    @Autowired
    private VoyageRepository voyageRepository;

    public Voyage saveVoyage(Voyage voyage) {
        voyage.setIdVoyage(UUID.randomUUID());
        return voyageRepository.save(voyage);
    }

    public Optional<Voyage> getVoyageById(UUID id) {
        return voyageRepository.findById(id);
    }

    public Iterable<Voyage> getAllVoyages() {
        return voyageRepository.findAll();
    }

    public void deleteVoyage(UUID id) {
        voyageRepository.deleteById(id);
    }

    public void updateVoyage(UUID idVoyage, Instant dateDepart, String lieuDepart, String lieuArrive, int nbrPlace, Instant datePublication, Instant dateLimReservation, Instant dateLimConfirmation, int nbrReservation, int status, String description, UUID idAgence, UUID idPeriode)
    {
        voyageRepository.updateVoyage(idVoyage, dateDepart, lieuDepart, lieuArrive, nbrPlace, datePublication, dateLimReservation, dateLimConfirmation, nbrReservation, status, description, idAgence, idPeriode);
    }

    public void updateVoyage(Voyage voyage)
    {
        voyageRepository.updateVoyage(voyage.getIdVoyage(), voyage.getDateDepart(), voyage.getLieuDepart(), voyage.getLieuArrive(), voyage.getNbrPlace(), voyage.getDatePublication(), voyage.getDateLimReservation(), voyage.getDateLimConfirmation(), voyage.getNbrReservation(), voyage.getStatus(), voyage.getDescription(), voyage.getIdAgence(), voyage.getIdPeriode());
    }

    public Iterable<Voyage> getVoyagesByIdAgence(UUID idAgence) {
        return voyageRepository.findByIdAgnce(idAgence);
    }
}
