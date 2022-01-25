package com.flab.doorrush.domain.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.flab.doorrush.domain.order.dao.OrderMapper;
import com.flab.doorrush.domain.order.dto.request.MenuDTO;
import com.flab.doorrush.domain.order.dto.request.OrderRequest;
import com.flab.doorrush.domain.order.dto.response.CreateOrderResponse;
import com.flab.doorrush.domain.order.dto.response.OrderHistory;
import com.flab.doorrush.domain.order.exception.OrderException;
import com.flab.doorrush.domain.user.exception.NotExistsAddressException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

  @Autowired
  OrderService orderService;

  @Autowired
  OrderMapper orderMapper;

  List<MenuDTO> orderMenus;
  OrderRequest orderRequest;

  @BeforeEach
  public void setUp() {
    // Given
    orderMenus = Arrays.asList(
        new MenuDTO(7L, 1),
        new MenuDTO(8L, 1),
        new MenuDTO(9L, 1),
        new MenuDTO(10L, 1),
        new MenuDTO(11L, 2));

    orderRequest = OrderRequest.builder()
        .menus(orderMenus)
        .addressSeq(1L)
        .restaurantSeq(3L)
        .amount(60500L)
        .build();
  }

  @Test
  @DisplayName("주문 생성 성공 테스트")
  public void createOrderSuccessTest() {

    // When
    CreateOrderResponse response = orderService.createOrder(orderRequest, "test6");

    // Then
    assertThat(response.getOrder().getOrderSeq()).isNotNull();
    List<OrderHistory> history = orderMapper.selectOrderBySeq(response.getOrder().getOrderSeq());
    assertThat(history.size()).isEqualTo(5);
  }

  @Test
  @DisplayName("메뉴정보가 비어있을 경우 OrderException 예외 발생")
  public void createOrderFailTest() {
    List<MenuDTO> list = new ArrayList<>();
    //Given
    OrderRequest orderRequest = OrderRequest.builder()
        .menus(list)
        .addressSeq(10L)
        .restaurantSeq(3L)
        .amount(60500L)
        .build();

    // Then
    assertThrows(OrderException.class,
        // When
        () -> orderService.createOrder(orderRequest, "test6"));
  }

  @Test
  @DisplayName("주소지 조회 실패 시 NotExistsAddressException 예외 발생")
  public void createOrderAddressFailTest() {
    //Given
    OrderRequest orderRequest = OrderRequest.builder()
        .menus(orderMenus)
        .addressSeq(15L)
        .restaurantSeq(3L)
        .amount(60500L)
        .build();

    // Then
    assertThrows(NotExistsAddressException.class,
        // When
        () -> orderService.createOrder(orderRequest, "test6"));
  }

  @Test
  @DisplayName("총 금액 조회 성공 테스트")
  public void getTotalPrice() {
    // Given
    List<MenuDTO> list = new ArrayList<>();
    list = Arrays.asList(
        (new MenuDTO(1L, 2)),
        (new MenuDTO(2L, 3)));
    // When
    Long totalPrice = orderService.getTotalPrice(list);

    // Then
    assertThat(totalPrice).isEqualTo(40500L);
  }
}
