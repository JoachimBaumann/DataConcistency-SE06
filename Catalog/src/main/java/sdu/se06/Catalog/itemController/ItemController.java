package sdu.se06.Catalog.itemController;


import org.springframework.web.bind.annotation.*;
import sdu.se06.Catalog.Catalog.Item;

import java.util.List;
@RequestMapping("/Item/")
@RestController
public class ItemController {
    private final itemRepository repository;

    public ItemController(itemRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/api/v1/item")
    public Item createItem(@RequestBody Item item) {
        return repository.save(item);

    }

    @GetMapping("/api/v1/item")
    public List<Item> getItems() {
        return repository.findAll();
    }
}
