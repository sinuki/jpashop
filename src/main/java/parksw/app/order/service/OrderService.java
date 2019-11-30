package parksw.app.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parksw.app.delivery.domain.Delivery;
import parksw.app.item.domain.Item;
import parksw.app.item.repository.ItemRepository;
import parksw.app.member.domain.Member;
import parksw.app.member.repository.MemberRepository;
import parksw.app.order.domain.Order;
import parksw.app.order.domain.OrderItem;
import parksw.app.order.repository.OrderRepository;
import parksw.app.order.repository.OrderSearch;

import java.util.List;

/**
 * OrderService
 * author: sinuki
 * createdAt: 2019/11/30
 **/
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    /**
     * 주문
     * @param memberId
     * @param itemId
     * @param count
     * @return
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.find(memberId);
        Item item = itemRepository.find(itemId);
        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        // 주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     * @param orderId
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 엔티티 조회
        Order order = orderRepository.find(orderId);
        // 주문 취소
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
