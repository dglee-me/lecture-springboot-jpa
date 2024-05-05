package kr.co.dglee.lecture.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.persistence.EntityManager;
import kr.co.dglee.lecture.domain.Address;
import kr.co.dglee.lecture.domain.Member;
import kr.co.dglee.lecture.domain.item.Book;
import kr.co.dglee.lecture.domain.order.Order;
import kr.co.dglee.lecture.domain.order.OrderStatus;
import kr.co.dglee.lecture.exception.NotEnoughStockException;
import kr.co.dglee.lecture.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceTest {

  @Autowired
  EntityManager em;

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  private OrderService orderService;

  private Member createMemeber() {

    Member member = new Member();
    member.setName("회원1");
    member.setAddress(new Address("서울", "강가", "12345"));
    em.persist(member);

    return member;
  }

  private Book createItem() {
    Book book = new Book();
    book.setName("시골 JPA");
    book.setPrice(10000);
    book.setStockQuantity(10);
    em.persist(book);

    return book;
  }

  @Test
  @DisplayName("상품 주문")
  void 상품_주문() throws Exception {
    // given
    Member member = createMemeber();
    Book book = createItem();

    int orderCount = 2;

    // when
    Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

    // then
    Order order = orderRepository.findById(orderId);

    assertEquals(OrderStatus.ORDER, order.getStatus(), "상품 주문시 상태는 ORDER여야 한다.");
    assertEquals(1, order.getOrderItems().size(), "주문한 상품의 수는 정확해야한다.");
    assertEquals(10000 * orderCount, order.getTotalPrice(), "주문의 총 가격은 가격 * 수량이다.");
    assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 감소해야 한다.");
  }

  @Test
  @DisplayName("상품 주문 시 재고 초과")
  void 상품_주문_재고_초과() throws Exception {
    // given
    Member member = createMemeber();
    Book book = createItem();

    int orderCount = 11;

    // when
    // then
    assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), orderCount), "재고의 갯수 이상으로 주문하면 실패해야한다.");
  }

  @Test
  @DisplayName("상품을 주문 후 취소했을 때")
  void 상품_주문_취소() throws Exception {
    // given
    Member member = createMemeber();
    Book item = createItem();

    int orderCount = 2;
    Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

    // when
    orderService.cancelOrder(orderId);

    // then
    Order order = orderRepository.findById(orderId);

    assertEquals(OrderStatus.CANCEL, order.getStatus(), "주문 취소 시 상태는 취소여야한다.");
    assertEquals(10, item.getStockQuantity(), "주문 취소된 상품은 취소된 수량만큼 갯수가 증가해야한다.");
  }
}