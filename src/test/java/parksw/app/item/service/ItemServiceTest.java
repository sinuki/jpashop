package parksw.app.item.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import parksw.app.item.domain.Movie;
import parksw.app.item.repository.ItemRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ItemServiceTest
 * author: sinuki
 * createdAt: 2019/11/24
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @Test
    void 상품_저장() {
        Movie movieItem = new Movie();
        movieItem.setName("포레스트검프");
        movieItem.setActor("톰 행크스");
        movieItem.setDirector("로버트 제머키스");

        Long savedId = itemService.save(movieItem);

        assertEquals(movieItem, itemRepository.find(savedId));
    }
}