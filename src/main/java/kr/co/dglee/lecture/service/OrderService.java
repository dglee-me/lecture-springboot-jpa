package kr.co.dglee.lecture.service;

import kr.co.dglee.lecture.domain.Member;
import kr.co.dglee.lecture.domain.delivery.Delivery;
import kr.co.dglee.lecture.domain.item.Item;
import kr.co.dglee.lecture.domain.order.Order;
import kr.co.dglee.lecture.domain.order.OrderItem;
import kr.co.dglee.lecture.repository.ItemRepository;
import kr.co.dglee.lecture.repository.MemberRepository;
import kr.co.dglee.lecture.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

  private final OrderRepository orderRepository;

  private final MemberRepository memberRepository;

  private final ItemRepository itemRepository;

  /**
   * 주문 취소
   *
   * @param memberId 사용자 ID
   * @param itemId   상품 ID
   * @param count    주문 갯수
   * @return
   */
  @Transactional
  public Long order(Long memberId, Long itemId, int count) {

    Member member = memberRepository.findById(memberId);
    Item item = itemRepository.findById(itemId);

    // 배송 정보 생성
    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());

    // 주문 상품 생성
    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

    // 주문 생성
    Order order = Order.createOrder(member, delivery, orderItem);

    // 주문 저장 (Delivery, OrderItem은 cascade 옵션을 따라 Order와 함께 저장된다.)
    orderRepository.save(order);
    return order.getId();
  }

  @Transactional
  public void cancelOrder(Long orderId) {
    Order order = orderRepository.findById(orderId);
    order.cancel();
  }

  // 주문 검색
}
