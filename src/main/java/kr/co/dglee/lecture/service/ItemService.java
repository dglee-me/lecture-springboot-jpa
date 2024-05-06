package kr.co.dglee.lecture.service;

import java.util.List;
import kr.co.dglee.lecture.domain.item.Item;
import kr.co.dglee.lecture.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

  private final ItemRepository itemRepository;

  @Transactional
  public void saveItem(Item item) {
    itemRepository.save(item);
  }

  /**
   * 변경 감지를 통해 업데이트를 한다.
   * <p>
   * 영속 상태의 데이터를 불러오고, 변경이 된 데이터만 확실히 변경되도록 바꾼다.
   * (Merge를 사용할 경우 null 등의 문제가 있을 수 있다.)
   *
   * @param id
   * @param name
   * @param price
   * @param stockQuantity
   */
  @Transactional
  public void updateItem(Long id, String name, int price, int stockQuantity) {
    Item item = itemRepository.findById(id);
    item.setName(name);
    item.setPrice(price);
    item.setStockQuantity(stockQuantity);
  }

  public Item findById(Long id) {
    return itemRepository.findById(id);
  }

  public List<Item> findAll() {
    return itemRepository.findAll();
  }
}
