package cm.jonabacho.serviceannulation.service;

import cm.jonabacho.serviceannulation.model.LigneVoyage;
import cm.jonabacho.serviceannulation.model.Voyage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransformationService {

    @Autowired
    private LigneVoyageService ligneVoyageService;

    @Autowired
    private VoyageService voyageService;

    public List<Voyage> transformLigneVoyageToVoyage(List<LigneVoyage> ligneVoyages) {
        // Extraire les IDs de Voyage
        Set<UUID> voyageIds = ligneVoyages.stream()
                .map(LigneVoyage::getIdVoyage)
                .collect(Collectors.toSet());

        // Récupérer les objets Voyage correspondant aux IDs
        return voyageIds.stream()
                .map(voyageService::getVoyageById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}