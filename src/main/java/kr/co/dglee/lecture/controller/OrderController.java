package kr.co.dglee.lecture.controller;

import java.util.List;
import kr.co.dglee.lecture.domain.Member;
import kr.co.dglee.lecture.domain.item.Item;
import kr.co.dglee.lecture.domain.order.Order;
import kr.co.dglee.lecture.domain.order.OrderSearch;
import kr.co.dglee.lecture.domain.order.OrderStatus;
import kr.co.dglee.lecture.service.ItemService;
import kr.co.dglee.lecture.service.MemberService;
import kr.co.dglee.lecture.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final MemberService memberService;
  private final ItemService itemService;

  @GetMapping("/order")
  public String createForm(Model model) {

    List<Member> memberList = memberService.findMembers();
    List<Item> itemList = itemService.findAll();

    model.addAttribute("members", memberList);
    model.addAttribute("items", itemList);
    return "/pages/orders/orderForm";
  }

  @PostMapping("/order")
  public String createOrder(@RequestParam Long memberId,
      @RequestParam Long itemId,
      @RequestParam int count) {

    orderService.order(memberId, itemId, count);
    return "redirect:/orders";
  }

  @GetMapping("/orders")
  public String orderList(Model model,
      @ModelAttribute("orderSearch") OrderSearch orderSearch) {

    model.addAttribute("orders", orderService.findOrders(orderSearch));
    model.addAttribute("orderSearch", orderSearch);
    return "/pages/orders/orderList";
  }

  @PostMapping("/orders/{orderId}/cancel")
  public String orderCancel(@PathVariable Long orderId) {
    orderService.cancelOrder(orderId);
    return "redirect:/orders";
  }
}
