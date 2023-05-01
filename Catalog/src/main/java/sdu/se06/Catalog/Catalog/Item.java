package sdu.se06.Catalog.Catalog;


import jakarta.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "itemID")
    private int itemID;
    private String itemName;
    private String itemDescription;
    private String itemCondition;


    public Item() {
    }

    public Item(int itemID, String itemName, String itemDescription, String itemCondition) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCondition = itemCondition;
    }

    public Item(String itemName, String itemDescription, String itemCondition) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCondition = itemCondition;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemCondition() {
        return itemCondition;
    }

    public void setItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
    }

    public int getItemID() {
        return itemID;
    }
}
