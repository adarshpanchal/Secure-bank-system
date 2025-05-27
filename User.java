public class User {
    private String username;
    private String password;
    private String accountNumber;
    private String pin;
    private double balance;
    private StringBuilder transactionHistory;

    public User(String username, String password, String accountNumber, String pin, double balance) {
        this.username = username;
        this.password = password;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new StringBuilder();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getAccountNumber() { return accountNumber; }
    public String getPin() { return pin; }
    public double getBalance() { return balance; }
    public String getTransactionHistory() { return transactionHistory.toString(); }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addTransaction(String transaction) {
        transactionHistory.append(transaction).append("\n");
    }
}
