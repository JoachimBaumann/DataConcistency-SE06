package sdu.se06.Catalog.Catalog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "listing")
public class Listing {

    @Id
    @GeneratedValue
    private int listingID;
    private int sellerID;
    private String listingName;
    private boolean closed = false;
    private double listingPrice;
    private String ListingDescription;
    private String ListingCondition;
    private String pictureURL;

}


