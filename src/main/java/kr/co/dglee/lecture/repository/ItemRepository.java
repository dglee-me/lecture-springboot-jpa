package kr.co.dglee.lecture.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import kr.co.dglee.lecture.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

  public Item findById(Long id) {
    return em.find(Item.class, id);
  }

  public List<Item> findAll() {
    return em.createQuery("SELECT i FROM Item i", Item.class)
        .getResultList();
  }
}
