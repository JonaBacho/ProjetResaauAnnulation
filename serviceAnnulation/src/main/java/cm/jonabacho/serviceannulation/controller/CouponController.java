package cm.jonabacho.serviceannulation.controller;

import cm.jonabacho.serviceannulation.dto.ApiStruct;
import cm.jonabacho.serviceannulation.model.Coupon;
import cm.jonabacho.serviceannulation.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}, maxAge = 3600)
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/")
    public ResponseEntity<ApiStruct> getAllCoupons() {
        ApiStruct apiStruct = new ApiStruct();
        Iterable<Coupon> coupons = couponService.getAllCoupons();
        apiStruct.setText("All coupons");
        apiStruct.setData(coupons);
        return new ResponseEntity<>(apiStruct, HttpStatus.OK);
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<ApiStruct> getCouponsByUserId(@PathVariable UUID idUser) {
        ApiStruct apiStruct = new ApiStruct();
        List<Coupon> coupons = couponService.getCouponsByUserId(idUser);
        apiStruct.setText("Coupons of the user");
        apiStruct.setData(coupons);
        return new ResponseEntity<>(apiStruct, HttpStatus.OK);
    }

    @GetMapping("/agence/{idAgence}")
    public ResponseEntity<ApiStruct> getCouponsByAgenceId(@PathVariable UUID idAgence) {
        List<Coupon> coupons = couponService.getCouponsByAgenceId(idAgence);
        ApiStruct apiStruct = new ApiStruct();
        apiStruct.setText("Coupons of the agence");
        apiStruct.setData(coupons);
        return new ResponseEntity<>(apiStruct, HttpStatus.OK);
    }

    @GetMapping("/user/{idUser}/agence/{idAgence}")
    public ResponseEntity<ApiStruct> getCouponByUserIdAndAgenceId(@PathVariable UUID idUser, @PathVariable UUID idAgence) {
        ApiStruct apiStruct = new ApiStruct();
        Optional<Coupon> coupon = couponService.getCouponByUserIdAndAgenceId(idUser, idAgence);
        if(coupon.isEmpty()) {
            apiStruct.setText("Coupon Not Found");
            return new ResponseEntity<>(apiStruct, HttpStatus.NOT_FOUND);
        }
        apiStruct.setText("Coupon Found");
        apiStruct.setData(coupon);
        return new ResponseEntity<>(apiStruct, HttpStatus.OK);
    }
}
