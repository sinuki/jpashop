package parksw.app.order.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import parksw.app.member.domain.Address;
import parksw.app.order.domain.Order;
import parksw.app.order.domain.OrderStatus;
import parksw.app.order.repository.OrderRepository;
import parksw.app.order.repository.OrderSearch;
import parksw.app.order.repository.SimpleOrderQueryDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * XToOne
 * Order
 * Order -> Member
 * Order -> Delivery
 **/
@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    // bad case
//    @GetMapping("api/v1/orders")
//    public List<Order> ordersV1(OrderSearch orderSearch) {
//        List<Order> all = orderRepository.findAll(orderSearch);
//        return all;
//    }

    @GetMapping("api/v2/orders")
    public List<SimpleOrderDto> ordersV2(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch).stream()
                                .map(SimpleOrderDto::new)
                                .collect(Collectors.toList());
    }

    @GetMapping("api/v3/orders")
    public List<SimpleOrderDto> orderV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
    }

    @GetMapping("api/v4/orders")
    public List<SimpleOrderQueryDto> orderV4() {
        return orderRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }
}
