package com.flab.doorrush.domain.restaurant.service;

import com.flab.doorrush.domain.restaurant.dao.RestaurantMapper;
import com.flab.doorrush.domain.restaurant.domain.Restaurant;
import com.flab.doorrush.domain.restaurant.dto.request.AddRestaurantRequest;
import com.flab.doorrush.domain.restaurant.dto.response.AddRestaurantResponse;
import com.flab.doorrush.domain.restaurant.exception.AddRestaurantException;
import com.flab.doorrush.domain.user.dao.UserAddressMapper;
import com.flab.doorrush.domain.user.domain.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

  private final UserAddressMapper userAddressMapper;
  private final RestaurantMapper restaurantMapper;


  @Transactional
  public AddRestaurantResponse addRestaurant(AddRestaurantRequest addRestaurantRequest,
      Long ownerSeq) {
    Address address = addRestaurantRequest.getRestaurantAddressRequest().toEntity();
    try {
      userAddressMapper.insertAddress(address);
      Long addressSeq = address.getAddressSeq();
      Restaurant restaurant = addRestaurantRequest.toEntity(addressSeq, ownerSeq);
      restaurantMapper.insertRestaurant(restaurant);
      return AddRestaurantResponse.from(restaurant);

    } catch (Exception e) {
      throw new AddRestaurantException(e, "식당 insert 처리 중 예외 발생");
    }
  }

}
