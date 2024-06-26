package cm.jonabacho.serviceannulation.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import cm.jonabacho.serviceannulation.model.Coupon;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CouponRepository extends CassandraRepository<Coupon, UUID> {
    @Query("SELECT * FROM coupon WHERE idUser = ?0")
    List<Coupon> findByIdUser(UUID idUser);

    @Query("SELECT * FROM coupon WHERE idAgence = ?0")
    List<Coupon> findByIdAgence(UUID idAgence);

    @Query("SELECT * FROM coupon WHERE idUser = ?0 AND idAgence = ?1 LIMIT 1 ALLOW FILTERING")
    Optional<Coupon> findByIdUserAndIdAgence(UUID idUser, UUID idAgence);

    @Transactional
    @Query("UPDATE coupon SET dateDebut = ?1, dateFin = ?2, state = ?3, valeur = ?4, idAgence = ?5 WHERE idCoupon = ?0")
    void updateCoupon(UUID idCoupon, Instant dateDebut, Instant dateFin, String state, BigDecimal valeur, UUID idAgence);
}
