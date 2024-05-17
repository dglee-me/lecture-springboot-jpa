package kr.co.dglee.lecture.dto;

import java.time.LocalDateTime;
import kr.co.dglee.lecture.domain.Address;
import kr.co.dglee.lecture.domain.order.OrderStatus;
import lombok.Getter;

@Getter
public class OrderSimpleQueryDTO {

  private Long orderId;

  private String name;

  private LocalDateTime orderDate;

  private OrderStatus orderStatus;

  private Address address;

  public OrderSimpleQueryDTO(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
    this.orderId = orderId;
    this.name = name;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.address = address;
  }
}