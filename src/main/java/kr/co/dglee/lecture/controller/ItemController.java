package kr.co.dglee.lecture.controller;

import java.util.List;
import kr.co.dglee.lecture.domain.item.Book;
import kr.co.dglee.lecture.domain.item.Item;
import kr.co.dglee.lecture.form.BookForm;
import kr.co.dglee.lecture.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping(value = "/items")
  public String list(Model model) {
    List<Item> items = itemService.findAll();
    model.addAttribute("items", items);
    return "/pages/items/itemList";
  }

  @GetMapping("/items/new")
  public String createForm(Model model) {
    model.addAttribute("form", new BookForm());

    return "/pages/items/createItemForm";
  }

  @PostMapping("/items/new")
  public String createItem(BookForm form) {
    Book book = new Book();
    book.setName(form.getName());
    book.setPrice(form.getPrice());
    book.setStockQuantity(form.getStockQuantity());
    book.setAuthor(form.getAuthor());
    book.setIsbn(form.getIsbn());

    itemService.saveItem(book);
    return "redirect:/items";
  }

  @GetMapping("/items/{id}/edit")
  public String updateItemForm(Model model, @PathVariable Long id) {

    Book item = (Book) itemService.findById(id);

    BookForm bookForm = new BookForm();
    bookForm.setId(item.getId());
    bookForm.setName(item.getName());
    bookForm.setStockQuantity(item.getStockQuantity());
    bookForm.setPrice(item.getPrice());
    bookForm.setIsbn(item.getIsbn());
    bookForm.setAuthor(item.getAuthor());

    model.addAttribute("form", bookForm);
    return "/pages/items/updateItemForm";
  }

  @PostMapping("/items/{id}/edit")
  public String updateItem(@ModelAttribute("form") BookForm form) {

   /* Book book = new Book();
    book.setId(form.getId());
    book.setAuthor(form.getAuthor());
    book.setStockQuantity(form.getStockQuantity());
    book.setName(form.getName());
    book.setIsbn(form.getIsbn());
    book.setPrice(form.getPrice());

    itemService.saveItem(book);*/

    itemService.updateItem(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity());
    return "redirect:/items";
  }
}
