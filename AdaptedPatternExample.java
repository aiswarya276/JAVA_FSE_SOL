// PaymentProcessor.java
public interface PaymentProcessor {
    void processPayment(double amount);
}

// StripeGateway.java
public class StripeGateway {
    public void makeStripePayment(double amount) {
        System.out.println("Paid $" + amount + " using Stripe.");
    }
}

// PayPalGateway.java
public class PayPalGateway {
    public void sendPayment(double amount) {
        System.out.println("Paid $" + amount + " using PayPal.");
    }
}


// StripeAdapter.java
public class StripeAdapter implements PaymentProcessor {
    private StripeGateway stripeGateway;

    public StripeAdapter(StripeGateway stripeGateway) {
        this.stripeGateway = stripeGateway;
    }

    @Override
    public void processPayment(double amount) {
        stripeGateway.makeStripePayment(amount);
    }
}

// PayPalAdapter.java
public class PayPalAdapter implements PaymentProcessor {
    private PayPalGateway payPalGateway;

    public PayPalAdapter(PayPalGateway payPalGateway) {
        this.payPalGateway = payPalGateway;
    }

    @Override
    public void processPayment(double amount) {
        payPalGateway.sendPayment(amount);
    }
}

// PaymentTest.java
public class PaymentTest {
    public static void main(String[] args) {
        PaymentProcessor stripePayment = new StripeAdapter(new StripeGateway());
        stripePayment.processPayment(150.0);

        PaymentProcessor paypalPayment = new PayPalAdapter(new PayPalGateway());
        paypalPayment.processPayment(85.5);
    }
}
