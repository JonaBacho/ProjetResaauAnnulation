package cm.jonabacho.serviceannulation.controller;

import cm.jonabacho.serviceannulation.dto.ApiStruct;
import cm.jonabacho.serviceannulation.model.Coupon;
import cm.jonabacho.serviceannulation.model.HistoriqueAnnulation;
import cm.jonabacho.serviceannulation.service.HistoriqueAnnulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/historique-annulations")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}, maxAge = 3600)
public class HistoriqueAnnulationController {

    @Autowired
    private HistoriqueAnnulationService historiqueAnnulationService;

    @GetMapping("/")
    public ResponseEntity<ApiStruct> getAllCoupons() {
        ApiStruct apiStruct = new ApiStruct();
        Iterable<HistoriqueAnnulation> historiques = historiqueAnnulationService.getAllHistoriqueAnnulations();
        apiStruct.setText("All historique annulations");
        apiStruct.setData(historiques);
        return new ResponseEntity<>(apiStruct, HttpStatus.OK);
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<HistoriqueAnnulation>> getHistoriqueAnnulationsByUserId(@PathVariable UUID idUser) {
        List<HistoriqueAnnulation> historiques = historiqueAnnulationService.getHistoriqueAnnulationsByUserId(idUser);
        return ResponseEntity.ok(historiques);
    }

    @GetMapping("/agence/{idAgence}")
    public ResponseEntity<List<HistoriqueAnnulation>> getHistoriqueAnnulationsByAgenceId(@PathVariable UUID idAgence) {
        List<HistoriqueAnnulation> historiques = historiqueAnnulationService.getHistoriqueAnnulationsByAgenceId(idAgence);
        return ResponseEntity.ok(historiques);
    }

    @GetMapping("/user/{idUser}/agence/{idAgence}")
    public ResponseEntity<ApiStruct> getHistoriqueAnnulationByUserIdAndAgenceId(@PathVariable UUID idUser, @PathVariable UUID idAgence) {
        List<HistoriqueAnnulation> historiqueAnnulation = historiqueAnnulationService.getHistoriqueAnnulationByUserIdAndAgenceId(idUser, idAgence);
        ApiStruct apiStruct = new ApiStruct();
        apiStruct.setText("Historique annulation");
        apiStruct.setData(historiqueAnnulation);
        return new ResponseEntity<>(apiStruct, HttpStatus.OK);
    }
}


