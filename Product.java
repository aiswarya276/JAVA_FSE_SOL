// Product.java
public class Product implements Comparable<Product> {
    private String productId;
    private String productName;
    private String category;

    public Product(String productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }

    @Override
    public int compareTo(Product other) {
        return this.productName.compareToIgnoreCase(other.productName);
    }

    @Override
    public String toString() {
        return "[" + productId + "] " + productName + " (" + category + ")";
    }
}

// SearchUtil.java
public class SearchUtil {
    public static Product linearSearch(Product[] products, String targetName) {
        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(targetName)) {
                return product;
            }
        }
        return null;
    }

        public static Product binarySearch(Product[] products, String targetName) {
        int left = 0;
        int right = products.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int compare = products[mid].getProductName().compareToIgnoreCase(targetName);

            if (compare == 0) return products[mid];
            else if (compare < 0) left = mid + 1;
            else right = mid - 1;
        }
        return null;
    }
}

// SearchTest.java
import java.util.Arrays;

public class SearchTest {
    public static void main(String[] args) {
        Product[] products = {
            new Product("P101", "Shoes", "Footwear"),
            new Product("P102", "Shirt", "Apparel"),
            new Product("P103", "Laptop", "Electronics"),
            new Product("P104", "Phone", "Electronics"),
            new Product("P105", "Watch", "Accessories")
        };

        // Linear Search
        System.out.println("🔎 Linear Search for 'Laptop':");
        Product foundLinear = SearchUtil.linearSearch(products, "Laptop");
        System.out.println(foundLinear != null ? foundLinear : "Not found");

        // Sort for Binary Search
        Arrays.sort(products);  // based on productName

        // Binary Search
        System.out.println("\n🔎 Binary Search for 'Laptop':");
        Product foundBinary = SearchUtil.binarySearch(products, "Laptop");
        System.out.println(foundBinary != null ? foundBinary : "Not found");
    }
}
