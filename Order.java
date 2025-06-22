// Order.java
public class Order {
    private String orderId;
    private String customerName;
    private double totalPrice;

    public Order(String orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public double getTotalPrice() { return totalPrice; }

    @Override
    public String toString() {
        return "[" + orderId + "] " + customerName + " - $" + totalPrice;
    }
}

// SortUtil.java
public class SortUtil {
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (orders[j].getTotalPrice() < orders[j + 1].getTotalPrice()) {
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break; // Optimization
        }
    }

        public static void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pi = partition(orders, low, high);
            quickSort(orders, low, pi - 1);
            quickSort(orders, pi + 1, high);
        }
    }

    private static int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].getTotalPrice();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (orders[j].getTotalPrice() >= pivot) { // Sort descending
                i++;
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }

        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;

        return i + 1;
    }
}

// OrderTest.java
public class OrderTest {
    public static void main(String[] args) {
        Order[] orders = {
            new Order("O101", "Alice", 1200.0),
            new Order("O102", "Bob", 400.0),
            new Order("O103", "Charlie", 900.0),
            new Order("O104", "Daisy", 1500.0)
        };

        System.out.println("🔁 Bubble Sort by Total Price (Descending):");
        SortUtil.bubbleSort(orders);
        for (Order order : orders) System.out.println(order);

        System.out.println("\n⚡ Quick Sort by Total Price (Descending):");
        Order[] moreOrders = {
            new Order("O201", "Ella", 500.0),
            new Order("O202", "Frank", 2500.0),
            new Order("O203", "George", 800.0),
            new Order("O204", "Helen", 2000.0)
        };
        SortUtil.quickSort(moreOrders, 0, moreOrders.length - 1);
        for (Order order : moreOrders) System.out.println(order);
    }
}
