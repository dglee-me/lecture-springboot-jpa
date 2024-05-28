package kr.co.dglee.lecture.repository.order.query;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.co.dglee.lecture.repository.order.query.dto.OrderItemQueryDTO;
import kr.co.dglee.lecture.repository.order.query.dto.OrderQueryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

  private final EntityManager em;

  private static List<Long> getOrderIds(List<OrderQueryDTO> result) {
    return result.stream()
        .map(o -> o.getOrderId())
        .toList();
  }

  public List<OrderQueryDTO> findOrderQueryDTO_optimization() {
    List<OrderQueryDTO> result = findOrders();

    Map<Long, List<OrderItemQueryDTO>> orderItemMap = findOrderItemMap(getOrderIds(result));
    result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

    return result;
  }

  private Map<Long, List<OrderItemQueryDTO>> findOrderItemMap(List<Long> orderIds) {
    List<OrderItemQueryDTO> orderItems = em.createQuery(
        "SELECT new kr.co.dglee.lecture.repository.order.query.dto.OrderItemQueryDTO(oi.order.id, i.name, oi.orderPrice, oi.count)" +
            " FROM OrderItem oi" +
            " JOIN oi.item i" +
            " WHERE oi.order.id IN :orderIds", OrderItemQueryDTO.class
    ).setParameter("orderIds", orderIds).getResultList();

    Map<Long, List<OrderItemQueryDTO>> orderItemMap = orderItems.stream()
        .collect(Collectors.groupingBy(OrderItemQueryDTO::getOrderId));
    return orderItemMap;
  }

  public List<OrderQueryDTO> findOrderQueryDTOs() {
    List<OrderQueryDTO> result = findOrders();

    result.forEach(o -> {
      List<OrderItemQueryDTO> orderItems = findOrderItems(o.getOrderId());
      o.setOrderItems(orderItems);
    });

    return result;
  }

  private List<OrderItemQueryDTO> findOrderItems(Long orderId) {
    return em.createQuery(
            "SELECT new kr.co.dglee.lecture.repository.order.query.dto.OrderItemQueryDTO(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                " FROM OrderItem oi" +
                " JOIN oi.item i" +
                " WHERE oi.order.id = :orderId", OrderItemQueryDTO.class)
        .setParameter("orderId", orderId)
        .getResultList();
  }

  private List<OrderQueryDTO> findOrders() {
    return em.createQuery(
            "SELECT new kr.co.dglee.lecture.repository.order.query.dto.OrderQueryDTO(o.id, m.name, o.orderDate, o.status, d.address)" +
                " FROM Order o" +
                " JOIN o.member m" +
                " JOIN o.delivery d", OrderQueryDTO.class)
        .getResultList();
  }
}
