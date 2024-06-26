package cm.jonabacho.serviceannulation.service;

import cm.jonabacho.serviceannulation.model.User;
import cm.jonabacho.serviceannulation.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cm.jonabacho.serviceannulation.model.AgenceVoyage;
import cm.jonabacho.serviceannulation.repository.AgenceVoyageRepository;

import java.util.Optional;
import java.util.UUID;



@Service
public class AgenceVoyageService {
    @Autowired
    private AgenceVoyageRepository agenceVoyageRepository;

    public AgenceVoyage saveAgenceVoyage(AgenceVoyage agenceVoyage) {
        // Hacher le mot de passe avant de le sauvegarder
        agenceVoyage.setPassword(PasswordUtil.hashPassword(agenceVoyage.getPassword()));
        agenceVoyage.setIdAgence(UUID.randomUUID());
        return agenceVoyageRepository.save(agenceVoyage);
    }

    public Optional<AgenceVoyage> getAgenceVoyageById(UUID id) {
        return agenceVoyageRepository.findById(id);
    }

    public Iterable<AgenceVoyage> getAllAgenceVoyages() {
        return agenceVoyageRepository.findAll();
    }

    public void deleteAgenceVoyage(UUID id) {
        agenceVoyageRepository.deleteById(id);
    }

    public AgenceVoyage findByEmail(String email) {
        return this.agenceVoyageRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Aucune Agnence ne correspond à cet email"));
    }

    public Optional<AgenceVoyage> authenticateAgence(String email, String password) {
        Optional<AgenceVoyage> agenceVoyage = agenceVoyageRepository.findByEmail(email);
        if (agenceVoyage.isPresent() && PasswordUtil.checkPassword(password, agenceVoyage.get().getPassword())) {
            return agenceVoyage;
        }
        return Optional.empty();
    }

    public void updateAgenceVoyage(UUID idAgence, String nom, String localisationGPS, String telephone, String email, String password){
        agenceVoyageRepository.updateAgenceVoyage(idAgence, nom, localisationGPS, telephone, email, password);
    }
}

