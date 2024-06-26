package cm.jonabacho.serviceannulation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cm.jonabacho.serviceannulation.repository.PeriodeRepository;
import cm.jonabacho.serviceannulation.model.Periode;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class PeriodeService {
    @Autowired
    private PeriodeRepository periodeRepository;

    public Periode savePeriode(Periode periode) {
        periode.setIdPeriode(UUID.randomUUID());
        return periodeRepository.save(periode);
    }

    public Optional<Periode> getPeriodeById(UUID id) {
        return periodeRepository.findById(id);
    }

    public Iterable<Periode> getAllPeriodes() {
        return periodeRepository.findAll();
    }

    public void deletePeriode(UUID id) {
        periodeRepository.deleteById(id);
    }

    public void updatePeriode(UUID idPeriode, Instant dateDebut, Instant dateFin, BigDecimal tauxPeriode){
        periodeRepository.updatePeriode(idPeriode, dateDebut, dateFin, tauxPeriode);
    }

    public Iterable<Periode> getPeriodesByIdAgence(UUID idAgence) {
        return periodeRepository.findByIdAgence(idAgence);
    }
}