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

  public Item findById(Long id) {
    return itemRepository.findById(id);
  }

  public List<Item> findAll() {
    return itemRepository.findAll();
  }
}
