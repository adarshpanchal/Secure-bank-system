import javax.swing.*;
import java.awt.*;

public class LoginSignupFrame extends JFrame {
    private DataHandler handler;

    private JTabbedPane tabs;

    private JTextField signupUsername;
    private JPasswordField signupPassword;
    private JTextField signupAccountNumber;
    private JPasswordField signupPin;

    private JTextField loginUsername;
    private JPasswordField loginPassword;

    public LoginSignupFrame(DataHandler handler) {
        this.handler = handler;
        setTitle("Secure Bank System - Login / Signup");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabs = new JTabbedPane();

        JPanel signupPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        signupUsername = new JTextField();
        signupPassword = new JPasswordField();
        signupAccountNumber = new JTextField();
        signupPin = new JPasswordField();

        signupPanel.add(new JLabel("Username:"));
        signupPanel.add(signupUsername);
        signupPanel.add(new JLabel("Password:"));
        signupPanel.add(signupPassword);
        signupPanel.add(new JLabel("Account Number:"));
        signupPanel.add(signupAccountNumber);
        signupPanel.add(new JLabel("PIN (4 digits):"));
        signupPanel.add(signupPin);

        JButton signupBtn = new JButton("Sign Up");
        signupPanel.add(new JLabel());
        signupPanel.add(signupBtn);

        signupBtn.addActionListener(e -> doSignup());

        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginUsername = new JTextField();
        loginPassword = new JPasswordField();

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(loginUsername);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(loginPassword);

        JButton loginBtn = new JButton("Log In");
        loginPanel.add(new JLabel());
        loginPanel.add(loginBtn);

        loginBtn.addActionListener(e -> doLogin());

        tabs.addTab("Login", loginPanel);
        tabs.addTab("Sign Up", signupPanel);

        add(tabs);
    }

    private void doSignup() {
        String username = signupUsername.getText().trim();
        String password = new String(signupPassword.getPassword());
        String accNum = signupAccountNumber.getText().trim();
        String pin = new String(signupPin.getPassword());

        if (username.isEmpty() || password.isEmpty() || accNum.isEmpty() || pin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pin.length() != 4 || !pin.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(this, "PIN must be exactly 4 digits", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (handler.getUserByAccountNumber(accNum) != null) {
            JOptionPane.showMessageDialog(this, "Account number already exists", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User newUser = new User(username, password, accNum, pin, 0.0);
        handler.addUser(newUser);
        handler.saveToFile();

        JOptionPane.showMessageDialog(this, "Signup successful! You can now log in.");

        signupUsername.setText("");
        signupPassword.setText("");
        signupAccountNumber.setText("");
        signupPin.setText("");

        tabs.setSelectedIndex(0);
    }

    private void doLogin() {
        String username = loginUsername.getText().trim();
        String password = new String(loginPassword.getPassword());

        User user = handler.authenticate(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DashboardFrame dashboard = new DashboardFrame(handler, user);
        dashboard.setVisible(true);
        dispose();
    }
}
