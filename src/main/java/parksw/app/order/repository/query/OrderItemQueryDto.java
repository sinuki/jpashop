package parksw.app.order.repository.query;

import lombok.Data;

/**
 * OrderItemQueryDto
 * author: sinuki
 * createdAt: 2020/01/12
 **/
@Data
public class OrderItemQueryDto {

    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemQueryDto(Long orderId, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
