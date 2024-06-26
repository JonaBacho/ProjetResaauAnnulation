package cm.jonabacho.serviceannulation.service;

import cm.jonabacho.serviceannulation.model.HistoriqueAnnulation;
import cm.jonabacho.serviceannulation.model.LigneVoyage;
import cm.jonabacho.serviceannulation.model.Periode;
import cm.jonabacho.serviceannulation.model.Voyage;
import cm.jonabacho.serviceannulation.repository.HistoriqueAnnulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HistoriqueAnnulationService {
    @Autowired
    private HistoriqueAnnulationRepository historiqueAnnulationRepository;

    public HistoriqueAnnulation saveHistoriqueAnnulation(HistoriqueAnnulation historiqueAnnulation) {
        historiqueAnnulation.setIdH(UUID.randomUUID());
        return historiqueAnnulationRepository.save(historiqueAnnulation);
    }

    public Optional<HistoriqueAnnulation> getHistoriqueAnnulationById(UUID id) {
        return historiqueAnnulationRepository.findById(id);
    }

    public Iterable<HistoriqueAnnulation> getAllHistoriqueAnnulations() {
        return historiqueAnnulationRepository.findAll();
    }

    public void deleteHistoriqueAnnulation(UUID id) {
        historiqueAnnulationRepository.deleteById(id);
    }

    public List<HistoriqueAnnulation> getHistoriqueAnnulationsByUserId(UUID idUser) {
        return historiqueAnnulationRepository.findByIdUser(idUser);
    }

    public List<HistoriqueAnnulation> getHistoriqueAnnulationsByAgenceId(UUID idAgence) {
        return historiqueAnnulationRepository.findByIdAgence(idAgence);
    }

    public List<HistoriqueAnnulation> getHistoriqueAnnulationsByPeriodeId(UUID idPeriode) {
        return historiqueAnnulationRepository.findByIdPeriode(idPeriode);
    }

    public List<HistoriqueAnnulation> getHistoriqueAnnulationByUserIdAndAgenceId(UUID idUser, UUID idAgence) {
        return historiqueAnnulationRepository.findByIdUserAndIdAgence(idUser, idAgence);
    }

    public List<HistoriqueAnnulation> getHistoriqueAnnulationByUserIdAndPeriodeId(UUID idUser, UUID idPeriode) {
        return historiqueAnnulationRepository.findByIdUserAndIdPeriode(idUser, idPeriode);
    }

    public List<HistoriqueAnnulation> getHistoriqueAnnulationByAgenceIdAndPeriodeId(UUID idAgence, UUID idPeriode) {
        return historiqueAnnulationRepository.findByIdAgenceAndIdPeriode(idAgence, idPeriode);
    }

    public Optional<HistoriqueAnnulation> getHistoriqueAnnulationByUserIdAndAgenceIdAndPeriodeId(UUID idUser, UUID idAgence, UUID idPeriode) {
        return historiqueAnnulationRepository.findByIdUserAndIdAgenceAndIdPeriode(idUser, idAgence, idPeriode);
    }

    public void updateHistoriqueAnnulation(UUID idH, String statusAnnulation, String causeAnnulation, Instant dateAnnulation, String descriptionCause, BigDecimal tauxIndemnisation, UUID idUser, UUID idVoyage, UUID idAgence, UUID idPeriode) {
        historiqueAnnulationRepository.updateHistoriqueAnnulation(idH, statusAnnulation, causeAnnulation, dateAnnulation, descriptionCause, tauxIndemnisation, idUser, idVoyage, idAgence, idPeriode);
    }

    // methode de calcul de taux d'indeminsation
    public BigDecimal calculTauxIndemnisation(Instant annulationDate, LigneVoyage ligneVoyage, Voyage voyage, Optional<Periode> periode) {
        // c'est ici que nous definissons la politique de calcul
        BigDecimal value = BigDecimal.ZERO;

        BigDecimal tauxClasse = BigDecimal.ZERO;
        switch (ligneVoyage.getClasse().toUpperCase()){
            case "C":
                tauxClasse = BigDecimal.valueOf(0.2);
                break;
            case "B":
                tauxClasse = BigDecimal.valueOf(0.6);
                break;
            case "A":
                tauxClasse = BigDecimal.valueOf(0.8);

        }

        // calcul de l'écart entre la date d'annulation et la date de depart du voyage
        int joursAvantDepart = (int)ChronoUnit.DAYS.between(annulationDate, voyage.getDateDepart());
        BigDecimal reduction = BigDecimal.ZERO;
        if(joursAvantDepart <= 2){
            reduction = BigDecimal.valueOf(20.0);
        } else if (joursAvantDepart <= 10) {
            reduction = BigDecimal.valueOf(40.0);
        } else if (joursAvantDepart <= 30) {
            reduction = BigDecimal.valueOf(60.0);
        }
        else{
            reduction = BigDecimal.valueOf(90.0);
        }

        if(periode.isEmpty()){
            value = reduction.multiply(tauxClasse);
            return value;
        }
        value = reduction.multiply(tauxClasse.multiply(periode.get().getTauxPeriode().multiply(ligneVoyage.getPrixPaye()))).divide(BigDecimal.valueOf(100));
        return value;



    }
}