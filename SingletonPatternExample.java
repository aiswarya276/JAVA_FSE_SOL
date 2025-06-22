package SingletonPatternExample;

public class Logger {

    // Step 1: Create a private static instance of Logger
    private static Logger instance;

    // Step 2: Make the constructor private to prevent external instantiation
    private Logger() {
        System.out.println("Logger Initialized.");
    }

    // Step 3: Provide a public static method to get the instance
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger(); 
        }
        return instance;
    }
k
}


public class Main {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.log("This is the first log message.");
        logger2.log("This is the second log message.");

        // Test: Check if both references point to the same object
        if (logger1 == logger2) {
            System.out.println("Both logger instances are the same.");
        } else {
            System.out.println("Different logger instances exist.");
        }
    }
}
