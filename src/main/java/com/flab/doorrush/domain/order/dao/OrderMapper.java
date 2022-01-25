package com.flab.doorrush.domain.order.dao;

import com.flab.doorrush.domain.order.domain.Order;
import com.flab.doorrush.domain.order.domain.OrderMenu;
import com.flab.doorrush.domain.order.dto.request.MenuDTO;
import com.flab.doorrush.domain.order.dto.response.OrderHistory;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

  void insertOrder(Order order);

  void insertOrderMenu(List<OrderMenu> orderMenus);

  Optional<Long> selectTotalPriceByMenus(List<MenuDTO> menus);

  List<OrderHistory> selectOrderBySeq(Long orderSeq);

}