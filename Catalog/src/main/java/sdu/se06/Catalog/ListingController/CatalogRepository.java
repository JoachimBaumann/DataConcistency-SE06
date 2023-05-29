package sdu.se06.Catalog.ListingController;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sdu.se06.Catalog.model.Listing;
@Repository
public interface CatalogRepository extends JpaRepository<Listing, Integer> {


}
