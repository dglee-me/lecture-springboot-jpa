package kr.co.dglee.lecture.repository.order.query.dto;

import java.time.LocalDateTime;
import java.util.List;
import kr.co.dglee.lecture.domain.Address;
import kr.co.dglee.lecture.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderQueryDTO {

  private Long orderId;

  private String name;

  private LocalDateTime orderDate;

  private OrderStatus orderStatus;

  private Address address;

  private List<OrderItemQueryDTO> orderItems;

  public OrderQueryDTO(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
    this.orderId = orderId;
    this.name = name;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.address = address;
  }
}
