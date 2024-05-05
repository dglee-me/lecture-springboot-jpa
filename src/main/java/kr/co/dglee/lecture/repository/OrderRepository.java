package kr.co.dglee.lecture.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import kr.co.dglee.lecture.domain.order.Order;
import kr.co.dglee.lecture.domain.order.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

  private final EntityManager em;

  public void save(Order order) {
    em.persist(order);
  }

  public Order findById(Long id) {
    return em.find(Order.class, id);
  }

  //== 다 최악 ==

  public List<Order> findAllByString(OrderSearch orderSearch) {

    String jpql = "SELECT o FROM Order o JOIN o.member m";
    boolean isFirst = true;

    if (orderSearch.getOrderStatus() != null) {
      if (isFirst) {
        jpql += " WHERE";
        isFirst = false;
      } else {
        jpql += " AND";
      }
      jpql += " o.status = :status";
    }

    if (StringUtils.hasText(orderSearch.getMemberName())) {
      if (isFirst) {
        jpql += " WHERE";
        isFirst = false;
      } else {
        jpql += " AND";
      }
      jpql += " m.name LIKE :name";
    }

    TypedQuery<Order> query = em.createQuery(jpql, Order.class)
        .setMaxResults(1000);

    if (orderSearch.getOrderStatus() != null) {
      query = query.setParameter("status", orderSearch.getOrderStatus());
    }
    if (StringUtils.hasText(orderSearch.getMemberName())) {
      query = query.setParameter("name", orderSearch.getMemberName());
    }

    return query.getResultList();
  }

  public List<Order> findAllByCriteria(OrderSearch orderSearch) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Order> cq = cb.createQuery(Order.class);

    Root<Order> o = cq.from(Order.class);
    Join<Object, Object> m = o.join("member", JoinType.INNER);

    List<Predicate> criteria = new ArrayList<>();

    if (orderSearch.getOrderStatus() != null) {
      criteria.add(cb.equal(o.get("status"), orderSearch.getOrderStatus()));
    }
    if (StringUtils.hasText(orderSearch.getMemberName())) {
      criteria.add(cb.like(m.get("name"), "%" + orderSearch.getMemberName() + "%"));
    }

    cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
    return em.createQuery(cq).setMaxResults(1000).getResultList();
  }
}
