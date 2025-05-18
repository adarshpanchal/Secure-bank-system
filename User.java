import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String password;
    private List<Transaction> transactions;
    private double balance;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.transactions = new ArrayList<>();
        this.balance = 0.0;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction("Withdraw", amount));
        }
    }

    public List<Transaction> getTransactions() { return transactions; }
}