import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class LoginPanel extends JPanel {
    public LoginPanel(LoginSignupFrame parent, HashMap<String, User> users) {
        setLayout(new GridLayout(5, 1));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        JButton switchToSignup = new JButton("Sign Up");

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginBtn);
        add(switchToSignup);

        loginBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            if (users.containsKey(user) && users.get(user).getPassword().equals(pass)) {
                new DashboardFrame(users, user);
                SwingUtilities.getWindowAncestor(this).dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }
        });

        switchToSignup.addActionListener(e -> parent.switchTo("Signup"));
    }
}