import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final LocalDateTime timestamp;
    private final String type;
    private final String details;
    private final double amount;

    public Transaction(String type, String details, double amount) {
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.details = details;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return timestamp.format(FORMAT) + " | " + type + " | " + details
                + " | Rs. " + String.format("%.2f", amount);
    }
}
