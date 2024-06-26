package cm.jonabacho.serviceannulation.service;

import cm.jonabacho.serviceannulation.model.Coupon;
import cm.jonabacho.serviceannulation.model.LigneVoyage;
import cm.jonabacho.serviceannulation.repository.LigneVoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LigneVoyageService {
    @Autowired
    private LigneVoyageRepository ligneVoyageRepository;

    public LigneVoyage saveLigneVoyage(LigneVoyage ligneVoyage) {
        ligneVoyage.setIdLigne(UUID.randomUUID());
        return ligneVoyageRepository.save(ligneVoyage);
    }

    public Optional<LigneVoyage> getLigneVoyageById(UUID id) {
        return ligneVoyageRepository.findById(id);
    }

    public Iterable<LigneVoyage> getAllLigneVoyages() {
        return ligneVoyageRepository.findAll();
    }

    public void deleteLigneVoyage(UUID id) {
        ligneVoyageRepository.deleteById(id);
    }

    public List<LigneVoyage> getLigneVoyageByVoyageId(UUID idVoyage) {
        return ligneVoyageRepository.findByIdVoyage(idVoyage);
    }

    public List<LigneVoyage> getLigneVoyageByUserId(UUID idUser) {
        return ligneVoyageRepository.findByIdUser(idUser);
    }

    public Optional<LigneVoyage> getLigneVoyageByUserIdAndVoyageId(UUID idUser, UUID idVoyage) {
        return ligneVoyageRepository.findByIdUserAndIdVoyage(idUser, idVoyage);
    }

    public void updateLigneVoyage(UUID idLigne, String status, BigDecimal prixPaye, String classe, Instant dateReservation, Instant dateConfirmation) {
        ligneVoyageRepository.updateLigneVoyage(idLigne, status, prixPaye, classe, dateReservation, dateConfirmation);
    }

    public void updateLigneVoyage(LigneVoyage ligneVoyage) {
        ligneVoyageRepository.updateLigneVoyage(ligneVoyage.getIdLigne(), ligneVoyage.getStatus(), ligneVoyage.getPrixPaye(), ligneVoyage.getClasse(), ligneVoyage.getDateReservation(), ligneVoyage.getDateConfirmation());
    }
}