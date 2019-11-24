package parksw.app.item.domain;

import lombok.Getter;
import lombok.Setter;
import parksw.app.item.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Item
 * author: sinuki
 * createdAt: 2019/11/10
 **/
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (0 > restStock) {
            throw new NotEnoughStockException("재고량이 충분하지 않습니다.");
        }
        this.stockQuantity = restStock;
    }
}
