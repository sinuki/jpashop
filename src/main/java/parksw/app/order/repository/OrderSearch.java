package parksw.app.order.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Getter;
import lombok.Setter;
import parksw.app.order.domain.OrderStatus;
import parksw.app.order.domain.QOrder;

/**
 * OrderSearch
 * author: sinuki
 * createdAt: 2019/11/30
 **/
@Getter
@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

    public BooleanExpression likeMemberName(QOrder order) {
        if (memberName == null) {
            return null;
        }

        return order.member.name.like(memberName + "%");
    }

    public BooleanExpression equalsOrderStatus(QOrder order) {
        if (orderStatus == null) {
            return null;
        }

        return order.status.eq(orderStatus);
    }
}
