package sdu.se06.Catalog.model;

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

    public Listing(int sellerID, String listingName, boolean closed, double listingPrice, String listingDescription, String listingCondition, String pictureURL) {
        this.sellerID = sellerID;
        this.listingName = listingName;
        this.closed = closed;
        this.listingPrice = listingPrice;
        ListingDescription = listingDescription;
        ListingCondition = listingCondition;
        this.pictureURL = pictureURL;
    }
}


