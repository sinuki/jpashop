package parksw.app.order.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OrderQueryRepository
 * author: sinuki
 * createdAt: 2020/01/12
 **/
@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    private List<OrderQueryDto> findOrders() {
        return em.createQuery("select new parksw.app.order.repository.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)"
                                        + " from Order o"
                                        + " join o.member m"
                                        + " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new parksw.app.order.repository.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)"
                                        + " from OrderItem oi"
                                        + " join oi.item i"
                                        + " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> orders = findOrders();
        orders.forEach(order -> {
            List<OrderItemQueryDto> items = findOrderItems(order.getOrderId());
            order.setOrderItems(items);
        });
        return orders;
    }

    public List<OrderQueryDto> findAllByDtoOptimization() {
        List<OrderQueryDto> result = findOrders();
        List<Long> orderIds = result.stream().map(OrderQueryDto::getOrderId).collect(Collectors.toList());
        List<OrderItemQueryDto> orderItems = em.createQuery(
                "select new parksw.app.order.repository.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)"
                        + " from OrderItem oi"
                        + " join oi.item i"
                        + " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();
        Map<Long, List<OrderItemQueryDto> > orderItemMap = orderItems.stream().collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
        result.forEach(order -> {
            order.setOrderItems(orderItemMap.get(order.getOrderId()));
        });
        return result;
    }

    public List<OrderFlatDto> findAllByDtoFlat() {
        return em.createQuery(
                "select new parksw.app.order.repository.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)"
                        + " from Order o"
                        + " join o.member m"
                        + " join o.delivery d"
                        + " join o.orderItems oi"
                        + " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }
}
