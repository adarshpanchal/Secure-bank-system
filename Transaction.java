import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable {
    private String type;
    private double amount;
    private String timestamp;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now().toString();
    }

    public String toString() {
        return timestamp + " - " + type + ": " + amount;
    }
}