package com.flab.doorrush.domain.order.dao;

import com.flab.doorrush.domain.order.domain.Order;
import com.flab.doorrush.domain.order.domain.OrderMenu;
import com.flab.doorrush.domain.order.dto.request.Menu;
import com.flab.doorrush.domain.order.dto.response.OrderHistory;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

  void insertOrder(Order order);

  void insertOrderMenu(List<OrderMenu> orderMenus);

  Long selectTotalPrice(List<Menu> menus);

  List<OrderHistory> selectOrderBySeq(Long orderSeq);

}