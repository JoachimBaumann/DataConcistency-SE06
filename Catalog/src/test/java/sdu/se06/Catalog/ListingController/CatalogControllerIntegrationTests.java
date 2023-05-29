package sdu.se06.Catalog.ListingController;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sdu.se06.Catalog.model.Listing;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatalogController.class)
public class CatalogControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatalogRepository catalogRepository;

    @InjectMocks
    private CatalogController catalogController;


    // Integration Test
    @Test
    public void testGetListings() throws Exception {
        // Mock data
        Listing listing1 = new Listing(1,1, "Listing 1", false, 10.0, "Description 1", "Condition 1", "http://example.com/1");
        Listing listing2 = new Listing(2,2, "Listing 2", true, 20.0, "Description 2", "Condition 2", "http://example.com/2");
        List<Listing> listings = Arrays.asList(listing1, listing2);

        // Mock repository response
        when(catalogRepository.findAll()).thenReturn(listings);

        // Perform the GET request
        mockMvc.perform(get("/api/v1/listings/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].listingID").value(1))
                .andExpect(jsonPath("$[0].listingName").value("Listing 1"))
                .andExpect(jsonPath("$[0].closed").value(false))
                .andExpect(jsonPath("$[0].listingPrice").value(10.0))
                .andExpect(jsonPath("$[0].listingDescription").value("Description 1"))
                .andExpect(jsonPath("$[0].listingCondition").value("Condition 1"))
                .andExpect(jsonPath("$[0].pictureURL").value("http://example.com/1"))
                .andExpect(jsonPath("$[1].listingID").value(2))
                .andExpect(jsonPath("$[1].listingName").value("Listing 2"))
                .andExpect(jsonPath("$[1].closed").value(true))
                .andExpect(jsonPath("$[1].listingPrice").value(20.0))
                .andExpect(jsonPath("$[1].listingDescription").value("Description 2"))
                .andExpect(jsonPath("$[1].listingCondition").value("Condition 2"))
                .andExpect(jsonPath("$[1].pictureURL").value("http://example.com/2"));
    }


    // Integration Test
    @Test
    public void testCreateListing() throws Exception {
        // Create a sample listing
        Listing listing = new Listing();
        listing.setListingName("Test Listing");
        listing.setListingPrice(10.0);

        // Perform the POST request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/listings/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"listingName\":\"Test Listing\",\"listingPrice\":10.0}");

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Data saved"));

        // Verify that the repository's save method was called with any listing object
        verify(catalogRepository).save(any(Listing.class));
    }

    // Integration test
    @Test
    public void testGetListing() throws Exception {
        // Mock data
        Listing listing = new Listing(1, 1, "Listing 1", false, 10.0, "Description 1", "Condition 1", "http://example.com/1");
        Optional<Listing> optionalListing = Optional.of(listing);

        // Mock repository response
        when(catalogRepository.findById(1)).thenReturn(optionalListing);

        // Perform the GET request
        mockMvc.perform(get("/api/v1/listing/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.listingID").value(1))
                .andExpect(jsonPath("$.listingName").value("Listing 1"))
                .andExpect(jsonPath("$.closed").value(false))
                .andExpect(jsonPath("$.listingPrice").value(10.0))
                .andExpect(jsonPath("$.listingDescription").value("Description 1"))
                .andExpect(jsonPath("$.listingCondition").value("Condition 1"))
                .andExpect(jsonPath("$.pictureURL").value("http://example.com/1"));

        // Verify that the repository's findById method was called with the correct ID
        verify(catalogRepository).findById(1);
    }
}
