package com.flab.doorrush.domain.order.service;

import com.flab.doorrush.domain.order.dao.OrderMapper;
import com.flab.doorrush.domain.order.domain.Order;
import com.flab.doorrush.domain.order.domain.OrderMenu;
import com.flab.doorrush.domain.order.domain.OrderStatus;
import com.flab.doorrush.domain.order.dto.request.MenuDTO;
import com.flab.doorrush.domain.order.dto.request.OrderRequest;
import com.flab.doorrush.domain.order.dto.response.CreateOrderResponse;
import com.flab.doorrush.domain.order.dto.response.OrderHistory;
import com.flab.doorrush.domain.order.dto.response.OrderMenuCart;
import com.flab.doorrush.domain.order.dto.response.OrderMenusCartResponse;
import com.flab.doorrush.domain.order.exception.OrderException;
import com.flab.doorrush.domain.restaurant.dao.RestaurantMapper;
import com.flab.doorrush.domain.restaurant.domain.Restaurant;
import com.flab.doorrush.domain.user.dao.UserAddressMapper;
import com.flab.doorrush.domain.user.dao.UserMapper;
import com.flab.doorrush.domain.user.domain.Address;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.exception.NotExistsAddressException;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import com.flab.doorrush.global.api.KakaoAddressApi;
import com.flab.doorrush.global.dto.request.KakaoApiGetAddressRequest;
import com.flab.doorrush.global.dto.response.AddressDetail;
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
  private final UserAddressMapper userAddressMapper;
  private final RestaurantMapper restaurantMapper;
  private final KakaoAddressApi kakaoAddressApi;

  @Transactional
  public CreateOrderResponse createOrder(OrderRequest orderRequest, String loginId) {
    User user = userMapper.selectUserById(loginId)
        .orElseThrow(() -> new UserNotFoundException("회원정보가 없습니다."));

    if (orderRequest.getMenus().isEmpty()) {
      throw new OrderException("메뉴 정보를 입력해주세요.");
    }

    Restaurant restaurant = restaurantMapper.selectRestaurantBySeq(orderRequest.getRestaurantSeq());

    Order order = Order.builder()
        .userSeq(user.getUserSeq())
        .address(getOriginAddress(orderRequest.getAddressSeq()))
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

  private String getOriginAddress(Long addressSeq) {
    Address address = userAddressMapper.selectAddressBySeq(addressSeq)
        .orElseThrow(() -> new NotExistsAddressException("주소정보가 잘못되었습니다."));

    KakaoApiGetAddressRequest request = KakaoApiGetAddressRequest.builder()
        .x(address.getSpotX().toString())
        .y(address.getSpotY().toString())
        .build();
    AddressDetail addressDetail = kakaoAddressApi.getAddressBySpot(request);
    return addressDetail.getOriginAddress();
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
    List<OrderMenuCart> orderMenuCarts = new ArrayList<>();
    menus.forEach(menu -> orderMenuCarts.add(orderMapper.selectPriceByMenuDTO(menu)
        .orElseThrow(() -> new OrderException("메뉴 정보를 확인해주세요."))));

    Long totalPrice = orderMenuCarts.stream().map(OrderMenuCart::getMenuSumPrice)
        .mapToLong(a -> Math.toIntExact((Long) a)).sum();

    return OrderMenusCartResponse.builder()
        .orderMenuCarts(orderMenuCarts)
        .totalPrice(totalPrice)
        .build();
  }
}
