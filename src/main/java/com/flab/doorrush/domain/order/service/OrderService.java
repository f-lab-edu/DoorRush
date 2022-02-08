package com.flab.doorrush.domain.order.service;

import com.flab.doorrush.domain.order.dao.OrderMapper;
import com.flab.doorrush.domain.order.domain.Order;
import com.flab.doorrush.domain.order.domain.OrderMenu;
import com.flab.doorrush.domain.order.domain.OrderStatus;
import com.flab.doorrush.domain.order.dto.request.MenuDTO;
import com.flab.doorrush.domain.order.dto.request.OrderRequest;
import com.flab.doorrush.domain.order.dto.response.CreateOrderResponse;
import com.flab.doorrush.domain.order.dto.response.OrderHistory;
import com.flab.doorrush.domain.order.dto.response.OrderMenuPrice;
import com.flab.doorrush.domain.order.dto.response.OrderMenusCartResponse;
import com.flab.doorrush.domain.order.exception.OrderException;
import com.flab.doorrush.domain.restaurant.dao.RestaurantMapper;
import com.flab.doorrush.domain.restaurant.domain.Restaurant;
import com.flab.doorrush.domain.user.dao.UserMapper;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import com.flab.doorrush.domain.user.service.UserAddressService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

  private final OrderMapper orderMapper;
  private final UserMapper userMapper;
  private final RestaurantMapper restaurantMapper;
  private final UserAddressService userAddressService;

  @Transactional
  public CreateOrderResponse createOrder(OrderRequest orderRequest, String loginId) {
    User user = userMapper.selectUserById(loginId)
        .orElseThrow(() -> new UserNotFoundException("회원정보가 없습니다."));

    if (orderRequest.getMenus().isEmpty()) {
      throw new OrderException("메뉴 정보를 입력해주세요.");
    }

    Restaurant restaurant = restaurantMapper.selectRestaurantByRestaurantSeq(orderRequest.getRestaurantSeq());

    Order order = Order.builder()
        .userSeq(user.getUserSeq())
        .address(userAddressService.getOriginAddress(orderRequest.getAddressSeq()).getOriginAddress())
        .restaurantSeq(orderRequest.getRestaurantSeq())
        .restaurantName(restaurant.getRestaurantName())
        .orderStatus(OrderStatus.READY)
        .amount(orderRequest.getAmount())
        .build();

    orderMapper.insertOrder(order);

    if (order.getOrderSeq() == null) {
      throw new OrderException("주문이 정상적으로 처리되지 않았습니다. 다시 시도해주세요");
    }

    addOrderMenu(orderRequest.getMenus(), order.getOrderSeq());
    return CreateOrderResponse.from(order);
  }

  private void addOrderMenu(List<MenuDTO> menus, Long orderSeq) {
    List<OrderMenu> orderMenus = new ArrayList<>();
    menus.forEach(menu -> orderMenus.add(menu.toEntity(orderSeq)));
    orderMapper.insertOrderMenu(orderMenus);
    List<OrderHistory> list = orderMapper.selectOrderBySeq(orderSeq);
    if (list.isEmpty()) {
      throw new OrderException("addOrderMenu 실행 중 오류가 발생했습니다.");
    }
  }

  public OrderMenusCartResponse getTotalPrice(List<MenuDTO> menus) {
    List<OrderMenuPrice> orderMenuCarts = new ArrayList<>();
    menus.forEach(menu -> orderMenuCarts.add(orderMapper.selectPriceByMenuDTO(menu)
        .orElseThrow(() -> new OrderException("메뉴 정보를 확인해주세요."))));

    Long totalPrice = orderMenuCarts.stream()
        .map(OrderMenuPrice::getMenuSumPrice)
        .mapToLong(Math::toIntExact)
        .sum();

    return OrderMenusCartResponse.builder()
        .orderMenuCarts(orderMenuCarts)
        .totalPrice(totalPrice)
        .build();
  }

}
