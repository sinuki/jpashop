package parksw.app.order.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import parksw.app.member.domain.Address;
import parksw.app.order.domain.Order;
import parksw.app.order.domain.OrderItem;
import parksw.app.order.domain.OrderStatus;
import parksw.app.order.repository.OrderRepository;
import parksw.app.order.repository.OrderSearch;
import parksw.app.order.repository.query.OrderFlatDto;
import parksw.app.order.repository.query.OrderItemQueryDto;
import parksw.app.order.repository.query.OrderQueryDto;
import parksw.app.order.repository.query.OrderQueryRepository;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * OrderApiController
 * author: sinuki
 * createdAt: 2020/01/02
 **/
@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("api/v1/orders")
    public List<Order> ordersV1(OrderSearch orderSearch) {
        List<Order> all = orderRepository.findAll(orderSearch);
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    @GetMapping("api/v2/orders")
    public List<OrderDto> ordersV2(OrderSearch orderSearch) {
        List<Order> orders = orderRepository.findAll(orderSearch);
        return orders.stream().map(OrderDto::new).collect(toList());
    }

    @GetMapping("api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        return orders.stream().map(OrderDto::new).collect(toList());
    }

    @GetMapping("api/v3.1/orders")
    public List<OrderDto> ordersV3_1(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                     @RequestParam(value = "limit", defaultValue = "100") int limit) {
        // xToOne관계는 결과 로우수를 뻥튀기시키지 않으므로 페치 조인해서 가져와도 상관없음.
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        return orders.stream().map(OrderDto::new).collect(toList());
    }

    @GetMapping("api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        // n + 1문제가 발생
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        // query 2번으로 결과 조회 가능
        return orderQueryRepository.findAllByDtoOptimization();
    }

    @GetMapping("api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        // query 1번으로 결과 조회 가능, 그러나 페이징이 불가능하고 애플리케이션 레벨에서 작업이 많음
        List<OrderFlatDto> result = orderQueryRepository.findAllByDtoFlat();
        return result.stream()
                    .collect(
                            groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                            mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList()))
                    ).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                                            e.getKey().getName(),
                                            e.getKey().getOrderDate(),
                                            e.getKey().getOrderStatus(),
                                            e.getKey().getAddress(),
                                            e.getValue())
                ).collect(toList());
    }

    @Getter
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        // OrderItem은 여전히 엔티티를 그대로 사용하고 있음.
        // 흔히 하기 쉬운 실수로 이러면 안 되고 귀찮더라도 전부 바꿔줘야 한다.
//        private List<OrderItem> orderItems;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
            this.orderItems = order.getOrderItems().stream().map(OrderItemDto::new).collect(toList());
        }
    }
    @Getter
    static class OrderItemDto {
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }
}
