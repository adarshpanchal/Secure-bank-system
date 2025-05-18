import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class DashboardFrame extends JFrame {
    public DashboardFrame(HashMap<String, User> users, String currentUser) {
        setTitle("Secure Bank Dashboard - " + currentUser);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));
        User user = users.get(currentUser);

        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton balanceBtn = new JButton("Check Balance");
        JButton historyBtn = new JButton("Transaction History");
        JButton logoutBtn = new JButton("Logout");

        add(new JLabel("Welcome, " + currentUser));
        add(depositBtn);
        add(withdrawBtn);
        add(balanceBtn);
        add(historyBtn);
        add(logoutBtn);

        depositBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Amount to deposit:");
            double amt = Double.parseDouble(input);
            user.deposit(amt);
            DataHandler.saveUsers(users);
        });

        withdrawBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Amount to withdraw:");
            double amt = Double.parseDouble(input);
            user.withdraw(amt);
            DataHandler.saveUsers(users);
        });

        balanceBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Balance: " + user.getBalance()));

        historyBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Transaction t : user.getTransactions()) {
                sb.append(t.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        logoutBtn.addActionListener(e -> {
            new LoginSignupFrame();
            dispose();
        });

        setVisible(true);
    }
}