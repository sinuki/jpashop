package parksw.app.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import parksw.app.item.domain.Item;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

/**
 * OrderItem
 * author: sinuki
 * createdAt: 2019/11/10
 **/
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    /**
     * 주문 상품 생성
     * @param item
     * @param orderPrice
     * @param count
     * @return
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem  = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    /**
     * 주문 상품 취소
     */
    public void cancel() {
        item.addStock(count);
    }

    /**
     * 주문 상품 전체 가격 조회
     * @return
     */
    public int getTotalPrice() {
        return orderPrice * count;
    }
}
