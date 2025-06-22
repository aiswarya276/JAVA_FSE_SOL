// Notifier.java
public interface Notifier {
    void send(String message);
}

// EmailNotifier.java
public class EmailNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}

// NotifierDecorator.java
public abstract class NotifierDecorator implements Notifier {
    protected Notifier wrappedNotifier;

    public NotifierDecorator(Notifier notifier) {
        this.wrappedNotifier = notifier;
    }

    @Override
    public void send(String message) {
        wrappedNotifier.send(message);
    }
}

// SMSNotifierDecorator.java
public class SMSNotifierDecorator extends NotifierDecorator {

    public SMSNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    private void sendSMS(String message) {
        System.out.println("Sending SMS: " + message);
    }

    @Override
    public void send(String message) {
        super.send(message);
        sendSMS(message);
    }
}

// SlackNotifierDecorator.java
public class SlackNotifierDecorator extends NotifierDecorator {
    public SlackNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending Slack Message: " + message);
    }
}

// NotificationTest.java
public class NotificationTest {
    public static void main(String[] args) {
        Notifier notifier = new EmailNotifier();

        // Add SMS and Slack notification functionality dynamically
        Notifier smsNotifier = new SMSNotifierDecorator(notifier);
        Notifier slackAndSmsNotifier = new SlackNotifierDecorator(smsNotifier);

        System.out.println("Sending through multiple channels:");
        slackAndSmsNotifier.send("System maintenance at midnight.");
    }
}

