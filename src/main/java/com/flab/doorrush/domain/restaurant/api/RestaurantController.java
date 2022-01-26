package com.flab.doorrush.domain.restaurant.api;

import com.flab.doorrush.domain.restaurant.dto.request.AddRestaurantRequest;
import com.flab.doorrush.domain.restaurant.service.RestaurantService;
import com.flab.doorrush.global.Response.BasicResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

  private final RestaurantService restaurantService;

  @PostMapping("/{ownerSeq}/addRestaurant")
  public ResponseEntity<BasicResponse<Void>> addRestaurant(@PathVariable Long ownerSeq,
      @Valid @RequestBody AddRestaurantRequest addRestaurantRequest) {
    addRestaurantRequest.setOwnerSeq(ownerSeq);
    restaurantService.addRestaurant(addRestaurantRequest);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}



