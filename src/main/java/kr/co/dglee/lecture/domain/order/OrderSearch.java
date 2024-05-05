package kr.co.dglee.lecture.domain.order;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrderSearch {

  private String memberName;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;
}
