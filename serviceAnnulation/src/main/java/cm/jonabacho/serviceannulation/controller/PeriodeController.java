package cm.jonabacho.serviceannulation.controller;

import cm.jonabacho.serviceannulation.dto.ApiStruct;
import cm.jonabacho.serviceannulation.model.Periode;
import cm.jonabacho.serviceannulation.model.User;
import cm.jonabacho.serviceannulation.repository.PeriodeRepository;
import cm.jonabacho.serviceannulation.service.PeriodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/periodes")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}, maxAge = 3600)
public class PeriodeController {

    @Autowired
    private PeriodeService periodeService;

    @PostMapping("/create")
    public ResponseEntity<ApiStruct> createPeriode(@RequestBody Map<String,String> body) {
        ApiStruct apistruct = new ApiStruct();
        //HashMap<String,Object> map = new HashMap<>();
        Periode periode = new Periode();
        periode.setDateDebut(Instant.parse(body.get("dateDebut")));
        periode.setDateFin(Instant.parse(body.get("dateDebut")));
        periode.setTauxPeriode(BigDecimal.valueOf(Double.parseDouble(body.get("tauxPeriode"))));
        periode.setIdAgence(UUID.fromString(body.get("idAgence")));

        Periode createdPeriode = periodeService.savePeriode(periode);
        apistruct.setText("Periode created successfully");
        //map.put("idPassager", createdUser.getId());
        apistruct.setValue(createdPeriode.getIdPeriode().toString());
        return new ResponseEntity<>(apistruct, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<ApiStruct> getAllPeriodes() {
        Iterable<Periode> periodes = periodeService.getAllPeriodes();
        ApiStruct apiStruct = new ApiStruct();
        apiStruct.setText("Periodes");
        apiStruct.setData(periodes);
        return new ResponseEntity<>(apiStruct, HttpStatus.OK);
    }

    @GetMapping("/{idPeriode}")
    public ResponseEntity<ApiStruct> getPeriodeById(@PathVariable UUID idPeriode) {
        Optional<Periode> periode = periodeService.getPeriodeById(idPeriode);
        ApiStruct apiStruct = new ApiStruct();
        if(periode.isEmpty()){
            apiStruct.setText("Periode not found");
            return new ResponseEntity<>(apiStruct, HttpStatus.NOT_FOUND);
        }
        apiStruct.setText("Periode found");
        apiStruct.setData(periode.get());
        return new ResponseEntity<>(apiStruct, HttpStatus.OK);
    }

    // À revoir
    @PutMapping("/{idPeriode}")
    public ResponseEntity<Periode> updatePeriode(@PathVariable UUID idPeriode, @RequestBody Periode periodeDetails) {
        Optional<Periode> optionalPeriode = periodeService.getPeriodeById(idPeriode);

        if (optionalPeriode.isPresent()) {
            Periode periode = optionalPeriode.get();
            periode.setDateDebut(periodeDetails.getDateDebut());
            periode.setTauxPeriode(periodeDetails.getTauxPeriode());

            Periode updatedPeriode = periodeService.savePeriode(periode);
            return ResponseEntity.ok(updatedPeriode);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idPeriode}")
    public ResponseEntity<Void> deletePeriode(@PathVariable UUID idPeriode) {
        periodeService.deletePeriode(idPeriode);
        return ResponseEntity.notFound().build();
    }
}
