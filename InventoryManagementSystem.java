// Product.java
public class Product {
    private String productId;
    private String productName;
    private int quantity;
    private double price;

    public Product(String productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public void setProductName(String name) { this.productName = name; }
    public void setQuantity(int qty) { this.quantity = qty; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "[" + productId + "] " + productName + " - Qty: " + quantity + ", Price: $" + price;
    }
}

// InventoryManager.java
import java.util.HashMap;

public class InventoryManager {
    private HashMap<String, Product> inventory;

    public InventoryManager() {
        inventory = new HashMap<>();
    }

    public void addProduct(Product product) {
        inventory.put(product.getProductId(), product);
    }

    public void updateProduct(String productId, String name, int quantity, double price) {
        Product product = inventory.get(productId);
        if (product != null) {
            product.setProductName(name);
            product.setQuantity(quantity);
            product.setPrice(price);
        } else {
            System.out.println("Product ID " + productId + " not found.");
        }
    }

    public void deleteProduct(String productId) {
        if (inventory.remove(productId) == null) {
            System.out.println("Product ID " + productId + " not found.");
        }
    }

    public void printInventory() {
        for (Product p : inventory.values()) {
            System.out.println(p);
        }
    }
}

// InventoryTest.java
public class InventoryTest {
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();

        Product p1 = new Product("P101", "Keyboard", 50, 999.99);
        Product p2 = new Product("P102", "Mouse", 150, 499.50);
        Product p3 = new Product("P103", "Monitor", 30, 5999.00);

        manager.addProduct(p1);
        manager.addProduct(p2);
        manager.addProduct(p3);

        System.out.println("Initial Inventory:");
        manager.printInventory();

        manager.updateProduct("P103", "LED Monitor", 25, 6499.00);
        System.out.println("\nAfter Update:");
        manager.printInventory();

        manager.deleteProduct("P102");
        System.out.println("\nAfter Deletion:");
        manager.printInventory();
    }
}
