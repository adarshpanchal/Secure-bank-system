import java.io.*;
import java.util.*;

public class DataHandler {
    private HashMap<String, User> users = new HashMap<>();
    private static final String FILE_NAME = "bankdata.txt";

    public void addUser(User user) {
        users.put(user.getAccountNumber(), user);
    }

    public User authenticate(String username, String password) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User getUserByAccountNumber(String accNum) {
        return users.get(accNum);
    }

    public boolean verifyPin(String accNum, String pin) {
        User user = users.get(accNum);
        return user != null && user.getPin().equals(pin);
    }

    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            for (User user : users.values()) {
                // Save username,password,accNum,pin,balance,transactionHistory(base64)
                String transBase64 = Base64.getEncoder().encodeToString(user.getTransactionHistory().getBytes());
                writer.println(user.getUsername() + "," +
                               user.getPassword() + "," +
                               user.getAccountNumber() + "," +
                               user.getPin() + "," +
                               user.getBalance() + "," +
                               transBase64);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",", 6);
                if (data.length == 6) {
                    String username = data[0];
                    String password = data[1];
                    String accNum = data[2];
                    String pin = data[3];
                    double balance = Double.parseDouble(data[4]);
                    String transBase64 = data[5];
                    User user = new User(username, password, accNum, pin, balance);
                    String transHistory = new String(Base64.getDecoder().decode(transBase64));
                    user.addTransaction(transHistory); // Add all history at once
                    users.put(accNum, user);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        }
    }
}
