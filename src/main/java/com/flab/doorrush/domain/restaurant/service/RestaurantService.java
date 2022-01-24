package com.flab.doorrush.domain.restaurant.service;

import com.flab.doorrush.domain.restaurant.dao.RestaurantMapper;
import com.flab.doorrush.domain.restaurant.domain.Restaurant;
import com.flab.doorrush.domain.restaurant.dto.request.AddRestaurantRequest;
import com.flab.doorrush.domain.user.dao.UserAddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

  private final UserAddressMapper userAddressMapper;
  private final RestaurantMapper restaurantMapper;


  @Transactional
  public void addRestaurant(AddRestaurantRequest addRestaurantRequest) {

    userAddressMapper.insertAddress(addRestaurantRequest.getRestaurantAddressRequest().toEntity());
    Long addressSeq = userAddressMapper.selectAddressSeq(
        addRestaurantRequest.getRestaurantAddressRequest().toEntity());

    Restaurant restaurant = addRestaurantRequest.toEntity(addressSeq);
    restaurantMapper.insertRestaurant(restaurant);
  }
}
