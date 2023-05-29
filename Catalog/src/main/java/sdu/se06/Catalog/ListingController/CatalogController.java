package sdu.se06.Catalog.ListingController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sdu.se06.Catalog.model.Listing;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/")
@RestController
public class CatalogController {

    private final CatalogRepository repository;

    public CatalogController(CatalogRepository repository) {
        this.repository = repository;
    }


    @PostMapping("/listings/")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> createListing(@RequestBody Listing listings) {

        repository.save(listings);
        return ResponseEntity.ok("Data saved");
    }

    @GetMapping("/listings/")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Listing> getListings() {

        return repository.findAll();
    }


    @GetMapping(value = "/listing/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Listing> getListing(@PathVariable("id") int id) {
        Optional<Listing> listingData = repository.findById(id);

        if (listingData.isPresent()) {
            return new ResponseEntity<>(listingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
