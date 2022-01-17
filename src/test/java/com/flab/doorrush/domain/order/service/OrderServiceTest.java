package com.flab.doorrush.domain.order.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.doorrush.domain.order.dao.OrderMapper;
import com.flab.doorrush.domain.order.dto.request.MenuDto;
import com.flab.doorrush.domain.order.dto.request.OrderRequest;
import com.flab.doorrush.domain.order.dto.response.CreateOrderResponse;
import com.flab.doorrush.domain.order.dto.response.OrderHistory;
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

  List<MenuDto> orderMenus;

  @BeforeEach
  public void setUp() {
    orderMenus = Arrays.asList(
        new MenuDto(7L,  1),
        new MenuDto(8L, 1),
        new MenuDto(9L, 1),
        new MenuDto(10L, 1),
        new MenuDto(11L, 2)
    );
  }

  @Test
  @DisplayName("주문 생성 성공 테스트")
  public void createOrderSuccessTest() {
    // Given
    OrderRequest orderRequest = OrderRequest.builder()
        .menus(orderMenus)
        .addressSeq(10L)
        .restaurantSeq(3L)
        .amount(60500L)
        .build();

    // When
    CreateOrderResponse response = orderService.createOrder(orderRequest,"test6");

    // Then
    assertThat(response.getOrder().getOrderSeq()).isNotNull();
    List<OrderHistory> history = orderMapper.selectOrderBySeq(response.getOrder().getOrderSeq());
    assertThat(history.size()).isEqualTo(5);
  }

  @Test
  @DisplayName("총 금액 조회 성공 테스트")
  public void getTotalPrice() {
    // Given
    List<MenuDto> list = new ArrayList<>();
    list = Arrays.asList(
        (new MenuDto(1L,2)),
        (new MenuDto(2L,3)));
    // When
    Long totalPrice = orderService.getTotalPrice(list);

    // Then
    assertThat(totalPrice).isEqualTo(40500L);
  }

}
