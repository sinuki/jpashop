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
    // 엔티티를 바로 조회하면 재사용성도 좋고 개발도 단순해진다.
    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery("select o from Order o "
                                + "join fetch o.member m "
                                + "join fetch o.delivery d", Order.class)
                .getResultList();
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery("select o from Order o "
                + "join fetch o.member m "
                + "join fetch o.delivery d", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    // 일반 SQL처럼 원하는 컬럼만 직접 조회 -> 애플리케이션 네트워크 용량 최적화가 가능하나 생각보다 미비
    // 재사용성이 떨어지고 api스펙이 리포지토리에 침투 -> 별개의 패키지로 따로 떼어 만드는 것을 권장.
    // ex) queryservice, queryrepository
    // 엄청나게 헤비한 트래픽을 처리하는 경우 고민해보는 게 좋다.
    public List<SimpleOrderQueryDto> findOrderDtos() {
        return em.createQuery("select new parksw.app.order.repository.SimpleOrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o "
                                + "join o.member m "
                                + "join o.delivery d", SimpleOrderQueryDto.class)
                .getResultList();
    }

    public List<Order> findAllWithItem() {
        // 여기서 distinct는 일반적인 쿼리와 동작이 다르다.
        // 일반적인 쿼리에서는 로우의 모든 값이 같아야 중복을 제거하지만
        // 여기서는 엔티티가 중복인 경우(=pk가 같은 경우)에도 중복을 제거해준다.
        // * 페치 조인을 하게 되면 모든 데이터를 읽어오고 페이징을 메모리에서 하게 되므로 주의.
        // * 컬렉션 페치 조인은 1개만 사용 가능하다. 둘 이상이면 데이터가 부정합하게 조회될 수 있다.
        return em.createQuery("select distinct o from Order o"
                                        + " join fetch o.member m"
                                        + " join fetch o.delivery d"
                                        + " join fetch o.orderItems oi"
                                        + " join fetch oi.item i", Order.class)
                .getResultList();
    }
}
