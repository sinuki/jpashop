package parksw.app.member.domain;

import lombok.Getter;
import lombok.Setter;
import parksw.app.order.domain.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Member
 * author: sinuki
 * createdAt: 2019/11/09
 **/
@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
