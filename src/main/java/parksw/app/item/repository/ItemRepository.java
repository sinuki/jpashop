package parksw.app.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import parksw.app.item.domain.Item;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * ItemRepository
 * author: sinuki
 * createdAt: 2019/11/24
 **/
@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item find(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
