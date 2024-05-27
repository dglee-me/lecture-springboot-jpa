package kr.co.dglee.lecture.dto;

import kr.co.dglee.lecture.domain.order.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemDTO {

  private String itemName;
  private int orderPrice;
  private int count;

  public OrderItemDTO(OrderItem i) {
    itemName = i.getItem().getName();
    orderPrice = i.getOrderPrice();
    count = i.getCount();
  }
}
