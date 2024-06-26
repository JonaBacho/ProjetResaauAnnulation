package cm.jonabacho.serviceannulation.controller;

import cm.jonabacho.serviceannulation.model.AgenceVoyage;
import cm.jonabacho.serviceannulation.model.LigneVoyage;
import cm.jonabacho.serviceannulation.service.AgenceVoyageService;
import cm.jonabacho.serviceannulation.service.LigneVoyageService;
import cm.jonabacho.serviceannulation.service.TransformationService;
import cm.jonabacho.serviceannulation.service.VoyageService;
import cm.jonabacho.serviceannulation.model.Voyage;
import cm.jonabacho.serviceannulation.dto.ApiStruct;
import cm.jonabacho.serviceannulation.controller.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/voyages")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}, maxAge = 3600)
public class VoyageController {
    @Autowired
    private VoyageService voyageService;

    @Autowired
    private LigneVoyageService ligneVoyageService;

    @Autowired
    private TransformationService transformationService;

    @Autowired
    private AgenceVoyageService agenceVoyageService;

    //private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @PostMapping("/create")
    public ResponseEntity<ApiStruct> createVoyage(@RequestBody Map<String,String> body) {
        Voyage voyage = new Voyage();
        HashMap<String,Object> map = new HashMap<>();
        ApiStruct apiStruct = new ApiStruct();
        voyage.setDateDepart(Instant.parse(body.get("dateDepart")));
        voyage.setLieuDepart(body.get("lieuDepart"));
        voyage.setLieuArrive(body.get("lieuArrive"));
        voyage.setDatePublication(Instant.now());
        voyage.setDateLimConfirmation(Instant.parse(body.get("dateLimConfirmation")));
        voyage.setDateLimReservation(Instant.parse(body.get("dateLimReservation")));
        voyage.setNbrPlace(Integer.parseInt(body.get("nbrPlace")));
        voyage.setNbrReservation(Integer.parseInt(body.get("nbrReservation")));
        voyage.setIdAgence(UUID.fromString(body.get("idAgence")));
        voyage.setIdPeriode(UUID.fromString(body.get("idPeriode")));
        voyage.setDescription(body.get("description"));
        voyage.setStatus(1);

        Voyage createdVoyage = voyageService.saveVoyage(voyage);
        apiStruct.setText("Voyage created successfully");
        map.put("idVoyage", createdVoyage.getIdVoyage().toString());
        apiStruct.setData(map);

        return new ResponseEntity<>(apiStruct, HttpStatus.CREATED);
    }

    @PostMapping("/create/user")
    public ResponseEntity<ApiStruct> createVoyageForUser(@RequestBody Map<String,String> body) {
        Voyage voyage = new Voyage();
        HashMap<String,Object> map = new HashMap<>();
        LigneVoyage ligneVoyage = new LigneVoyage();
        ApiStruct apiStruct = new ApiStruct();
        voyage.setDateDepart(Instant.parse(body.get("dateDepart")));
        voyage.setLieuDepart(body.get("lieuDepart"));
        voyage.setLieuArrive(body.get("lieuArrive"));
        voyage.setDatePublication(Instant.parse(body.get("datePublication")));
        voyage.setDateLimConfirmation(Instant.parse(body.get("dateLimConfirmation")));
        voyage.setDateLimReservation(Instant.parse(body.get("dateLimReservation")));
        voyage.setNbrPlace(Integer.parseInt(body.get("nbrPlace")));
        voyage.setNbrReservation(Integer.parseInt(body.get("nbrReservation")));
        voyage.setIdAgence(UUID.fromString(body.get("idAgence")));
        voyage.setIdPeriode(UUID.fromString(body.get("idPeriode")));
        voyage.setDescription(body.get("description"));
        voyage.setStatus(1);

        Voyage createdVoyage = voyageService.saveVoyage(voyage);

        ligneVoyage.setIdVoyage(createdVoyage.getIdVoyage());
        ligneVoyage.setIdUser(UUID.fromString(body.get("idUser")));
        ligneVoyage.setClasse(body.get("classe"));
        ligneVoyage.setPrixPaye(BigDecimal.valueOf(Double.parseDouble(body.get("prix"))));
        ligneVoyage.setStatus(body.get("status"));
        ligneVoyage.setDateReservation(Instant.parse(body.get("dateReservation")));
        ligneVoyage.setDateConfirmation(Instant.parse(body.get("dateConfirmation")));

        LigneVoyage createdLigneVoyage = ligneVoyageService.saveLigneVoyage(ligneVoyage);

        apiStruct.setText("Voyage created successfully");
        map.put("idVoyage", createdVoyage.getIdVoyage().toString());
        map.put("idLigneVoyage", createdLigneVoyage.getIdVoyage().toString());
        apiStruct.setData(map);

        return new ResponseEntity<>(apiStruct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiStruct> getVoyageById(@PathVariable UUID id) {
        Optional<Voyage> voyage = voyageService.getVoyageById(id);
        ApiStruct apiStruct = new ApiStruct();
        if(voyage.isEmpty()){
            apiStruct.setText("Voyage not found in the database");
            apiStruct.setData(null);
            return new ResponseEntity<>(apiStruct, HttpStatus.NOT_FOUND);
        }

        apiStruct.setText("Voyage found in the database");
        apiStruct.setData(voyage.get());
        return new ResponseEntity<>(apiStruct, HttpStatus.FOUND);
    }

    // renvoie tous les voyages assoicés à un utilisateur
    @GetMapping("/detailsUser")
    public ResponseEntity<ApiStruct> getVoyageDetailsUser(@RequestBody Map<String,String> body) {
        ApiStruct apiStruct = new ApiStruct();
        Optional<Voyage> voyage = voyageService.getVoyageById(UUID.fromString(body.get("idVoyage")));
        Optional<LigneVoyage> ligneVoyage = ligneVoyageService.getLigneVoyageByUserIdAndVoyageId(UUID.fromString(body.get("idVoyage")), UUID.fromString(body.get("idUser")));
        if(voyage.isEmpty() || ligneVoyage.isEmpty()){
            apiStruct.setText("Voyage not found in the database");
        }
        apiStruct.setText("Voyage found in the database");
        HashMap<String, Object> mapDetails = new HashMap<>() {};
        Optional<AgenceVoyage> agenceVoyage = agenceVoyageService.getAgenceVoyageById(UUID.fromString(body.get("idVoyage")));

        mapDetails.put("nomAgence", agenceVoyage.get().getNom());
        mapDetails.put("dateDebut", voyage.get().getDateDepart());
        mapDetails.put("datePublication", voyage.get().getDatePublication());
        mapDetails.put("dateLimConfrimation", voyage.get().getDateLimConfirmation());
        mapDetails.put("lieuDepart", voyage.get().getLieuDepart());
        mapDetails.put("lieuArrive", voyage.get().getLieuArrive());
        mapDetails.put("description", voyage.get().getDescription());
        mapDetails.put("Prix", ligneVoyage.get().getPrixPaye());
        mapDetails.put("classe", ligneVoyage.get().getClasse());
        mapDetails.put("status", ligneVoyage.get().getStatus());

        apiStruct.setText("Voyage found in the database");
        apiStruct.setData(mapDetails);
        return new ResponseEntity<>(apiStruct, HttpStatus.FOUND);
    }


    // renvoie tous les voyages associés à un utilisateur
    @GetMapping("/user/{idUser}")
    public ResponseEntity<ApiStruct> getVoyageInfoByIdUser(@PathVariable UUID idUser) {
        List<LigneVoyage> ligneVoyages = ligneVoyageService.getLigneVoyageByUserId(idUser);
        List<Voyage> voyages = transformationService.transformLigneVoyageToVoyage(ligneVoyages);
        ApiStruct apiStruct = new ApiStruct();

        List<HashMap<String, Object>> mapList = new ArrayList<>();
        int n = ligneVoyages.size();
        for(int i = 0; i < n; i++){
            if(!ligneVoyages.get(i).getStatus().equals("0"))
            {
                HashMap<String, Object> mapDetails = new HashMap<>() {};
                Optional<AgenceVoyage> agenceVoyage = agenceVoyageService.getAgenceVoyageById(voyages.get(i).getIdAgence());
                if(agenceVoyage.isEmpty()){
                    mapDetails.put("nomAgence", "pas d'agence");
                }
                mapDetails.put("nomAgence", agenceVoyage.get().getNom());
                mapDetails.put("dateDebut", voyages.get(i).getDateDepart());
                mapDetails.put("Prix", ligneVoyages.get(i).getPrixPaye());
                mapDetails.put("classe", ligneVoyages.get(i).getClasse());
                mapDetails.put("status", ligneVoyages.get(i).getStatus());
                mapList.add(mapDetails);
            }
        }

        apiStruct.setText("Voyage found in the database");
        apiStruct.setData(mapList);
        return new ResponseEntity<>(apiStruct, HttpStatus.FOUND);
    }

    // renvoie tous les voyages assoicés à un utilisateur
    @GetMapping("/detailsAgence")
    public ResponseEntity<ApiStruct> getVoyageDetailsAgence(@RequestBody Map<String,String> body) {
        ApiStruct apiStruct = new ApiStruct();
        Optional<Voyage> voyage = voyageService.getVoyageById(UUID.fromString(body.get("idVoyage")));
        if(voyage.isEmpty()){
            apiStruct.setText("Voyage not found in the database");
        }
        apiStruct.setText("Voyage found in the database");
        HashMap<String, Object> mapDetails = new HashMap<>() {};
        Optional<AgenceVoyage> agenceVoyage = agenceVoyageService.getAgenceVoyageById(UUID.fromString(body.get("idVoyage")));

        mapDetails.put("nomAgence", agenceVoyage.get().getNom());
        mapDetails.put("dateDebut", voyage.get().getDateDepart());
        mapDetails.put("datePublication", voyage.get().getDatePublication());
        mapDetails.put("nbrePlace", voyage.get().getNbrPlace());
        mapDetails.put("nbreReservation", voyage.get().getNbrReservation());
        mapDetails.put("dateLimConfrimation", voyage.get().getDateLimConfirmation());
        mapDetails.put("dateLimReservation", voyage.get().getDateLimReservation());
        mapDetails.put("description", voyage.get().getDescription());
        mapDetails.put("lieuDepart", voyage.get().getLieuDepart());
        mapDetails.put("lieuArrive", voyage.get().getLieuArrive());

        apiStruct.setText("Voyage found in the database");
        apiStruct.setData(mapDetails);
        return new ResponseEntity<>(apiStruct, HttpStatus.FOUND);
    }

    // renvoie tous les voyages associés à une agence
    @GetMapping("/agence/{idAgence}")
    public ResponseEntity<ApiStruct> getVoyageInfoByIdAgence(@PathVariable UUID idAgence) {
        Iterable<Voyage> voyages = voyageService.getVoyagesByIdAgence(idAgence);
        ApiStruct apiStruct = new ApiStruct();
        List<HashMap<String, Object>> mapList = new ArrayList<>();
        for(Voyage voyage : voyages){
            if(voyage.getStatus()==1)
            {
                HashMap<String, Object> mapDetails = new HashMap<>() {};
                Optional<AgenceVoyage> agenceVoyage = agenceVoyageService.getAgenceVoyageById(voyage.getIdAgence());
                if(agenceVoyage.isEmpty()){
                    mapDetails.put("nomAgence", "pas d'agence");
                }
                mapDetails.put("nomAgence", agenceVoyage.get().getNom());
                mapDetails.put("dateDebut", voyage.getDateDepart());
                mapDetails.put("datePublication", voyage.getDatePublication());
                mapDetails.put("nbrePlace", voyage.getNbrPlace());
                mapDetails.put("nbreReservation", voyage.getNbrReservation());
                mapDetails.put("dateLimConfrimation", voyage.getDateLimConfirmation());
                mapDetails.put("dateLimReservation", voyage.getDateLimReservation());
                mapDetails.put("description", voyage.getDescription());
                mapDetails.put("lieuDepart", voyage.getLieuDepart());
                mapDetails.put("lieuArrive", voyage.getLieuArrive());
                mapList.add(mapDetails);
            }
        }

        apiStruct.setText("Voyage found in the database");
        apiStruct.setData(mapList);
        return new ResponseEntity<>(apiStruct, HttpStatus.FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<ApiStruct> getAllVoyages() {
        ApiStruct apiStruct = new ApiStruct();
        Iterable<Voyage> voyages = voyageService.getAllVoyages();
        apiStruct.setText("Voyages found in the database");
        apiStruct.setData(voyages);
        return new ResponseEntity<>(apiStruct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoyage(@PathVariable UUID id) {
        voyageService.deleteVoyage(id);
        return ResponseEntity.noContent().build();
    }
}
