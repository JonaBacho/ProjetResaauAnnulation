package cm.jonabacho.serviceannulation.service;

import cm.jonabacho.serviceannulation.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import cm.jonabacho.serviceannulation.model.User;
import cm.jonabacho.serviceannulation.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        // Hacher le mot de passe avant de le sauvegarder
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        user.setId(UUID.randomUUID());
        return userRepository.save(user);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Aucun utilisateur ne correspond à cet email"));
    }

    public Optional<User> authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && PasswordUtil.checkPassword(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    public void updateUser(UUID idUser, String username, String email, String password, String role){
        userRepository.updateUser(idUser, username, email, password, role);
    }

}
