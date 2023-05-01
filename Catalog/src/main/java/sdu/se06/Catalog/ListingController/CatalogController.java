package sdu.se06.Catalog.ListingController;

import org.springframework.web.bind.annotation.*;
import sdu.se06.Catalog.Catalog.Listing;

import java.util.List;
@RequestMapping("/listing")
@RestController
public class CatalogController {

    private final CatalogRepository repository;

    public CatalogController(CatalogRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/api/v1/")
    public Listing createListing(@RequestBody Listing listing) {

        return repository.save(listing);
    }

    @GetMapping("/api/v1/")
    public List<Listing> getListings() {

        return repository.findAll();
    }
}
