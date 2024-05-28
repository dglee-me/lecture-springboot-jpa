package kr.co.dglee.lecture.repository.order.query;

import jakarta.persistence.EntityManager;
import java.util.List;
import kr.co.dglee.lecture.repository.order.query.dto.OrderItemQueryDTO;
import kr.co.dglee.lecture.repository.order.query.dto.OrderQueryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

  private final EntityManager em;

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
