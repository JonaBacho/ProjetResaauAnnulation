package cm.jonabacho.serviceannulation.controller;

import cm.jonabacho.serviceannulation.model.AgenceVoyage;
import cm.jonabacho.serviceannulation.model.User;
import cm.jonabacho.serviceannulation.service.AgenceVoyageService;
import cm.jonabacho.serviceannulation.service.UserService;
import cm.jonabacho.serviceannulation.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}, maxAge = 3600)
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AgenceVoyageService agenceVoyageService;

    @PostMapping("/login/user")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<User> user = userService.authenticateUser(email, password);
        if (user.isPresent()) {
            // Générer un JWT (vous devez implémenter la génération du token)
            String token = JwtUtil.generateToken(user.get().getEmail(), "Passager");

            Map<String, String > response = new HashMap<>();
            //response.put("token", token);
            response.put("idUser", user.get().getId().toString());
            System.out.println(user.get().getId());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("/login/agence")
    public ResponseEntity<Map<String, String>> loginAgence(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<AgenceVoyage> agenceVoyage = agenceVoyageService.authenticateAgence(email, password);
        if (agenceVoyage.isPresent()) {
            // Générer un JWT (vous devez implémenter la génération du token)
            String token = JwtUtil.generateToken(agenceVoyage.get().getEmail(), "Agence");

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("idAgence", agenceVoyage.get().getIdAgence().toString());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }
}

