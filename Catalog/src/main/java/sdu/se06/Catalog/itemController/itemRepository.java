package sdu.se06.Catalog.itemController;

import org.springframework.data.jpa.repository.JpaRepository;
import sdu.se06.Catalog.Catalog.Item;

public interface itemRepository extends JpaRepository<Item, Integer> {
}
