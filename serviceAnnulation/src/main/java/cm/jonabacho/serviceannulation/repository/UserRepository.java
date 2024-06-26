package cm.jonabacho.serviceannulation.repository;

import cm.jonabacho.serviceannulation.model.User;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.time.LocalDate;
import java.util.UUID;

import java.util.Optional;

public interface UserRepository extends CassandraRepository<User, UUID> {
    @Query("SELECT * FROM User where username=?0")
    Optional<User> findByUsername(String username);

    @Query("SELECT * FROM User where email=?0")
    Optional<User> findByEmail(String email);

    @Query("UPDATE User SET username = ?1, email = ?2, password = ?3, role = ?4 WHERE idUser = ?0")
    void updateUser(UUID idUser, String username, String email, String password, String role);
}
