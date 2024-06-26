package cm.jonabacho.serviceannulation.controller;

import cm.jonabacho.serviceannulation.dto.ApiStruct;
import cm.jonabacho.serviceannulation.model.*;
import cm.jonabacho.serviceannulation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/agences")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}, maxAge = 3600)
public class AgenceVoyageController {

    @Autowired
    private AgenceVoyageService agenceVoyageService;

    @Autowired
    private VoyageService voyageService;

    @Autowired
    private LigneVoyageService ligneVoyageService;

    @Autowired
    private PeriodeService periodeService;

    @Autowired
    private HistoriqueAnnulationService historiqueAnnulationService;

    @Autowired
    private CouponService couponService;

    // implemetation de la logique d'annulation
    @PostMapping("/annulled")
    public ResponseEntity<ApiStruct> annulledVoyage(@RequestBody Map<String,String> body){
        ApiStruct apiStruct = new ApiStruct();
        Optional<Voyage> voyage = voyageService.getVoyageById(UUID.fromString(body.get("idVoyage")));
        //HashMap<String,Object> map = new HashMap<>();
        // recuperation de la ligne voyage associé au passager
        List<LigneVoyage> ligneVoyages = ligneVoyageService.getLigneVoyageByVoyageId(UUID.fromString(body.get("idVoyage")));
        if(voyage.isEmpty()){
            apiStruct.setText("this travel not found in the database");
            apiStruct.setData(null);
            return new ResponseEntity<>(apiStruct, HttpStatus.NOT_FOUND);
        }
        voyage.get().setStatus(0);
        voyageService.updateVoyage(voyage.get());
        // on annule les voyages au près de tous les utilisateurs concerné
        for(LigneVoyage ligneVoyage : ligneVoyages){
            if(!ligneVoyage.getStatus().equals("0")){
                ligneVoyage.setStatus("0");  // voyage annulé
                ligneVoyageService.updateLigneVoyage(ligneVoyage); // enregistrement des modifications
            }
            int nbrReservation = voyage.get().getNbrReservation();
            if(nbrReservation > 0){
                voyage.get().setNbrReservation(nbrReservation - 1);  // on decremente le nombre de reservation}
                voyageService.updateVoyage(voyage.get());
            }
            // on commence la construction des donnes lies à l'annulation
            // on construit un nouvelle objet d'historique d'annulation
            HistoriqueAnnulation historiqueAnnulation =  new HistoriqueAnnulation();
            historiqueAnnulation.setIdUser(UUID.fromString(body.get("idUser")));
            historiqueAnnulation.setIdAgence(voyage.get().getIdAgence());
            historiqueAnnulation.setIdPeriode(voyage.get().getIdPeriode());
            historiqueAnnulation.setIdVoyage(voyage.get().getIdVoyage());
            historiqueAnnulation.setCauseAnnulation("Par l'agence");
            Instant annulationDate = Instant.now();
            historiqueAnnulation.setDateAnnulation(annulationDate);
            historiqueAnnulation.setDescriptionCause(body.get("descriptionCause"));
            Optional<Periode> periode = periodeService.getPeriodeById(voyage.get().getIdPeriode());
            // calcul du taux indemnisation
            BigDecimal tauxIndemnisation = historiqueAnnulationService.calculTauxIndemnisation(annulationDate, ligneVoyage, voyage.get(), periode);
            historiqueAnnulation.setTauxIndemnisation(tauxIndemnisation);
            historiqueAnnulationService.saveHistoriqueAnnulation(historiqueAnnulation); // save

            // on construit le coupon de l'utilisateur
            Coupon coupon = couponService.createCoupon(UUID.fromString(body.get("idUser")), annulationDate, tauxIndemnisation, voyage.get().getIdAgence());
        }
        apiStruct.setText("this travel is annulled");


        return new ResponseEntity<>(apiStruct, HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<ApiStruct> createUser(@RequestBody Map<String,String> body) {
        ApiStruct apistruct = new ApiStruct();
        AgenceVoyage agenceVoyage = new AgenceVoyage();
        agenceVoyage.setEmail(body.get("email"));
        agenceVoyage.setNom(body.get("nom"));
        agenceVoyage.setTelephone(body.get("telephone"));
        agenceVoyage.setLocalisationGPS(body.get("localisationGPS"));
        agenceVoyage.setPassword(body.get("password"));

        AgenceVoyage createdAgence = agenceVoyageService.saveAgenceVoyage(agenceVoyage);
        apistruct.setText("Agence created successfully");
        apistruct.setValue(createdAgence.getIdAgence().toString());
        return new ResponseEntity<>(apistruct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiStruct> getUserById(@PathVariable UUID id) {
        Optional<AgenceVoyage> agenceVoyage = agenceVoyageService.getAgenceVoyageById(id);
        ApiStruct apiStruct = new ApiStruct();
        if(agenceVoyage.isEmpty()){
            apiStruct.setText("Agence not found in the database");
            apiStruct.setData(null);
            return new ResponseEntity<>(apiStruct, HttpStatus.NOT_FOUND);
        }

        apiStruct.setText("Agence found in the database");
        apiStruct.setData(agenceVoyage.get());
        return new ResponseEntity<>(apiStruct, HttpStatus.FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<ApiStruct> getAllAgence() {
        Iterable<AgenceVoyage> agenceVoyages = agenceVoyageService.getAllAgenceVoyages();
        ApiStruct apiStruct = new ApiStruct();
        apiStruct.setText("Agences found in the database");
        apiStruct.setData(agenceVoyages);
        return new ResponseEntity<>(apiStruct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        agenceVoyageService.deleteAgenceVoyage(id);
        return ResponseEntity.noContent().build();
    }
}
