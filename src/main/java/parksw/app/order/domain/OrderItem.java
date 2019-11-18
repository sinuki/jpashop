package parksw.app.order.domain;

import lombok.Getter;
import lombok.Setter;
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
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;
}
