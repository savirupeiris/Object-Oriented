public class Electronics extends Product {
    //Instance Variables
    private String productBrand;
    private String warrantyPeriod;

    //Default Constructor-If there's no any constructors calling this constructor will execute
    public Electronics(){
    }
    //Parameterized Constructor
    public Electronics(String productID,String productName,int numberOfAvailableItems,double price,String productBrand,String warrantyPeriod){
        super(productID, productName, numberOfAvailableItems, price);
        this.productBrand=productBrand;
        this.warrantyPeriod=warrantyPeriod;
    }

    public Electronics(String productID, String productName, String productBrand, String warrantyPeriod) {
        super(productID, productName);
        this.productBrand = productBrand;
        this.warrantyPeriod = warrantyPeriod;
    }

    //Get and Set for Brands
    public String getBrand() {
        return productBrand;
    }
    public void setBrand(String brand) {
        this.productBrand = productBrand;
    }
    //Get and Set for Warranty Period
    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }
    public void setWarrantyPeriod(String warrantyPeriod) {
        warrantyPeriod = warrantyPeriod;
    }
}

