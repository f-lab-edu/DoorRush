package com.flab.doorrush.domain.order.api;

import com.flab.doorrush.domain.order.dto.request.MenuDTO;
import com.flab.doorrush.domain.order.dto.request.OrderRequest;
import com.flab.doorrush.domain.order.dto.response.CreateOrderResponse;
import com.flab.doorrush.domain.order.dto.response.OrderMenusCartResponse;
import com.flab.doorrush.domain.order.service.OrderService;
import com.flab.doorrush.global.Response.BasicResponse;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping("check-price")
  public ResponseEntity<BasicResponse<OrderMenusCartResponse>> checkPrice(@RequestBody @Valid List<MenuDTO> menus) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(BasicResponse.success(orderService.getTotalPrice(menus)));
  }

  @PostMapping
  public ResponseEntity<BasicResponse<CreateOrderResponse>> createOrder(
      @RequestBody @Valid OrderRequest orderRequest,
      HttpSession session) {
    CreateOrderResponse response = orderService.createOrder(orderRequest,
        session.getAttribute("loginId").toString());
    return ResponseEntity.status(HttpStatus.CREATED).body(BasicResponse.success(response)
    );
  }
}
