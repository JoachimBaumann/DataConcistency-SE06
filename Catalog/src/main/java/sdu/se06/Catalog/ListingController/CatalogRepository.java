package sdu.se06.Catalog.ListingController;

import org.springframework.data.jpa.repository.JpaRepository;
import sdu.se06.Catalog.Catalog.Listing;

public interface CatalogRepository extends JpaRepository<Listing, Integer> {
}
