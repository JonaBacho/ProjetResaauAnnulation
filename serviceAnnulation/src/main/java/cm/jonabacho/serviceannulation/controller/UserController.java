package cm.jonabacho.serviceannulation.controller;

import cm.jonabacho.serviceannulation.model.*;
import cm.jonabacho.serviceannulation.service.*;
import cm.jonabacho.serviceannulation.dto.ApiStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.pattern.PathPattern;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}, maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VoyageService voyageService;

    @Autowired
    private PeriodeService periodeService;

    @Autowired
    private LigneVoyageService ligneVoyageService;

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
        Optional<LigneVoyage> ligneVoyage = ligneVoyageService.getLigneVoyageByUserIdAndVoyageId(UUID.fromString(body.get("idUser")), UUID.fromString(body.get("idVoyage")));
        if(ligneVoyage.isEmpty()  || voyage.isEmpty()){
            apiStruct.setText("this travel not found in the database");
            apiStruct.setData(null);
            return new ResponseEntity<>(apiStruct, HttpStatus.NOT_FOUND);
        }
        System.out.println(ligneVoyage.get().getStatus());
        if(ligneVoyage.get().getStatus().equals("0")){
            apiStruct.setText("this travel is already annulled");
            apiStruct.setData(null);
            return new ResponseEntity<>(apiStruct, HttpStatus.ALREADY_REPORTED);
        }
        ligneVoyage.get().setStatus("0");  // voyage annulé
        ligneVoyageService.updateLigneVoyage(ligneVoyage.get()); // enregistrement des modifications
        apiStruct.setText("this travel is annulled");
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
        historiqueAnnulation.setCauseAnnulation("Par passager");
        historiqueAnnulation.setIdVoyage(voyage.get().getIdVoyage());
        Instant annulationDate = Instant.now();
        historiqueAnnulation.setDateAnnulation(annulationDate);
        historiqueAnnulation.setDescriptionCause(body.get("descriptionCause"));
        Optional<Periode> periode = periodeService.getPeriodeById(voyage.get().getIdPeriode());
        // calcul du taux indemnisation
        BigDecimal tauxIndemnisation = historiqueAnnulationService.calculTauxIndemnisation(annulationDate, ligneVoyage.get(), voyage.get(), periode);
        historiqueAnnulation.setTauxIndemnisation(tauxIndemnisation);
        historiqueAnnulationService.saveHistoriqueAnnulation(historiqueAnnulation); // save

        // on construit le coupon de l'utilisateur
        Coupon coupon = couponService.createCoupon(UUID.fromString(body.get("idUser")), annulationDate, tauxIndemnisation, voyage.get().getIdAgence());

        return new ResponseEntity<>(apiStruct, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiStruct> createUser(@RequestBody Map<String,String> body) {
        ApiStruct apistruct = new ApiStruct();
        //HashMap<String,Object> map = new HashMap<>();
        User user = new User();
        user.setEmail(body.get("email"));
        user.setUsername(body.get("username"));
        user.setPassword(body.get("password"));
        user.setRole("Passager");  // par défaut

        User createdUser = userService.saveUser(user);
        apistruct.setText("Passager created successfully");
        //map.put("idPassager", createdUser.getId());
        apistruct.setValue(createdUser.getId().toString());
        return new ResponseEntity<>(apistruct, HttpStatus.CREATED);
    }

    // utilisateur choisi un voyage (reserve un voyage)
    @PostMapping("/voyage")
    public ResponseEntity<ApiStruct> Matchvoyage(@RequestBody Map<String,String> body){
        ApiStruct apiStruct = new ApiStruct();
        Optional<Voyage> voyage = voyageService.getVoyageById(UUID.fromString(body.get("idVoyage")));
        if(voyage.isEmpty()){
            apiStruct.setText("this voyage not found in the database");
            return new ResponseEntity<>(apiStruct, HttpStatus.NOT_FOUND);
        }
        LigneVoyage ligneVoyage = new LigneVoyage();
        ligneVoyage.setIdVoyage(UUID.fromString(body.get("idVoyage")));
        ligneVoyage.setClasse(body.get("classe"));
        ligneVoyage.setPrixPaye(BigDecimal.valueOf(Double.parseDouble(body.get("prix"))));
        ligneVoyage.setIdUser(UUID.fromString(body.get("idUser")));
        ligneVoyage.setStatus("1");
        ligneVoyage.setDateConfirmation(Instant.parse(body.get("dateConfirmation")));
        ligneVoyage.setDateReservation(Instant.parse(body.get("dateReservation")));
        ligneVoyageService.saveLigneVoyage(ligneVoyage);
        apiStruct.setText("Voyage matched successfully");
        return new ResponseEntity<>(apiStruct, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiStruct> getUserById(@PathVariable UUID id) {
        Optional<User> user = userService.getUserById(id);
        ApiStruct apiStruct = new ApiStruct();
        if(user.isEmpty()){
            apiStruct.setText("passager not found in the database");
            apiStruct.setData(null);
            return new ResponseEntity<>(apiStruct, HttpStatus.NOT_FOUND);
        }

        apiStruct.setText("Passager found in the database");
        apiStruct.setData(user.get());
        return new ResponseEntity<>(apiStruct, HttpStatus.FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<ApiStruct> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        ApiStruct apiStruct = new ApiStruct();
        apiStruct.setText("Passagers");
        apiStruct.setData(users);
        return new ResponseEntity<>(apiStruct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

