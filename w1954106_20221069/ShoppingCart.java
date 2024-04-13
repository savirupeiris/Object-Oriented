import java.util.ArrayList;
public class ShoppingCart  {
    //Creating an arraylist to add,remove items from the shopping cart
    ArrayList<Product> ListOfProducts = new ArrayList<Product>();
    private Product product;
    private int quantity;
    private double price;

    //Add Product from the Arraylist

    public ShoppingCart(Product product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void addProduct(Product product) {
        ListOfProducts.add(product);
    }

    //Remove Product from the Arraylist
    public void removeProduct(Product product) {
        ListOfProducts.remove(product);
    }
    //Calculation of the Total cost of the product arraylist
    //All the list of products will be added in the arraylist.
    //each element from the arraylist will added to the totalCostPrice and return the full amount.
    //.get(x)--->It will go with all the elements one by one and add the price to the arraylist
    public void totalCost() {
        double totalCostPrice = 0;
        for (int x = 0; x < ListOfProducts.size(); x++) {
            totalCostPrice = totalCostPrice + ListOfProducts.get(x).getPrice();
        }
    }
}
