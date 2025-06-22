// Image.java
public interface Image {
    void display();
}

// RealImage.java
public class RealImage implements Image {
    private String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromRemoteServer();
    }

    private void loadFromRemoteServer() {
        System.out.println("Loading " + filename + " from remote server...");
        // Simulate delay or network call
        try {
            Thread.sleep(1000); // 1 second delay to simulate loading
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void display() {
        System.out.println("Displaying " + filename);
    }
}

// ProxyImage.java
public class ProxyImage implements Image {
    private String filename;
    private RealImage realImage;

    public ProxyImage(String filename) {
        this.filename = filename;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename); // lazy load
        } else {
            System.out.println("Using cached version of " + filename);
        }
        realImage.display();
    }
}

// ProxyPatternTest.java
public class ProxyPatternTest {
    public static void main(String[] args) {
        Image image1 = new ProxyImage("pic1.jpg");
        Image image2 = new ProxyImage("pic2.jpg");

        System.out.println("\nFirst call (should load from remote):");
        image1.display();

        System.out.println("\nSecond call (should use cache):");
        image1.display();

        System.out.println("\nLoading another image:");
        image2.display();
    }
}

