package cm.jonabacho.serviceannulation.service;

import cm.jonabacho.serviceannulation.model.Coupon;
import cm.jonabacho.serviceannulation.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public Coupon saveCoupon(Coupon coupon) {
        coupon.setIdCoupon(UUID.randomUUID());
        return couponRepository.save(coupon);
    }

    public Coupon createCoupon(UUID idUser, Instant dateDebut, BigDecimal valeur, UUID idAgence) {
        Coupon coupon = new Coupon();
        coupon.setIdUser(idUser);
        // ici est defini la politique de validité du coupon
        coupon.setDateDebut(dateDebut);
        coupon.setDateFin(dateDebut.plusSeconds(259200)); // duree de 3 mois ici
        coupon.setValeur(valeur);
        coupon.setIdAgence(idAgence);
        return this.saveCoupon(coupon);
    }

    public Optional<Coupon> getCouponById(UUID id) {
        return couponRepository.findById(id);
    }

    public Iterable<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public void deleteCoupon(UUID id) {
        couponRepository.deleteById(id);
    }

    public List<Coupon> getCouponsByUserId(UUID idUser) {
        return couponRepository.findByIdUser(idUser);
    }

    public List<Coupon> getCouponsByAgenceId(UUID idAgence) {
        return couponRepository.findByIdAgence(idAgence);
    }

    public Optional<Coupon> getCouponByUserIdAndAgenceId(UUID idUser, UUID idAgence) {
        return couponRepository.findByIdUserAndIdAgence(idUser, idAgence);
    }

    public void updateCoupon(UUID idCoupon, Instant dateDebut, Instant dateFin, String state, BigDecimal valeur, UUID idAgence) {
        couponRepository.updateCoupon(idCoupon, dateDebut, dateFin, state, valeur, idAgence);
    }
}