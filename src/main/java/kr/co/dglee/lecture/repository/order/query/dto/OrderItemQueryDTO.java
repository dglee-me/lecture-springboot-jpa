package kr.co.dglee.lecture.repository.order.query.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class OrderItemQueryDTO {

  @JsonIgnore
  private Long orderId;

  private String name;

  private int orderPrice;

  private int count;

  public OrderItemQueryDTO(Long orderId, String name, int orderPrice, int count) {
    this.orderId = orderId;
    this.name = name;
    this.orderPrice = orderPrice;
    this.count = count;
  }
}
