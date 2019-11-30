package parksw.app.order.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import parksw.app.order.domain.Order;

import javax.persistence.EntityManager;
import java.util.List;

import static parksw.app.order.domain.QOrder.order;

/**
 * OrderRepository
 * author: sinuki
 * createdAt: 2019/11/30
 **/
@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(Order order) {
        em.persist(order);
    }

    public Order find(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        return queryFactory.selectFrom(order)
                .where(
                    orderSearch.likeMemberName(order),
                    orderSearch.equalsOrderStatus(order))
                .limit(100)
                .fetch();
    }
}
