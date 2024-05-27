package kr.co.dglee.lecture.dto;

import java.time.LocalDateTime;
import java.util.List;
import kr.co.dglee.lecture.domain.Address;
import kr.co.dglee.lecture.domain.order.Order;
import kr.co.dglee.lecture.domain.order.OrderStatus;
import lombok.Getter;

@Getter
public class OrderDTO {

  private Long orderId;

  private String name;

  private LocalDateTime orderDate;

  private OrderStatus orderStatus;

  private Address address;

  private List<OrderItemDTO> orderItems;

  public OrderDTO(Order o) {
    orderId = o.getId();
    name = o.getMember().getName();
    orderDate = o.getOrderDate();
    orderStatus = o.getStatus();
    address = o.getDelivery().getAddress();
    orderItems = o.getOrderItems().stream()
        .map(i -> new OrderItemDTO(i))
        .toList();
  }
}
