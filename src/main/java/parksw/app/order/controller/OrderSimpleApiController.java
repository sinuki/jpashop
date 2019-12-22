package parksw.app.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import parksw.app.order.domain.Order;
import parksw.app.order.repository.OrderRepository;
import parksw.app.order.repository.OrderSearch;

import java.util.List;

/**
 * XToOne
 * Order
 * Order -> Member
 * Order -> Delivery
 **/
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("api/v1/orders")
    public List<Order> ordersV1(OrderSearch orderSearch) {
        List<Order> all = orderRepository.findAll(orderSearch);
        return all;
    }
}
