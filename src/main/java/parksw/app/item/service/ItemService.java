package parksw.app.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parksw.app.item.domain.Item;
import parksw.app.item.repository.ItemRepository;

import java.util.List;

/**
 * ItemService
 * author: sinuki
 * createdAt: 2019/11/24
 **/
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long save(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item find(Long itemId) {
        return itemRepository.find(itemId);
    }
}
