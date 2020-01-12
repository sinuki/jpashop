package parksw.app.order.repository.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import parksw.app.member.domain.Address;
import parksw.app.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderQueryDto
 * author: sinuki
 * createdAt: 2020/01/12
 **/
@Data
@EqualsAndHashCode(of = "orderId")
public class OrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, List<OrderItemQueryDto> orderItems) {
        this(orderId, name, orderDate, orderStatus, address);
        this.orderItems = orderItems;
    }
}
