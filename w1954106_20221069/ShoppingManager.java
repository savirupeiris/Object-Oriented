import java.util.ArrayList;

//In Interfaces we just declare only the method name-No body included
//These all methods will be overRiding in the WestminsterShoppingManager classs
public interface ShoppingManager {
    public void addNewProduct();
    void deleteProduct();
    void printProducts();
    void saveProducts(ArrayList<Product>saveProducts);

    void saveUsers(ArrayList<User> users);

    void loadProducts();

    void loadUsers();
}
