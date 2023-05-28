package sdu.se06.Catalog.ListingController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sdu.se06.Catalog.model.Listing;
import sdu.se06.Catalog.ListingController.CatalogController;
import sdu.se06.Catalog.ListingController.CatalogRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CatalogControllerUnitTest {

    private CatalogController catalogController;

    @Mock
    private CatalogRepository catalogRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        catalogController = new CatalogController(catalogRepository);
    }

    @Test
    public void testCreateListing() {
        // Create a sample listing
        Listing listing = new Listing();
        listing.setListingName("Test Listing");
        listing.setListingPrice(10.0);

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Data saved");

        // Mock repository save method
        when(catalogRepository.save(listing)).thenReturn(listing);

        // Perform the createListing() method
        ResponseEntity<String> actualResponse = catalogController.createListing(listing);

        // Verify the repository save method was called once
        verify(catalogRepository, times(1)).save(listing);

        // Assert the response
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetListing() {
        // Create a sample listing
        Listing listing = new Listing(1, 1, "Listing 1", false, 10.0, "Description 1", "Condition 1", "http://example.com/1");
        Optional<Listing> optionalListing = Optional.of(listing);

        ResponseEntity<Listing> expectedResponse = new ResponseEntity<>(listing, HttpStatus.OK);

        // Mock repository findById method
        when(catalogRepository.findById(1)).thenReturn(optionalListing);

        // Perform the getListing() method
        ResponseEntity<Listing> actualResponse = catalogController.getListing(1);

        // Verify the repository findById method was called once with the correct ID
        verify(catalogRepository, times(1)).findById(1);

        // Assert the response
        assertEquals(expectedResponse, actualResponse);
    }
}
