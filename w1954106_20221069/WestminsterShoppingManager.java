import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


//This the interface where Shopping manager connects-Maintains the list of Products
public class WestminsterShoppingManager implements ShoppingManager {
    static WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
    Scanner input = new Scanner(System.in);
    //Non-static field 'saveProducts' cannot be referenced from a static context
    static ArrayList<Product> savedProducts = new ArrayList<>();
    //Arraylist to store date in users
    static ArrayList<User> users = new ArrayList<>();

    static ArrayList<ShoppingCart> Cart=new ArrayList<>();
    static ArrayList<String> CartItems=new ArrayList<>();
    static User LogInUser=null;
    static double total;

    @Override
    public void addNewProduct() {
        Scanner input =new Scanner(System.in);
        System.out.println("-----------------------------------------------");
            if (savedProducts.size() <= 50) {
                System.out.println("Do you want to add Electronics or Clothing?\n 1.Clothing \n 2.Electronics");
                System.out.println("-----------------------------------------------");
                System.out.print("Enter Your Choice (1 or 2):");
                String option2 = input.next();

                if (!option2.equals("1") && !option2.equals("2")) {
                    System.out.println("\nInvalid Option. Please Try Again!");
//                    System.out.println("-------------------------------------------");
                    westminsterShoppingManager.addNewProduct();
                }
                System.out.print("\nEnter Product ID (e.g., a001, a002) : ");
                String prodId = input.next();

                for (int x = 0; x < savedProducts.size(); x++) {
                    if (savedProducts.get(x).getProductID().equals(prodId)) {
                        System.out.println("Product is already Exists!\nPlease Try Again");
                        return;
                    }
                }
                try {
                    //Validation of the Product Name
                    System.out.print("Enter Product Name\t\t\t\t\t: ");
                    String productName = input.next();

                    System.out.print("Enter number of available items\t\t:");
                    int numberOfAvailableItems = input.nextInt();
                    if (numberOfAvailableItems > 50) {
                        System.out.println("\nYou can not add more than 50 Items");
                        printMenuBar();
                    }
                    System.out.print("Enter Price\t\t\t\t\t\t\t: ");
                    double price = input.nextInt();

                    if (option2.equals("1")) {
                        System.out.print("Enter Product Size\t\t\t\t\t: ");
                        String Size = input.next();
                        System.out.print("Enter Product Colour\t\t\t\t: ");
                        String Colour = input.next();
                        Clothing clothing = new Clothing(prodId, productName, numberOfAvailableItems, price, Size, Colour);
                        savedProducts.add(clothing);
                    }

                    if (option2.equals("2")) {
                        System.out.print("Enter Product Brand\t\t\t\t\t: ");
                        String productBrand = input.next();
                        System.out.print("Enter Warranty Period \t\t\t\t: ");
                        String warrantyPeriod = input.next();

                        Electronics electronics = new Electronics(prodId, productName, numberOfAvailableItems, price, productBrand, warrantyPeriod);
                        savedProducts.add(electronics);
                    }
                    System.out.println("\nProduct Added Successfully!");
                }
                catch(InputMismatchException e){
                    System.out.println("\nInvalid Option. Please Try Again!");
                    westminsterShoppingManager.addNewProduct();
                }
                } else{
                System.out.println("Maximum number of products reached");

            }
        }

    @Override
    public void deleteProduct() {
        System.out.print("Enter Product ID: ");
        String deleteProduct=input.next();
        for(int x=0;x<savedProducts.size();x++){
            if(savedProducts.get(x).getProductID().equals(deleteProduct)){

                //Checking if the item is deleted by Electronics
                if(savedProducts.get(x)instanceof Electronics) {
                    savedProducts.remove(x);
                    System.out.println("Product successfully Deleted!");
                    System.out.println("\tProduct ID "+deleteProduct +"\n\tCategory:Electronics");
                    System.out.println("\nThe number of products remaining in the system: "+savedProducts.size());
                    return;
                }
                //Checking if the item is deleted by Clothing
                if(savedProducts.get(x)instanceof Clothing) {
                    savedProducts.remove(x);
                    System.out.println("Product successfully Deleted!");
                    System.out.println("\tProduct ID: "+deleteProduct +"\n\tCategory:Clothing");
                    System.out.println("\nThe number of products remaining in the system: "+savedProducts.size());
                    return;
                }
            }
        }
        System.out.println("\nProduct Not Found!");
    }
    @Override
    public void printProducts() {
        ArrayList<Product>arraylist=new ArrayList<>(savedProducts);

        // Sort by product ID
        for(int i = 0; i < arraylist.size(); ++i) {
            for(int j = i + 1; j < arraylist.size(); ++j) {
                if (arraylist.get(i).getProductID().compareTo(arraylist.get(j).getProductID()) > 0) {
                    Product temp = arraylist.get(i);
                    arraylist.set(i, arraylist.get(j));
                    arraylist.set(j, temp);
                }
            }
        }

        for(int x=0;x<arraylist.size();x++){
            System.out.println("Product ID                  : "+arraylist.get(x).getProductID());
            System.out.println("Product name                : " + arraylist.get(x).getProductName());
            System.out.println("Number of available items   : " + arraylist.get(x).getAvailableItems());
            System.out.println("Price                       : " + arraylist.get(x).getPrice());

            if(arraylist.get(x) instanceof Electronics){
                System.out.println("Product Brand               : "+((Electronics) arraylist.get(x)).getBrand());
                System.out.println("Product Warranty Period     : " +((Electronics) arraylist.get(x)).getWarrantyPeriod());
                System.out.println("\n");
            }
            if(arraylist.get(x) instanceof Clothing){
                System.out.print("Clothing Size               : "+((Clothing) arraylist.get(x)).getSize());
                System.out.print("\nClothing Colour             : "+((Clothing) arraylist.get(x)).getColour());
                System.out.println("\n");
            }
        }
    }
    @Override
    public void saveProducts(ArrayList<Product> savedProducts) {
        try {
            File file = new File("Products.txt");

            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                System.out.println("Absolute path: " + file.getAbsolutePath());
            } else {
                System.out.println("File already exists.");
            }

            FileOutputStream writer = new FileOutputStream("Products.txt");
            ObjectOutputStream obj = new ObjectOutputStream(writer);

            obj.writeObject(savedProducts);
            writer.close();
            obj.close();

            System.out.println("All products saved successfully!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void saveUsers(ArrayList<User> users) {
        try {
            File file = new File("Users.txt");

            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }

            FileOutputStream writer = new FileOutputStream("Users.txt");
            ObjectOutputStream obj = new ObjectOutputStream(writer);

            obj.writeObject(users);
            writer.close();
            obj.close();

            System.out.println("All users saved successfully!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadProducts() {
        try{
            FileInputStream readFile = new FileInputStream("Products.txt");
            ObjectInputStream readObj = new ObjectInputStream(readFile);

            savedProducts = (ArrayList<Product>) readObj.readObject();
            readObj.close();

        }catch(IOException | ClassNotFoundException e) {
            System.out.println("");
        }
    }
    @Override
    public void loadUsers() {
        try{
            FileInputStream readData = new FileInputStream("Users.txt");
            ObjectInputStream readStream = new ObjectInputStream(readData);

            users = (ArrayList<User>) readStream.readObject();
            readStream.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.print("");
        }
    }
    //User Verification
    public void accountVerification(){
        System.out.print("\nDo you already have an account? (Yes or No) :");
        //Validation done for both lower case and upper case letters
        String userOption=input.next().toUpperCase();
        if(userOption.equals("NO")){
            System.out.println("Please signup with your credentials.");
            System.out.print("Enter your username: ");
            String username=input.next();
            System.out.print("Enter your Password: ");
            String password=input.next();

            User user=new User(username,password);
            users.add(user); //Adding user information to users arraylist.
            LogInUser=user;
            saveUsers(users);
        }
        else if(userOption.equals(("YES"))) {
            System.out.println("Please login with your credentials");
            System.out.print("Enter your Username: ");
            String username = input.next();
            System.out.print("Enter your Password: ");
            String password = input.next();
//            User user=new User(username,password);
//            LogInUser=user;
            boolean loginUserCheck = false;
            for (int i = 0; i < users.size(); i++) {
//                Check whether the username and password matches
                if (users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password)) {
                    System.out.println("Log in Successful!");
                    User user = new User(username, password, users.get(i).getNumberOfPurchases());
                    LogInUser = user;
                    loginUserCheck = true;
                }
                }
            if (!loginUserCheck){
                System.out.println("\nUsername or Password is Incorrect.");
                System.out.println("You'll be directed back to the User Verification Centre.");
                accountVerification();
            }
            }
        else{
            System.out.println("Invalid option! \nPlease try again");
            accountVerification();
        }
        westminsterShoppingManager.Frame2();
    }
    //Graphical user Interface

    public void Frame2(){
        JFrame frame = new JFrame("Westminster Shopping Center");

        //Creating the shopping cart button
        JButton shoppingCart = new JButton("Shopping Cart");
        shoppingCart.setBounds(600, 20, 130, 34);

        //Creating the label for the dropdown list
        JLabel label01 = new JLabel("Select Product Category");
        label01.setBounds(100, 50, 200, 20);
        label01.setFont(new Font("Dialog", Font.PLAIN, 12));

        //Customizing the buttons
        Color buttonBackground01 = new Color(175, 205, 255);
        Color buttonForeground01 = new Color(0, 0, 0);
        Color cbBackground01 = new Color(255, 255, 255);
        Color cbForeground01 = new Color(0, 0, 0);

        //Creating the dropdown list
        String[] categories = { " All "," Electronics ", " Clothing " };
        JComboBox<String> cbCategories = new JComboBox(categories);
        cbCategories.setFont(new Font("Dialog", Font.PLAIN, 12));
        cbCategories.setBounds(300, 45, 200, 30);
        cbCategories.setSelectedIndex(0);

        String[] columnNames = { "Product ID", "Name", "Category", "Price(€)", "Info" };
        Object[][] data = new Object[savedProducts.size()][5];

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) cbCategories.getSelectedItem();
                if(selectedItem.equals(" All ")){
                    //Creating data[] using products array list
                    for(int x = 0; x < savedProducts.size(); x++){
                        data[x][0] = savedProducts.get(x).getProductID();
                        data[x][1] = savedProducts.get(x).getProductName();
                        if(savedProducts.get(x) instanceof Electronics){
                            data[x][2] = "Electronics";
                        }
                        else if(savedProducts.get(x) instanceof Clothing){
                            data[x][2] = "Clothing";
                        }
                        data[x][3] = savedProducts.get(x).getPrice();
                        if(savedProducts.get(x) instanceof Electronics){
                            data[x][4] = "Brand: " + ((Electronics) savedProducts.get(x)).getBrand() + ", Warranty: " + ((Electronics) savedProducts.get(x)).getWarrantyPeriod();
                        }
                        else if(savedProducts.get(x) instanceof Clothing){
                            data[x][4] = "Size: " + ((Clothing) savedProducts.get(x)).getSize() + ", Color: " + ((Clothing) savedProducts.get(x)).getColour();
                        }
                    }
                }
                else if(selectedItem.equals(" Electronics ")){
                    //Creating data[] using products array list where only electronics are included
                    for (int i = 0; i < data.length; i++) {
                        Arrays.fill(data[i], "");
                    }
                    int y = 0;
                    for(int x = 0; x < savedProducts.size(); x++){
                        if(savedProducts.get(x) instanceof Electronics){
                            data[y][0] = savedProducts.get(x).getProductID();
                            data[y][1] = savedProducts.get(x).getProductName();
                            data[y][2] = "Electronics";
                            data[y][3] = savedProducts.get(x).getPrice();
                            data[y][4] = "Brand: " + ((Electronics) savedProducts.get(x)).getBrand() + ", Warranty: " + ((Electronics) savedProducts.get(x)).getWarrantyPeriod();
                            y++;
                        }
                    }
                }
                else if(selectedItem.equals(" Clothing ")){
                    //Creating data[] using products array list where only clothing are included
                    for (int i = 0; i < data.length; i++) {
                        Arrays.fill(data[i], "");
                    }
                    int y = 0;
                    for(int x = 0; x < savedProducts.size(); x++){
                        if(savedProducts.get(x) instanceof Clothing){
                            data[y][0] = savedProducts.get(x).getProductID();
                            data[y][1] = savedProducts.get(x).getProductName();
                            data[y][2] = "Clothing";
                            data[y][3] = savedProducts.get(x).getPrice();
                            data[y][4] = "Size: " + ((Clothing) savedProducts.get(x)).getSize() + ", Color: " + ((Clothing) savedProducts.get(x)).getColour();
                            y++;
                        }
                    }
                }
            }
        };

        cbCategories.addActionListener(listener);
        listener.actionPerformed(null); // Trigger event handler for default item

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(150, 10, 10, 10));

        JPanel tablePanel = new JPanel(new BorderLayout());

        JTable table = new JTable(data, columnNames);

        //Setting the table Read-Only
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellEditor(null);
        }

        table.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));

        //Setting the colour of the table red for less available items
//        for(int i=0;i< table.getRowCount();i++) {
//            if(savedProducts.get(i).getAvailableItems()<=3){
//                table.setBackground(Color.red);
//            }
//        }

        table.setRowHeight(30);
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn infoColumn = columnModel.getColumn(4);
        infoColumn.setPreferredWidth(230);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Create a panel for product details
        JPanel detailsPanel = new JPanel(new FlowLayout());
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 50, 20));
        detailsPanel.setLayout(new GridLayout(0, 1));

        Font titleFont = new Font("Dialog", Font.BOLD, 12);
        Font detailsFont = new Font("Dialog", Font.PLAIN, 12);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                detailsPanel.removeAll(); // Remove all components from the details panel

                JSeparator separator = new JSeparator(); // Create a horizontal line separator
                separator.setForeground(Color.BLACK);
                separator.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
                detailsPanel.add(separator); // Add the separator to the panel

                int selectRow = table.getSelectedRow();
                String productID = (String) table.getValueAt(selectRow, 0);

                for(int x = 0; x < savedProducts.size(); x++) {
                    if (savedProducts.get(x).getProductID() == productID) {
                        JLabel title = new JLabel("Selected Product Details");
                        JLabel productId = new JLabel("Product ID: " + savedProducts.get(x).getProductID());

                        title.setFont(titleFont);
                        productId.setFont(detailsFont);

                        detailsPanel.add(title);
                        detailsPanel.add(productId);

                        if (savedProducts.get(x) instanceof Electronics) {
                            JLabel category = new JLabel("Category: Electronics");
                            JLabel productName = new JLabel("Product name: " + savedProducts.get(x).getProductName());
                            JLabel brand = new JLabel("Brand: " + ((Electronics) savedProducts.get(x)).getBrand());
                            JLabel warranty = new JLabel("Warranty: " + ((Electronics) savedProducts.get(x)).getWarrantyPeriod());
                            JLabel itemsAvailable = new JLabel("Items available: " + savedProducts.get(x).getAvailableItems());

                            category.setFont(detailsFont);
                            productName.setFont(detailsFont);
                            brand.setFont(detailsFont);
                            warranty.setFont(detailsFont);
                            itemsAvailable.setFont(detailsFont);

                            detailsPanel.add(category);
                            detailsPanel.add(productName);
                            detailsPanel.add(brand);
                            detailsPanel.add(warranty);
                            detailsPanel.add(itemsAvailable);

                        } else if (savedProducts.get(x) instanceof Clothing) {
                            JLabel category = new JLabel("Category: Clothing");
                            JLabel productName = new JLabel("Product name: " + savedProducts.get(x).getProductName());
                            JLabel size = new JLabel("Size: " + ((Clothing) savedProducts.get(x)).getSize());
                            JLabel color = new JLabel("Color: " + ((Clothing) savedProducts.get(x)).getColour());
                            JLabel itemsAvailable = new JLabel("Items available: " + savedProducts.get(x).getAvailableItems());

                            category.setFont(detailsFont);
                            productName.setFont(detailsFont);
                            size.setFont(detailsFont);
                            color.setFont(detailsFont);
                            itemsAvailable.setFont(detailsFont);

                            detailsPanel.add(category);
                            detailsPanel.add(productName);
                            detailsPanel.add(size);
                            detailsPanel.add(color);
                            detailsPanel.add(itemsAvailable);
                        }

                        JButton addToCart = new JButton("Add to Shopping Cart");
                        addToCart.setPreferredSize(new Dimension(200, 30));

                        Product product = savedProducts.get(x);
                        int index = x;

                        addToCart.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //Reducing the available items
                                savedProducts.get(index).setAvailableItems(savedProducts.get(index).getAvailableItems() - 1);

                                CartItems.add(product.getProductID());

                                // Check if a ShoppingCart item with this productID already exists
                                int existingItemIndex = -1;
                                for (int i = 0; i < Cart.size(); i++) {
                                    if (Cart.get(i).getProduct().getProductID().equals(product.getProductID())) {
                                        existingItemIndex = i;
                                        break;
                                    }
                                }

                                int quantity = Collections.frequency(CartItems, product.getProductID());

                                String productID = product.getProductID();
                                String productName = product.getProductName();

                                if(product instanceof Electronics){
                                    String brand = ((Electronics) product).getBrand();
                                    String warranty = ((Electronics) product).getWarrantyPeriod();

                                    Product electronics = new Electronics(productID, productName, brand, warranty);
                                    ShoppingCart shoppingCart = new ShoppingCart(electronics, quantity, product.getPrice());

                                    // If a matching item exists, update its quantity
                                    if (existingItemIndex != -1) {
                                        Cart.get(existingItemIndex).setQuantity(quantity);
                                        Cart.get(existingItemIndex).setPrice(product.getPrice() * quantity);
                                    } else {
                                        Cart.add(shoppingCart);
                                    }

                                }
                                else if(product instanceof Clothing) {
                                    String size = ((Clothing) product).getSize();
                                    String color = ((Clothing) product).getColour();

                                    Product clothing = new Clothing(productID, productName, size, color);
                                    ShoppingCart shoppingCart = new ShoppingCart(clothing, quantity, product.getPrice());

                                    // If a matching item exists, update its quantity
                                    if (existingItemIndex != -1) {
                                        Cart.get(existingItemIndex).setQuantity(quantity);
                                        Cart.get(existingItemIndex).setPrice(product.getPrice() * quantity);
                                    } else {
                                        Cart.add(shoppingCart);
                                    }
                                }
                            }
                        });

                        // Formatting the button
                        addToCart.setBackground(buttonBackground01);
                        addToCart.setForeground(buttonForeground01);
                        addToCart.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                        addToCart.setFocusPainted(false);

                        // Create a panel to center the button within
                        JPanel buttonPanel = new JPanel(new GridBagLayout());
                        GridBagConstraints c = new GridBagConstraints();
                        c.gridwidth = GridBagConstraints.REMAINDER;
                        c.anchor = GridBagConstraints.CENTER;
                        buttonPanel.add(addToCart, c);

                        // Add the button panel to the details panel
                        detailsPanel.add(buttonPanel);
                        mainPanel.add(detailsPanel, BorderLayout.SOUTH); // Add details panel to the main panel
                        mainPanel.revalidate(); // Revalidate the main panel for layout updates
                    }
                }
            }
        });

        //Creating the scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        shoppingCart.setBackground(buttonBackground01);
        shoppingCart.setForeground(buttonForeground01);
        shoppingCart.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        shoppingCart.setFocusPainted(false);

        cbCategories.setBackground(cbBackground01);
        cbCategories.setForeground(cbForeground01);
        cbCategories.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setBackground(cbBackground01);
                setForeground(cbForeground01);
                super.paint(g);
            }
        });

        shoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProducts(savedProducts);
                JFrame frame02 = new JFrame("Shopping Cart");

                JPanel mainPanel02 = new JPanel(new BorderLayout());
                mainPanel02.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JPanel tablePanel02 = new JPanel(new BorderLayout());

                String[] columnNames02 = {"Product", "Quantity", "Price(€)"};
                Object[][] data02 = new Object[Cart.size()][3];

                for (int x = 0; x < Cart.size(); x++) {
                    String product = "";
                    if(Cart.get(x).getProduct() instanceof Electronics){
                        String brand = ((Electronics) Cart.get(x).getProduct()).getBrand();
                        String warranty = ((Electronics) Cart.get(x).getProduct()).getWarrantyPeriod();
                        product = Cart.get(x).getProduct().getProductID() + ", "
                                + Cart.get(x).getProduct().getProductName() + ", "
                                + brand + ", " + warranty;
                    }
                    else if(Cart.get(x).getProduct() instanceof Clothing) {
                        String size = ((Clothing) Cart.get(x).getProduct()).getSize();
                        String color = ((Clothing) Cart.get(x).getProduct()).getColour();

                        product = Cart.get(x).getProduct().getProductID() + ", "
                                + Cart.get(x).getProduct().getProductName() + ", "
                                + size + ", " + color;
                    }

                    data02[x][0] = product;
                    data02[x][1] = Cart.get(x).getQuantity();
                    data02[x][2] = Cart.get(x).getPrice();
                }

                JTable table02 = new JTable(data02, columnNames02);

                //Setting the table Read-Only
                for (int i = 0; i < table02.getColumnCount(); i++) {
                    table02.getColumnModel().getColumn(i).setCellEditor(null);
                }

                table02.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
                table02.setRowHeight(30);

                TableColumnModel columnModel = table02.getColumnModel();
                TableColumn productColumn = columnModel.getColumn(0);
                productColumn.setPreferredWidth(250);

                DefaultTableCellRenderer centerRenderer02 = new DefaultTableCellRenderer();
                centerRenderer02.setHorizontalAlignment(JLabel.CENTER);
                table02.setDefaultRenderer(Object.class, centerRenderer02);

                //Creating the scroll pane
                JScrollPane scrollPane02 = new JScrollPane(table02);
                tablePanel02.add(scrollPane02, BorderLayout.CENTER);
                mainPanel02.add(tablePanel02, BorderLayout.CENTER);

                // Create a panel for total
                JPanel totalPanel = new JPanel(new FlowLayout());
                totalPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 50, 20));
                totalPanel.setLayout(new GridLayout(0, 1));

                for(int x = 0; x < Cart.size(); x++){
                    total += Cart.get(x).getPrice();
                }

                JLabel totalLabel = new JLabel("Total : \t" + total + "€");
                totalLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
                totalPanel.add(totalLabel);

                boolean discount1 = false;
                if(LogInUser.getNumberOfPurchases() == 0){
                    double firstPurchase = total * 0.1;
                    JLabel firstPurchaseLabel = new JLabel("First Purchase Discount (10%) : \t- " + firstPurchase + "€");
                    firstPurchaseLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
                    totalPanel.add(firstPurchaseLabel);
                    discount1 = true;
                }

                for(int x = 0; x < users.size(); x++){
                    if(users.get(x).getUsername().equals(LogInUser.getUsername())){
                        users.get(x).setNumberOfPurchases(users.get(x).getNumberOfPurchases() + 1);
                        System.out.println("\n purchase : " + users.get(x).getNumberOfPurchases());
                        saveUsers(users);
                    }
                }

                int electronicsCount = 0;
                int clothingCount = 0;

                for(int x = 0; x < Cart.size(); x++){
                    if(Cart.get(x).getProduct() instanceof Electronics){
                        electronicsCount += Cart.get(x).getQuantity();
                    }
                    else if(Cart.get(x).getProduct() instanceof Clothing){
                        clothingCount += Cart.get(x).getQuantity();
                    }
                }

                boolean discount2 = false;
                if(electronicsCount >= 3 || clothingCount >= 3){
                    double threePurchases = total * 0.2;
                    JLabel threePurchasesLabel = new JLabel("Three Items in the Same Category Discount (20%) : \t- " + (threePurchases * 0.2) + "€");
                    threePurchasesLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
                    totalPanel.add(threePurchasesLabel);
                    discount2 = true;
                }

                double finalTotal;

                if(discount1 && !discount2) {
                    finalTotal = total - (total * 0.1);
                }
                else if(!discount1 && discount2){
                    finalTotal = total - (total * 0.2);
                }
                else if(discount1 && discount2){
                    finalTotal = total - ((total * 0.1) + (total * 0.2));
                }
                else{
                    finalTotal = total;
                }

                JLabel finalTotalLabel = new JLabel("Final Total : \t" + finalTotal + "€");
                totalPanel.add(finalTotalLabel);

                mainPanel02.add(totalPanel, BorderLayout.SOUTH); // Add details panel to the main panel
                mainPanel02.revalidate(); // Revalidate the main panel for layout updates

                frame02.getContentPane().add(mainPanel02);

                frame02.setSize(600, 400); // Setting the size of the frame
                frame02.setVisible(true);// Making the frame visible
            }
        });

        frame.add(label01);
        frame.add(cbCategories);
        frame.add(shoppingCart);
        frame.getContentPane().add(mainPanel);

        frame.setSize(800, 700); // Setting the size of the frame
        frame.setVisible(true);// Making the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // The frame will close and the program will stop running when the close button is clicked ...
        printMenuBar();
    }


    public static void printMenuBar() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Welcome to Westminster Shopping Menu\n");
        System.out.println("[1] Add a new Item");
        System.out.println("[2] Delete an Item");
        System.out.println("[3] Print Products");
        System.out.println("[4] Save Products");
        System.out.println("[5] Open Shopping Center");
        System.out.println("[6] Exit the system");
        System.out.println("\n----------------------------------------------");
        Scanner input=new Scanner(System.in);

        System.out.print("Enter Option (1 to 6): ");

            String option = input.next();
            switch (option) {
                case "1":
                    westminsterShoppingManager.addNewProduct();
                    printMenuBar();
                    break;
                case "2":
                    westminsterShoppingManager.deleteProduct();
                    printMenuBar();
                    break;
                case "3":
                    westminsterShoppingManager.printProducts();
                    printMenuBar();
                    break;
                case "4":
                    westminsterShoppingManager.saveProducts(savedProducts);
                    printMenuBar();
                    break;
                case "5":
                    westminsterShoppingManager.accountVerification();
                    printMenuBar();
                    break;
                case "6":
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nInvalid Option, Please Try Again!");
                    printMenuBar();
                    break;
            }
        }
        
    public static void main(String[] args) {
        westminsterShoppingManager.loadProducts();
        westminsterShoppingManager.loadUsers();
        printMenuBar();

    }
}
