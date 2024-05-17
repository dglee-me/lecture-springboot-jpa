package kr.co.dglee.lecture.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.dglee.lecture.domain.Address;
import kr.co.dglee.lecture.domain.order.Order;
import kr.co.dglee.lecture.domain.order.OrderSearch;
import kr.co.dglee.lecture.domain.order.OrderStatus;
import kr.co.dglee.lecture.dto.OrderSimpleQueryDTO;
import kr.co.dglee.lecture.repository.OrderRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

  private final OrderRepository orderRepository;

  /**
   * Entity 직접 노출
   *
   * @return
   */
  @GetMapping("/api/v1/simple-orders")
  public List<Order> ordersV1() {

    List<Order> list = orderRepository.findAllByString(new OrderSearch());
    return list;
  }

  /**
   * DTO로 변환하여 반환
   *
   * @return
   */
  @GetMapping("/api/v2/simple-orders")
  public List<SimpleOrderDTO> ordersV2() {

    List<Order> orders = orderRepository.findAllByString(new OrderSearch());
    List<SimpleOrderDTO> orderDTOList = orders.stream().map(o -> new SimpleOrderDTO(o)).collect(Collectors.toList());

    return orderDTOList;
  }

  /**
   * FETCH JOIN으로 쿼리 수 최적화
   *
   * @return
   */
  @GetMapping("/api/v3/simple-orders")
  public List<SimpleOrderDTO> ordersV3() {

    List<Order> orders = orderRepository.findAllWithMemberDelivery();
    List<SimpleOrderDTO> orderDTOList = orders.stream().map(o -> new SimpleOrderDTO(o)).collect(Collectors.toList());

    return orderDTOList;
  }

  /**
   * 쿼리 자체를 DTO로 변환하여 SELECT 절 최적화
   *
   * @return
   */
  @GetMapping("/api/v4/simple-orders")
  public List<OrderSimpleQueryDTO> ordersV4() {

    return orderRepository.findOrderDTOs();
  }

  @Getter
  static class SimpleOrderDTO {

    private Long orderId;

    private String name;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    private Address address;

    public SimpleOrderDTO(Order order) {
      orderId = order.getId();
      name = order.getMember().getName();
      orderDate = order.getOrderDate();
      orderStatus = order.getStatus();
      address = order.getDelivery().getAddress();
    }
  }
}
