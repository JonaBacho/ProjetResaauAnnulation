package cm.jonabacho.serviceannulation.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Méthode pour hacher un mot de passe
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Méthode pour vérifier un mot de passe en clair par rapport à un hash
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}

