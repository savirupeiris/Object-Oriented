import java.io.Serializable;

public abstract class Product implements Serializable {
    //Instance Variables
    private String productID;
    private String productName;
    private int availableItems;
    private double price;

    //Default Constructor
    public Product(){

    }

    public Product(String productID, String productName) {
        this.productID = productID;
        this.productName = productName;
    }

    //Constructor with the four parameters
    public Product(String productID, String productName, int availableItems, double price){
        this.productID=productID;
        this.productName=productName;
        this.availableItems=availableItems;
        this.price=price;
    }
    public Product(String productID,String productName,double price){
        this.productID=productID;
        this.productName=productName;
        this.price=price;
    }
    //Get and Set Methods
    //When you call in the main method get method will return you the value
    //Set method will set a value and execute through the main method.
    public String getProductID() {
        return productID;
    }
    public void setProductID(String productID) {
        this.productID = productID;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getAvailableItems() {
        return availableItems;
    }
    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
