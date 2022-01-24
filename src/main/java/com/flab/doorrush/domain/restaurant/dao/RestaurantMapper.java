package com.flab.doorrush.domain.restaurant.dao;

import com.flab.doorrush.domain.restaurant.domain.Restaurant;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RestaurantMapper {

  int insertRestaurant(Restaurant restaurant);

  Restaurant selectRestaurantByRestaurantSeq(Long restaurantSeq);

  Long selectRestaurantSeq(Restaurant restaurant);
}
