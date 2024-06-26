package kr.co.dglee.lecture.api;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.dglee.lecture.domain.order.Order;
import kr.co.dglee.lecture.domain.order.OrderItem;
import kr.co.dglee.lecture.domain.order.OrderSearch;
import kr.co.dglee.lecture.dto.OrderDTO;
import kr.co.dglee.lecture.repository.order.query.dto.OrderFlatDTO;
import kr.co.dglee.lecture.repository.order.query.dto.OrderQueryDTO;
import kr.co.dglee.lecture.repository.OrderRepository;
import kr.co.dglee.lecture.repository.order.query.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

  private final OrderRepository orderRepository;

  private final OrderQueryRepository orderQueryRepository;

  @GetMapping("/api/v1/orders")
  public List<Order> ordersV1() {
    List<Order> all = orderRepository.findAllByString(new OrderSearch());
    for (Order order : all) {
      order.getMember().getName();
      order.getDelivery().getAddress();
      List<OrderItem> orderItems = order.getOrderItems();
      orderItems.forEach(o -> o.getItem().getName());
    }
    return all;
  }

  @GetMapping("/api/v2/orders")
  public List<OrderDTO> ordersV2() {
    List<Order> all = orderRepository.findAllByString(new OrderSearch());

    List<OrderDTO> dtos = all.stream()
        .map(OrderDTO::new)
        .collect(Collectors.toList());

    return dtos;
  }

  @GetMapping("/api/v3/orders")
  public List<OrderDTO> ordersV3() {

    List<Order> orders = orderRepository.findAllWithItem();

    List<OrderDTO> dtos = orders.stream()
        .map(OrderDTO::new)
        .collect(Collectors.toList());

    return dtos;
  }

  @GetMapping("/api/v3.1/orders")
  public List<OrderDTO> ordersV3_page(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "100") int limit) {
    List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

    List<OrderDTO> dtos = orders.stream()
        .map(OrderDTO::new)
        .collect(Collectors.toList());

    return dtos;
  }

  @GetMapping("/api/v4/orders")
  public List<OrderQueryDTO> ordersV4() {
    return orderQueryRepository.findOrderQueryDTOs();
  }

  @GetMapping("/api/v5/orders")
  public List<OrderQueryDTO> ordersV5() {
    return orderQueryRepository.findOrderQueryDTO_optimization();
  }

  @GetMapping("/api/v6/orders")
  public List<OrderFlatDTO> ordersV6() {
    List<OrderFlatDTO> result = orderQueryRepository.findAllByDto_optimization();
    return result;
  }
}
