package com.flab.doorrush.domain.order.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.doorrush.domain.order.dto.request.MenuDTO;
import com.flab.doorrush.domain.order.dto.request.OrderRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  private static List<MenuDTO> orderMenus;
  private static ValidatorFactory factory;
  private static Validator validator;
  private static OrderRequest orderRequest;

  @BeforeAll
  public static void init() {
    factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();

    orderMenus = Arrays.asList(
        new MenuDTO(7L,  1),
        new MenuDTO(8L, 1),
        new MenuDTO(9L, 1),
        new MenuDTO(10L, 1),
        new MenuDTO(11L, 2)
    );

    orderRequest = OrderRequest.builder()
        .menus(orderMenus)
        .addressSeq(1L)
        .restaurantSeq(3L)
        .amount(60500L)
        .build();
  }

  @Test
  @DisplayName("주문 생성 성공 테스트 상태값 201을 반환한다.")
  public void createOrderSuccessTest() throws Exception {
    // Given
    MockHttpSession mockHttpSession = new MockHttpSession();
    mockHttpSession.setAttribute("loginId", "test6");
    String content = objectMapper.writeValueAsString(orderRequest);

   // When
    mockMvc.perform(post("/orders/")
            .session(mockHttpSession)
            .content(content)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      // Then
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.data.order.orderSeq").isNotEmpty())
      .andExpect(jsonPath("$.data.order.amount").value(60500));
}

  @Test
  @DisplayName("주문 생성 유효성 검사 성공 테스트")
  public void createOrderValidSuccessTest() {

    // When
    Set<ConstraintViolation<OrderRequest>> violations = validator.validate(orderRequest);

    // Then
    assertThat(violations).isEmpty();
  }

  @Test
  @DisplayName("주문 생성 유효성 검사 실패 테스트")
  public void createOrderValidFailTest() {
    // Given
    OrderRequest orderRequest = OrderRequest.builder()
        .menus(orderMenus)
        .restaurantSeq(3L)
        .amount(60500L)
        .build();

    // When
    Set<ConstraintViolation<OrderRequest>> violations = validator.validate(orderRequest);

    // Then
    assertThat(violations).isNotEmpty();
    violations.forEach(
        error -> {
          assertThat(error.getMessage().contains("널이어서는 안됩니다."));
        });
  }


  @Test
  @DisplayName("가격 확인 성공 테스트 상태값 200을 반환한다.")
  public void checkPriceSuccessTest() throws Exception {
    // Given
    String content = objectMapper.writeValueAsString(orderMenus);

    // When
    mockMvc.perform(get("/orders/check-price")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").isNotEmpty())
        .andExpect(jsonPath("$.data.totalPrice").value(60500))
        .andExpect(jsonPath("$.data.orderMenuCarts").isNotEmpty());
  }

}
