package parksw.app.order.repository;

import lombok.Data;
import parksw.app.member.domain.Address;
import parksw.app.order.domain.OrderStatus;

import java.time.LocalDateTime;

/**
 * SimpleOrderQueryDto
 * author: sinuki
 * createdAt: 2020/01/01
 **/
@Data
public class SimpleOrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
