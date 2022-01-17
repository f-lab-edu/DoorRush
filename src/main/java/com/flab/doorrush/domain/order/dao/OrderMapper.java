package com.flab.doorrush.domain.order.dao;

import com.flab.doorrush.domain.order.domain.Order;
import com.flab.doorrush.domain.order.domain.OrderMenu;
import com.flab.doorrush.domain.order.dto.request.MenuDto;
import com.flab.doorrush.domain.order.dto.response.OrderHistory;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

  void insertOrder(Order order);

  void insertOrderMenu(List<OrderMenu> orderMenus);

  Long selectTotalPrice(List<MenuDto> menus);

  List<OrderHistory> selectOrderBySeq(Long orderSeq);

}