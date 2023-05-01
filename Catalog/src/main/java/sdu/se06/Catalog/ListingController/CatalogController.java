package sdu.se06.Catalog.ListingController;

import org.springframework.web.bind.annotation.*;
import sdu.se06.Catalog.Catalog.Listing;

import java.util.List;
@RequestMapping("/api/v1/")
@RestController
public class CatalogController {

    private final CatalogRepository repository;

    public CatalogController(CatalogRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/listing/")
    public Listing createListing(@RequestBody Listing listing) {

        return repository.save(listing);
    }

    @GetMapping("/listing/")
    public List<Listing> getListings() {

        return repository.findAll();
    }
}
