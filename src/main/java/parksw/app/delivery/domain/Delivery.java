package parksw.app.delivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import parksw.app.member.domain.Address;
import parksw.app.order.domain.Order;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

/**
 * Delivery
 * author: sinuki
 * createdAt: 2019/11/10
 **/
@Entity
@Getter
@Setter
@ToString
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
