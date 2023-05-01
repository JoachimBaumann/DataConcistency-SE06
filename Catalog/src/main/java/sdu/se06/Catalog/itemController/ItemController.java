package sdu.se06.Catalog.itemController;


import org.springframework.web.bind.annotation.*;
import sdu.se06.Catalog.Catalog.Item;

import java.util.List;
@RequestMapping("/api/v1/")
@RestController
public class ItemController {
    private final itemRepository repository;

    public ItemController(itemRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/item")
    public Item createItem(Item item) {
        return repository.save(item);

    }

    @GetMapping("/item")
    public List<Item> getItems() {
        return repository.findAll();
    }
}
