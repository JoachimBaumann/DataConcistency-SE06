package sdu.se06.Catalog.Catalog;

import jakarta.persistence.*;

@Entity
public class Listing {

    @Id
    @GeneratedValue
    private int listingID;
    private String listingName;
    private boolean closed;
    private double listingPrice;
    private int sellerID;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "itemID")
    private Item item;

    public Listing(int listingID, String listingName, boolean closed, double listingPrice, int sellerID, Item item) {
        this.listingID = listingID;
        this.listingName = listingName;
        this.closed = false;
        this.listingPrice = listingPrice;
        this.sellerID = sellerID;
        this.item = item;
    }

    public Listing() {
    }

    public int getListingID() {
        return listingID;
    }

    public String getListingName() {
        return listingName;
    }

    public boolean isClosed() {
        return closed;
    }

    public double getListingPrice() {
        return listingPrice;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

    public void setListingName(String listingName) {
        this.listingName = listingName;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void setListingPrice(double listingPrice) {
        this.listingPrice = listingPrice;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }
}


