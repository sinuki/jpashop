package parksw.app.order.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import parksw.app.item.domain.Book;
import parksw.app.item.exception.NotEnoughStockException;
import parksw.app.member.domain.Address;
import parksw.app.member.domain.Member;
import parksw.app.order.domain.Order;
import parksw.app.order.domain.OrderStatus;
import parksw.app.order.repository.OrderRepository;
import parksw.app.order.repository.OrderSearch;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * OrderServiceTest
 * author: sinuki
 * createdAt: 2019/11/30
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문() {
        Member member = createMember();
        em.persist(member);

        int bookPrice = 10_000;
        int bookQunatity = 10;
        Book book = createBook(bookPrice, bookQunatity);
        em.persist(book);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Order getOrder = orderRepository.find(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(bookPrice * orderCount, getOrder.getTotalPrice(), "주문 가격은 * 수량이다.");
        assertEquals(bookQunatity - orderCount, book.getStockQuantity(), "주문 수량만큼 재고가 줄어있어야 한다.");
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-456"));
        return member;
    }

    private Book createBook(int price, int quantity) {
        Book book = new Book();
        book.setName("JPA");
        book.setPrice(price);
        book.setStockQuantity(quantity);
        return book;
    }

    @Test
    void 상품재고수량초과() {
        Member member = createMember();
        em.persist(member);

        int bookPrice = 10_000;
        int bookQunatity = 10;
        Book book = createBook(bookPrice, bookQunatity);
        em.persist(book);

        int orderCount = bookQunatity + 1;
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        });
    }

    @Test
    void 주문취소() {
        Member member = createMember();
        em.persist(member);
        Book book = createBook(10_000, 10);
        em.persist(book);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        orderService.cancelOrder(orderId);
        Order getOrder = orderRepository.find(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 CANCEL이어야 한다.");
        assertEquals(10, book.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");
    }

    @Test
    void 주문검색_정상_테스트_파라미터_존재() {
        Member member = createMember();
        em.persist(member);

        Book book = createBook(10_000, 10);
        em.persist(book);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName("회원");
        orderSearch.setOrderStatus(OrderStatus.ORDER);

        List<Order> orders = orderRepository.findAll(orderSearch);
        assertEquals(1, orders.size());
        assertEquals(orderId, orders.get(0).getId());
    }

    @Test
    void 주문검색_정상_테스트_파라미터_미존재() {
        Member member = createMember();
        em.persist(member);

        Book book = createBook(10_000, 10);
        em.persist(book);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        OrderSearch orderSearch = new OrderSearch();

        List<Order> orders = orderRepository.findAll(orderSearch);
        assertEquals(1, orders.size());
        assertEquals(orderId, orders.get(0).getId());
    }

    @Test
    void 주문검색_실패_테스트() {
        Member member = createMember();
        em.persist(member);

        Book book = createBook(10_000, 10);
        em.persist(book);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setOrderStatus(OrderStatus.CANCEL);

        List<Order> orders = orderRepository.findAll(orderSearch);
        assertEquals(0, orders.size());
    }
}