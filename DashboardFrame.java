import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private DataHandler handler;
    private User user;

    private JLabel balanceLabel;
    private JTextArea transactionArea;

    public DashboardFrame(DataHandler handler, User user) {
        this.handler = handler;
        this.user = user;

        setTitle("Dashboard - " + user.getUsername());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        balanceLabel = new JLabel("Balance: ₹" + user.getBalance());
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(balanceLabel, BorderLayout.NORTH);

        transactionArea = new JTextArea();
        transactionArea.setEditable(false);
        transactionArea.setText(user.getTransactionHistory());
        add(new JScrollPane(transactionArea), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();

        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton transferBtn = new JButton("Transfer");
        JButton refreshBtn = new JButton("Refresh");
        JButton logoutBtn = new JButton("Logout");

        buttonsPanel.add(depositBtn);
        buttonsPanel.add(withdrawBtn);
        buttonsPanel.add(transferBtn);
        buttonsPanel.add(refreshBtn);
        buttonsPanel.add(logoutBtn);

        add(buttonsPanel, BorderLayout.SOUTH);

        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        transferBtn.addActionListener(e -> transfer());
        refreshBtn.addActionListener(e -> refresh());
        logoutBtn.addActionListener(e -> logout());
    }

    private void deposit() {
        String input = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
        if (input == null) return;

        try {
            double amount = Double.parseDouble(input);
            if (amount <= 0) throw new NumberFormatException();

            user.setBalance(user.getBalance() + amount);
            user.addTransaction("Deposited ₹" + amount);
            handler.saveToFile();

            refresh();
            JOptionPane.showMessageDialog(this, "Deposit successful!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void withdraw() {
        String pin = JOptionPane.showInputDialog(this, "Enter your PIN to withdraw:");
        if (pin == null) return;

        if (!handler.verifyPin(user.getAccountNumber(), pin)) {
            JOptionPane.showMessageDialog(this, "Incorrect PIN!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String input = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
        if (input == null) return;

        try {
            double amount = Double.parseDouble(input);
            if (amount <= 0) throw new NumberFormatException();

            if (amount > user.getBalance()) {
                JOptionPane.showMessageDialog(this, "Insufficient balance!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            user.setBalance(user.getBalance() - amount);
            user.addTransaction("Withdrew ₹" + amount);
            handler.saveToFile();

            refresh();
            JOptionPane.showMessageDialog(this, "Withdrawal successful!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void transfer() {
        String pin = JOptionPane.showInputDialog(this, "Enter your PIN to transfer:");
        if (pin == null) return;

        if (!handler.verifyPin(user.getAccountNumber(), pin)) {
            JOptionPane.showMessageDialog(this, "Incorrect PIN!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String targetAcc = JOptionPane.showInputDialog(this, "Enter recipient account number:");
        if (targetAcc == null) return;

        User targetUser = handler.getUserByAccountNumber(targetAcc);
        if (targetUser == null) {
            JOptionPane.showMessageDialog(this, "Recipient account not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String input = JOptionPane.showInputDialog(this, "Enter amount to transfer:");
        if (input == null) return;

        try {
            double amount = Double.parseDouble(input);
            if (amount <= 0) throw new NumberFormatException();

            if (amount > user.getBalance()) {
                JOptionPane.showMessageDialog(this, "Insufficient balance!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            user.setBalance(user.getBalance() - amount);
            targetUser.setBalance(targetUser.getBalance() + amount);

            user.addTransaction("Transferred ₹" + amount + " to " + targetUser.getUsername());
            targetUser.addTransaction("Received ₹" + amount + " from " + user.getUsername());

            handler.saveToFile();

            refresh();
            JOptionPane.showMessageDialog(this, "Transfer successful!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refresh() {
        balanceLabel.setText("Balance: ₹" + user.getBalance());
        transactionArea.setText(user.getTransactionHistory());
    }

    private void logout() {
        handler.saveToFile();
        dispose();
        new LoginSignupFrame(handler).setVisible(true);
    }
}
