import java.lang.reflect.Constructor;
public class Clothing extends Product {
    private String Size;
    private String Colour;
    //Default Constructor
    public Clothing(){

    }
    public Clothing(String Size,String Colour){
        this.Size=Size;
        this.Colour=Colour;
    }
    public Clothing(String productID,String productName,int numberOfAvailableItems,double price, String Size, String Colour){
        super(productID, productName, numberOfAvailableItems, price);
        this.Size=Size;
        this.Colour=Colour;
    }
    public Clothing(String productID, String productName, String Size, String color) {
        super(productID, productName);
        this.Size = Size;
        this.Colour = color;
    }
    public String getSize() {
        return Size;
    }
    public void setSize(String size) {
        Size = size;
    }
    public String getColour() {
        return Colour;
    }
    public void setColour(String colour) {
        Colour = colour;
    }
}
