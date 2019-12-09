package parksw.app.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import parksw.app.item.domain.Item;
import parksw.app.item.service.ItemService;
import parksw.app.member.domain.Member;
import parksw.app.member.service.MemberService;
import parksw.app.order.repository.OrderSearch;
import parksw.app.order.service.OrderService;

import java.util.List;

/**
 * OrderController
 * author: sinuki
 * createdAt: 2019/12/09
 **/
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("order")
    public String createForm(Model model) {
        List<Member> members = memberService.findAll();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    @PostMapping("order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        model.addAttribute("orders", orderService.findOrders(orderSearch));
        return "order/orderList";
    }

    @PostMapping("orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
